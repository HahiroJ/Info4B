package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.nio.file.Paths;

public class Server {

    static int port = 8080;
    static int maxClients = 10;
    static boolean help = false;
    //static PrintWriter pw[];
    static LinkedList<PrintWriter> pw;
    //static int idClient;
    static LinkedList<Player> clients;
    //static HashMap<String,String> fileCache = new HashMap<>();
    static FileCache cache;

    static int MEMORY_SIZE = 8000;
    static int MAX_CYCLE = 1000000;
    static int combat = 20;
    
    static String path = Paths.get("").toAbsolutePath().toString();
    //static String path = System.getProperty("user.dir");
    static String rankPath = "rank.txt";

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
                    break;
                }
                case "-cy": {
                    MAX_CYCLE = Integer.parseInt(args[i + 1]);
                    break;
                }
                case "-nCo": {
                    combat = Integer.parseInt(args[i + 1]);
                    break;
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
            cache = FileCache.getInstance();
            try {
                System.out.println("~~~ CoreWar Game Server Start ~~~");
                System.out.println("Info => Working Directory : " + path);
                ServerSocket server_socket = new ServerSocket(port);
                System.out.println("Socket listen => " + server_socket);
                System.out.println("Info => Server is ready !");
                String s = "\n";
                s += "Players Connected : " + clients.size() + "/" + maxClients + "\n";
                s += "Memory Size : " + MEMORY_SIZE + "\n";
                s += "Max cycle : " + MAX_CYCLE + "\n";
                s += "number of fights between two warrior : " + combat + " \n";
                s += "\n";
                System.out.println("Info => " + s);
                System.out.println("To change parameters restart server with new parameters. use --help for more informations.\n");
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
            System.out.println("-p [port] : set port of server");
            System.out.println("-mC [maxClient] : set max clients of server");
            System.out.println("-mS [MEMORY SIZE] : set memory size of the corewar game");
            System.out.println("-cy [MAX CYCLE] : set max cycle of corewar game");
            System.out.println("-nCo [combats] : set numbers of combat between two warrior");
        }

    }

    static synchronized public void remove(int i) {
        pw.remove(i);
        clients.remove(i);
        //rankFile();
    }

    static public void sendAll(String msg) {
        for (PrintWriter p : pw) {
            p.println("Server Info => "+msg);
        }
    }

    /*synchronized static public String getFileCache(String key) {
        if (fileCache.containsKey(key)) {
            return fileCache.get(key);
        }
        else {
            System.err.println("Error, files doesn't exit in cache");
            return null;
        }
    }*/

    static public void ranking(int i) {
        String temp = "";
        
        if ((temp = cache.getFile(rankPath)) == null) {
            temp = "Rank File doesn't exist ! \n";
        } 

        pw.get(i).println("Server Info Ranking => \n" + temp); 
    }

    synchronized static public void rankFile() {
        int maxP =  0;
        for (Player player : clients) {
            String temp = "" + player.getPseudo()+"#"+player.getid();
            maxP = (temp.length() > maxP - 1) ? temp.length() + 1 : maxP; 
        }
        String sep = "+";
        for (int i = 0; i < maxP; i++) {
            sep += "-";
        }
        sep += "+----+\n";
        String fin = sep;
        for (Player player : clients) {
            String tempP = "" + player.getPseudo()+"#"+player.getid();
            String tempS = "" + player.getScore();
            String tempF = "|"+tempP;
            for (int i = tempP.length(); i < maxP; i++) {
                tempF += " ";
            }
            tempF += "|";
            for (int i = tempS.length(); i < 4; i++) {
                tempF += " ";
            }
            tempF += tempS+"|\n";
            fin += tempF;
            fin += sep;
        }

        try {
            File fichier = new File(rankPath);
            FileWriter fw =new FileWriter(fichier);
            fw.write(fin.trim());
            fw.close();
            fichier.createNewFile();
            cache.put(rankPath, fin.trim());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
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
            rankFile();
            for (int i = 0; i < clients.size(); i++) {
                ranking(i);
            }
        }
        else {
            System.out.println("Info => just one warrior was submit.");
        }
    }

}