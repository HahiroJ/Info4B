package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Player
 */
public class Player implements Runnable {

    private int id;
    private String pseudo;
    private Socket socket;
    private String ip;
    //private String filePath;
    //private boolean submitWarrior;
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;

    public Player(int id, Socket s) {
        super();
        this.id = id;
        this.socket = s;
        try {
            this.buffered_reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.print_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        Server.pw[this.id] = this.print_writer;
        this.ip = this.socket.getRemoteSocketAddress().toString();
        try {
            this.pseudo = buffered_reader.readLine();
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void sendAll(String message) {
        for (int i = 0; i < Server.idClient; i++) {
            if (Server.pw[i] != null && i != this.id) {
                Server.pw[i].println(this.pseudo+"#"+this.id+" => "+message);
            }
        }
    }

    //Methode relative aux commandes
    
    //Methode relative aux commandes

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                String mes = this.buffered_reader.readLine();
                System.out.println("Send by " + this.pseudo+"#"+this.id+" => "+mes);
                if (mes.equals("END")) {
                    break;
                }
                sendAll(mes);
            }
            this.buffered_reader.close();
            this.print_writer.close();
            this.socket.close();
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}