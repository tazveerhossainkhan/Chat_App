import java.io.*;
import java.net.*;

public class ServerListener implements Runnable {
    private Socket socket;

    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
