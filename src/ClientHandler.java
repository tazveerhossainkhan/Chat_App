import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Ask user's name
            out.println("Enter your name:");
            String name = in.readLine();
            Server.addClientName(name);
            Server.broadcast("[SERVER] " + name + " joined the chat.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Server.broadcast("[" + name + "]: " + inputLine);
            }

            Server.removeClient(clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
