package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Player
 */
public class Player implements Runnable {

    //private int id;
    private String pseudo;
    private Socket socket;
    //private String filePath;
    //private boolean submitWarrior;
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;

    public Player(Socket s) {
        super();
        //this.id = id;
        this.socket = s;
        try {
            this.buffered_reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.print_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        Server.pw.add(this.print_writer);
        try {
            this.pseudo = buffered_reader.readLine();
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getid() {
        return Server.clients.indexOf(this);
    }

    synchronized public void setPseudo(String p) {
        this.pseudo = p;
        Server.pw.get(this.getid()).println("Server Info => Your new nickname is : " + this.pseudo + "#" + this.getid());
    }

    public void sendAll(String message) {
        for (int i = 0; i < Server.clients.size(); i++) {
            if (Server.pw.get(i) != null && i != this.getid()) {
                Server.pw.get(i).println(this.pseudo+"#"+this.getid()+" => "+message);
            }
        }
    }

    //Methode relative aux commandes
        public void help() {
            String h = "Server Help =>\n";
            h+= "~~ CoreWar commands ~~\n";
            h+="!list : return list of players on this server\n";
            h+="!pseudo : change your nickname\n";
            Server.pw.get(this.getid()).println(h);
        }

        public void list() {
            String l = "Server Connected Players =>\n";
            for (Player user : Server.clients) {
                l += "  " + user.getPseudo() + "#" + user.getid() + "\n";
            }
            Server.pw.get(this.getid()).println(l);
        }

        public void privateMSG(int id, String msg) {
            if (id < Server.clients.size()) {
                String mes = "Private message from " + this.pseudo+"#"+this.getid()+" => " + msg;
                Server.pw.get(id).println(mes);
            }
            else {
                Server.pw.get(this.getid()).println("Server Info => This user doesn't exist.");
            }
        }
    //Methode relative aux commandes

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                String mes = this.buffered_reader.readLine();
                System.out.println("Send by " + this.pseudo+"#"+this.getid()+" => "+mes);
                if (mes.equals("!quit")) {
                    for (int i = 0; i < Server.clients.size(); i++) {
                        if (Server.pw.get(i) != null && i != this.getid()) {
                            Server.pw.get(i).println("Server Info => "+this.pseudo+"#"+this.getid()+" is disconnected !");
                        }
                    }
                    break;
                }
                if (mes.startsWith("!")) {
                    Scanner scanner = new Scanner(mes);
                    switch (scanner.next()) {
                        case "!help": {
                            help();
                            break;
                        }
                        case "!msg": {
                            if (scanner.hasNextInt()) {
                                privateMSG(scanner.nextInt(), scanner.nextLine().trim());
                            }
                            else {
                                Server.pw.get(this.getid()).println("Server Info => method arguments incorrect.");
                            }
                            break;
                        }
                        case "!list": {
                            list();
                            break;
                        }
                        case "!pseudo": {
                            if (scanner.hasNext()) {
                                this.setPseudo(scanner.next());
                            }
                            else {
                                Server.pw.get(this.getid()).println("Server Info => method arguments incorrect.");
                            }
                            break;
                        }
                        default:
                        Server.pw.get(this.getid()).println("Server Error => No commands founds. Try !help");
                            break;
                    }
                    scanner.close();
                }
                else {
                    sendAll(mes);
                }
            }
            this.buffered_reader.close();
            this.print_writer.close();
            this.socket.close();
            Server.remove(this.getid());
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}