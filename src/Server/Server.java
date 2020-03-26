package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static int port = 8080;
    static int maxClients = 10;
    static boolean help = false;
    static PrintWriter pw[];
    static int idClient;

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p": {
                    port = Integer.parseInt(args[i + 1]);
                    break;
                }
                case "-nC": {
                    maxClients = Integer.parseInt(args[i + 1]);
                }
                case "-h":
                case "--help": {
                    help = true;
                    break;
                }
                default: {
                    System.out.println("Error, No arguments found.");
                    break;
                }
            }
        }

        if (!help) {
            pw = new PrintWriter[maxClients];
            try {
                ServerSocket server_socket = new ServerSocket(port);
                System.out.println("Socket listen => " + server_socket);
                while (idClient < maxClients) {
                    Socket socket = server_socket.accept();
                    Thread client = new Thread(new Player(idClient,socket));
                    System.out.println("Player is connected => " + socket);
                    idClient++;
                    client.start();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        else {
            System.out.println("~~ CoreWar Game Server ~~");
        }

    }

}