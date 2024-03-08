package API;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DATALAYER.BookRepository;
import ENTITY.Book;
import ENTITY.Dummy;
import BUSINESSLAYER.BookServiceImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookHandler implements HttpHandler {

    BookRepository bookRepo = new BookRepository();
    BookServiceImplementation service = new BUSINESSLAYER.BookServiceImplementation();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            String requestURI = exchange.getRequestURI().getPath();
            if (requestURI.matches("/books/\\d+")) {
                handleGetRequestById(exchange);
            } else {
                handleGetRequest(exchange);
            }
        } else if (method.equals("POST")) {
            String path[] = exchange.getRequestURI().getPath().split("/");
            if (path.length == 2 || path.length == 3) {
                if (path.length == 2) {
                    handlePostRequest(exchange);
                } else {
                    try {
                        handlePostRequestRepair(exchange);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if (method.equals("PUT")) {
            handlePutRequest(exchange);
        } else if (method.equals("DELETE")) {
            handleDeleteRequest(exchange);
        } else {

            exchange.sendResponseHeaders(405, 0);
            exchange.close();
        }
    }

    public class Utils {
        public static String convertInputStreamToString(InputStream inputStream) throws IOException {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                return br.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    public void handleGetRequest(HttpExchange exchange) throws IOException {
        try {
            List<Book> books = service.getAllBooks();
            if (books.isEmpty()) {
                sendResponse(exchange, 404, "Books not found");
            } else {
                JSONArray jsonArray = new JSONArray();
                for (Book book : books) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bookid", book.getId());
                    jsonObject.put("bookname", book.getTitle());
                    jsonObject.put("authorId", book.getAuthorId());
                    jsonObject.put("genreId", book.getGenreId());
                    jsonObject.put("publishDate", book.getPublicationDate());
                    jsonObject.put("damaged", book.getDamaged());
                    jsonObject.put("repairStatus", book.getRepairStatus());
                    jsonArray.put(jsonObject);
                }
                String response = jsonArray.toString();
                sendResponse(exchange, 200, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error retrieving books from the database";
            sendResponse(exchange, 500, errorMessage);
        }
    }

    public void handlePostRequest(HttpExchange exchange) throws IOException {

        String requestBody = Utils.convertInputStreamToString(exchange.getRequestBody());
        JSONObject json = new JSONObject(requestBody);
        if (!json.has("title")) {
            sendResponse(exchange, 400, "Missing required field: title");
            return;
        }
        String title = json.getString("title");
        int authorId = json.getInt("authorId");
        int genreId = json.getInt("genreId");
        String publicationDate = json.getString("publicationDate");
        String damaged = json.getString("damaged");
        LocalDate datee = LocalDate.parse(publicationDate);
        String repairStatus = "";
        repairStatus = "not started";
        if (damaged.contains("no damage")) {
            repairStatus = "no need to repair";
        }

        if (!bookRepo.authorExists(authorId) || !bookRepo.genreExists(genreId)) {
            String msg = "Author ID or genre ID does not exist.";
            sendResponse(exchange, 400, msg);
        }

        if (datee.isAfter(LocalDate.now())) {
            String errorMessage = "Publication date cannot be in the future.";
            sendResponse(exchange, 400, errorMessage);
        }

        Dummy book = new Dummy(title, authorId, genreId, publicationDate, damaged, repairStatus);

        try {
            service.createBook(book);
            sendResponse(exchange, 201, "book added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error creating book in the database";
            sendResponse(exchange, 500, errorMessage);
        }
    }

    public void handlePutRequest(HttpExchange exchange) throws IOException {
        try {
            int bookIdUrl = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));
            String requestBody = Utils.convertInputStreamToString(exchange.getRequestBody());
            JSONObject json = new JSONObject(requestBody);
            String bookName = json.getString("title");
            int authorId = json.getInt("authorId");
            int genreId = json.getInt("genreId");
            String publicationDate = json.getString("publicationDate");
            String damaged=json.getString("damaged");

            int rowsAffected = service.updateBook(bookIdUrl, bookName, authorId, genreId, publicationDate,damaged);
            String response = (rowsAffected > 0) ? "Book updated successfully" : "Book not found";
            sendResponse(exchange, (rowsAffected > 0) ? 200 : 404, response);
        } catch (SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Error updating book in the database");
        }
    }

    public void handleDeleteRequest(HttpExchange exchange) throws IOException {

        try {
            int bookId = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));

            int rowsAffected = service.deleteBook(bookId);
            String response = (rowsAffected > 0) ? "Book deleted successfully" : "Book not found";
            sendResponse(exchange, (rowsAffected > 0) ? 200 : 404, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Error deleting book from the database");
        }
    }

    public void handleGetRequestById(HttpExchange exchange) throws IOException {
        // String[] parts = exchange.getRequestURI().getPath().split("/");

        int bookId = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));

        try {
            Book book = service.getBookById(bookId);
            if (book != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", book.getId());
                jsonObject.put("title", book.getTitle());
                jsonObject.put("authorId", book.getAuthorId());
                jsonObject.put("genreId", book.getGenreId());
                jsonObject.put("publicationDate", book.getPublicationDate());
                jsonObject.put("damaged", book.getDamaged());
                jsonObject.put("repairStatus", book.getRepairStatus());
                String response = jsonObject.toString();
                sendResponse(exchange, 200, response);
            } else {
                sendResponse(exchange, 404, "Book not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error retrieving book from the database";
            sendResponse(exchange, 500, errorMessage);
        }

    }

    public void handlePostRequestRepair(HttpExchange exchange) throws IOException, SQLException {

        List<Book> books = new ArrayList<>();
        books = service.manageRepair();

        try {           
            service.updateStatus(books);
            String response = "repair status updated.";
            sendResponse(exchange, 201, response);

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error creating book in the database";
            sendResponse(exchange, 400, errorMessage);
        }

    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
