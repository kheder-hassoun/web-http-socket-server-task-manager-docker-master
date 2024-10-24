package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import singleton.TaskExecutor;
import singleton.TasksList;

import java.io.*;

public class RequestsHandlerHttp implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        int statusCode = 200;

        try {
            String requestMethod = httpExchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(requestMethod)) {
                response = TasksList.INSTANCE.list();
            } else if ("POST".equalsIgnoreCase(requestMethod)) {
                response = handlePostRequest(httpExchange);
            } else {
                response = "Unsupported request method: " + requestMethod;
                statusCode = 405; // Method Not Allowed
            }
        } catch (Exception e) {
            response = "Internal server error: " + e.getMessage();
            statusCode = 500; // Internal Server Error
            e.printStackTrace();
        } finally {
            sendResponse(httpExchange, response, statusCode);
        }
    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {
        Headers requestHeaders = httpExchange.getRequestHeaders();
        StringBuilder params = new StringBuilder();

        // Using try-with-resources to ensure stream is closed automatically
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
            reader.lines().forEach(params::append);
        }

        System.out.println("New request from " + requestHeaders.getFirst("User-Agent") + " -> " + params);
        return TaskExecutor.INSTANCE.run(params.toString());
    }

    private void sendResponse(HttpExchange httpExchange, String response, int statusCode) throws IOException {
        byte[] responseBytes = response.getBytes();
        httpExchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
