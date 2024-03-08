package TEST;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
 
import org.json.JSONObject;
import org.junit.Test;

import API.BookHandler;
 

 
public class BookHandlerTest {
 
    @Test
    public void handleGetRequestByIdTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("GET");
        exchange.setRequestHeader(URI.create("/books/1"));
        BookHandler bookHandler = new BookHandler();
        bookHandler.handleGetRequestById(exchange);
        assertEquals(200, exchange.getResponseCode());
    }
 
    @Test
    public void handlePostRequestTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("POST");
        String jsonPayload = "{\"title\":\"newbook\",\"authorId\":3,\"genreId\":4,\"publicationDate\":\"2024-04-06\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
 
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("title", "NewBook");
        requestBodyJson.put("author_id", 1);
        requestBodyJson.put("genre_id", 2);
        requestBodyJson.put("publication_date", "2022-12-31");
        requestBodyJson.put("dop", "2022-12-31");
        
        String requestBody = requestBodyJson.toString();
        exchange.setRequestBody(requestBody);
        BookHandler bookHandler = new BookHandler();
        bookHandler.handlePostRequest(exchange);
        assertEquals(201, exchange.getResponseCode());
    }
 
    @Test
    public void handlePutRequestTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("PUT");
        String jsonPayload = "{\"title\":\"newbook\",\"authorId\":3,\"genreId\":4,\"publicationDate\":\"2024-04-06\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
        exchange.setRequestHeader(URI.create("/books/28"));
 
        String requestBody = "{" +
                "\"book_name\": \"UpdatedBookof\"," +
                "\"dop\": \"2023-01-15\"," +
                "\"auth_Id\": 3," +
                "\"gen_Id\": 4" +
                "}";
 
        exchange.setRequestBody(requestBody);
 
        BookHandler bookHandler = new BookHandler();
        bookHandler.handlePutRequest(exchange);
        assertEquals(200, exchange.getResponseCode());
 
    }
 
    @Test
    public void handleDeleteRequestTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("DELETE");
        exchange.setRequestHeader(URI.create("/books/40"));
 
        BookHandler bookHandler = new BookHandler();
        bookHandler.handleDeleteRequest(exchange);
 
        assertEquals(200, exchange.getResponseCode());
 
    }
    @Test
    public void handlePostRequestMissingFieldTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("POST");
        String jsonPayload = "{\"authorId\":3,\"genreId\":4,\"publicationDate\":\"2024-04-06\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
    
        BookHandler bookHandler = new BookHandler();
        bookHandler.handlePostRequest(exchange);
        assertEquals(400, exchange.getResponseCode()); // Assuming 400 Bad Request for missing field
    }
    

    @Test
    public void handlePutRequestNonExistingBookTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("PUT");
        String jsonPayload = "{\"title\":\"newbook\",\"authorId\":3,\"genreId\":4,\"publicationDate\":\"2024-04-06\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
        exchange.setRequestHeader(URI.create("/books/100")); 

        BookHandler bookHandler = new BookHandler();
        bookHandler.handlePutRequest(exchange);
        assertEquals(404, exchange.getResponseCode()); 
    }

    @Test
    public void handleDeleteRequestNonExistingBookTest() throws IOException, SQLException {
        TestHttpExchange exchange = new TestHttpExchange();
        exchange.setRequestMethod("DELETE");
        exchange.setRequestHeader(URI.create("/books/23")); 

        BookHandler bookHandler = new BookHandler();
        bookHandler.handleDeleteRequest(exchange);
        assertEquals(404, exchange.getResponseCode()); 
    }
 
}
 