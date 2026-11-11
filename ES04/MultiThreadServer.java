import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** Listener sulla porta 5000: accetta un socket distinto e lo trasferisce a un Runnable. */
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(5000)) {
            System.out.println("Server in ascolto sulla porta 5000");
            while (true) {
                Socket socket = listener.accept(); // Solo questa attesa riguarda il listener.
                new Thread(new ClientTask(socket)).start();
            }
        }
    }
}
