import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Port the server will listen on
    private static final int PORT = 5000;

    // Start the server
    public static void main(String[] args) {

        System.out.println("Server starting on port " + PORT + "...");

        // Open the server and wait for clients to connect
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server is running. Waiting for clients...");

            // Keep looping so the server can accept multiple clients
            while (true) {

                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Spin up a new thread for this client so others aren't blocked
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Handles communication with a single connected client
    private static void handleClient(Socket clientSocket) {

        // Create one ClientHandler to route this client's requests to the database
        ClientHandler handler = new ClientHandler();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestLine;

            // Keep reading requests from the client until they disconnect
            while ((requestLine = input.readLine()) != null) {

                System.out.println("Request received: " + requestLine);

                // Send the request to ClientHandler and get the database response
                String response = handler.handleRequest(requestLine);

                // Replace newlines so the whole response sends as one line
                writer.println(response.replace("\n", " | "));
            }

        } catch (IOException e) {

            System.out.println("Client error: " + e.getMessage());

        } finally {

            // Always close the socket when the client disconnects
            try {
                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
