import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<Socket> clients = new ArrayList<>();
    private static List<String> clientNames = new ArrayList<>();
    private static List<String> chatHistory = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);

                // Send chat history to new client
                sendChatHistoryToClient(clientSocket);

                // Send message to inform other clients about new user
                broadcast("[SERVER] New user joined the chat.");

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        System.out.println(message);
        chatHistory.add(message);
        for (Socket client : clients) {
            try {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendChatHistoryToClient(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        for (String message : chatHistory) {
            out.println(message);
        }
    }

    public static void addClientName(String name) {
        clientNames.add(name);
    }

    public static void removeClient(Socket clientSocket) {
        int index = clients.indexOf(clientSocket);
        if (index != -1) {
            String name = clientNames.get(index);
            broadcast("[SERVER] " + name + " left the chat.");
            clients.remove(index);
            clientNames.remove(index);
        }
    }
}
