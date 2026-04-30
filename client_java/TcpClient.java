
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        String SERVER_IP = "100.116.195.63";
        int PORT = 5000;

        try (
                Socket socket = new Socket(SERVER_IP, PORT);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
                );
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectat la serverul TCP.");

            while (true) {
                System.out.print("Client: ");
                String mesajClient = scanner.nextLine();

                out.write(mesajClient);
                out.newLine();
                out.flush();

                if (mesajClient.equalsIgnoreCase("exit")) {
                    System.out.println("Clientul a inchis conversatia.");
                    break;
                }

                String mesajServer = in.readLine();

                if (mesajServer == null) {
                    System.out.println("Serverul s-a deconectat.");
                    break;
                }

                System.out.println("Server: " + mesajServer);

                if (mesajServer.equalsIgnoreCase("exit")) {
                    System.out.println("Serverul a inchis conversatia.");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Eroare TCP: " + e.getMessage());
        }
    }
}