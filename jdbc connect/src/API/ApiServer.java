package API;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ApiServer {

    public static void main(String[] args) throws IOException {
        ResourceBundle rd
            = ResourceBundle.getBundle("resource.system", Locale.US);
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(rd.getString("portNumber"))), 0);
        server.createContext("/books", new BookHandler());
        server.createContext("/books/repair",new RepairController());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}

