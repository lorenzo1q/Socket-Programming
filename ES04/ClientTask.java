import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** Gestore a righe UTF-8: il socket è chiuso automaticamente anche su errore o timeout. */
public class ClientTask implements Runnable {
    private final Socket socket;
    public ClientTask(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try (Socket owned = socket) {
            socket.setSoTimeout(30_000); // Limita le letture di un client inattivo.
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            System.out.println("Gestore: " + Thread.currentThread().getName());
            String message = in.readLine();
            if (message == null) return; // EOF senza richiesta: non rispondere al testo "null".
            out.println("Server: Ho ricevuto il tuo messaggio: " + message);
        } catch (IOException e) {
            System.err.println("Errore gestore: " + e.getMessage());
        }
    }
}
