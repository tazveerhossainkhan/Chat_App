import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            new Thread(new ServerListener(socket)).start();

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your name:");
            String name = userInput.readLine();
            out.println(name);

            String inputLine;
            while ((inputLine = userInput.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("quit")) {
                    out.println(inputLine);
                    break;
                }
                out.println(inputLine);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
