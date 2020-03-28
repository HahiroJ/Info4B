package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    static int port = 8080;
    static int maxClients = 10;
    static boolean help = false;
    //static PrintWriter pw[];
    static LinkedList<PrintWriter> pw;
    //static int idClient;
    static LinkedList<Player> clients;

    static int MEMORY_SIZE = 8000;
    static int MAX_CYCLE = 1000000;
    static int combat = 40;

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p": {
                    port = Integer.parseInt(args[i + 1]);
                    break;
                }
                case "-mC": {
                    maxClients = Integer.parseInt(args[i + 1]);
                    break;
                }
                case "-mS": {
                    MEMORY_SIZE = Integer.parseInt(args[i + 1]);
                }
                case "-cy": {
                    MAX_CYCLE = Integer.parseInt(args[i + 1]);
                }
                case "-nCo": {
                    combat = Integer.parseInt(args[i + 1]);
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
            pw = new LinkedList<PrintWriter>();
            clients = new LinkedList<Player>();
            try {
                ServerSocket server_socket = new ServerSocket(port);
                System.out.println("Socket listen => " + server_socket);
                while (clients.size() < maxClients) {
                    Socket socket = server_socket.accept();
                    clients.add(new Player(socket));
                    Thread client = new Thread(clients.getLast());
                    System.out.println("Player is connected => " + socket);
                    //idClient++;
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

    static synchronized public void remove(int i) {
        pw.remove(i);
        clients.remove(i);
    }

    static public void sendAll(String msg) {
        for (PrintWriter p : pw) {
            p.println("Server Info => "+msg);
        }
    }

    static public void ranking(int i) {
        String s = "~~~ Ranking ~~~\n";
        for (Player player : clients) {
            s += "---> "+player.getPseudo()+"#"+player.getid()+" : "+player.getScore()+"\n";
        }
        s += "~~~ Ranking ~~~\n";
        pw.get(i).println(s); 
    }

    static synchronized public void game() {
        LinkedList<Player> warriors = new LinkedList<Player>();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getSubmit()) {
                warriors.add(clients.get(i));
            }
        }
        if (warriors.size() > 1) {
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).setScore(0);
            }
            Game worker = new Game(MEMORY_SIZE, MAX_CYCLE, combat, warriors);
            Thread war = new Thread(worker);
            war.start();
            try {
                war.join();
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            for (int i = 0; i < clients.size(); i++) {
                ranking(i);
            }
        }
        else {
            System.out.println("Info => just one warrior was submit.");
        }
    }

}