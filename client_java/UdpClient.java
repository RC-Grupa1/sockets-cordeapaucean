import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) {
        String SERVER_IP = "100.116.195.63";
        int PORT = 5001;

        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)
        ) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            System.out.println("Client UDP pornit.");
            System.out.println("Conectare catre serverul UDP: " + SERVER_IP + ":" + PORT);
            System.out.println("Scrie exit pentru inchidere.");

            while (true) {
                System.out.print("Client: ");
                String mesajClient = scanner.nextLine();

                byte[] sendBuffer = mesajClient.getBytes(StandardCharsets.UTF_8);

                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        serverAddress,
                        PORT
                );

                socket.send(sendPacket);

                if (mesajClient.equalsIgnoreCase("exit")) {
                    System.out.println("Clientul a inchis conversatia.");
                    break;
                }

                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                );

                socket.receive(receivePacket);

                String mesajServer = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength(),
                        StandardCharsets.UTF_8
                ).trim();

                System.out.println("Server: " + mesajServer);

                if (mesajServer.equalsIgnoreCase("exit")) {
                    System.out.println("Serverul a inchis conversatia.");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Eroare UDP: " + e.getMessage());
        }
    }
}