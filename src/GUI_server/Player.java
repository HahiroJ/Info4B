package GUI_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Player
 */

public class Player implements Runnable {

    //private int id;
    private String ip;
    private String pseudo;
    private Socket socket;
    private String filePath;
    private boolean submitWarrior;
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;
    private int score;

    public Player(Socket s) {
        super();
        //this.id = id;
        this.filePath = "";
        this.submitWarrior = false;
        this.socket = s;
        this.score = 0;
        try
        {
            InetAddress myadress = InetAddress.getLocalHost();
            this.ip = myadress.getHostAddress();
        } catch(UnknownHostException e){e.printStackTrace();}

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

    public String getIP()
    {
        return this.ip;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getId() {
        return Server.clients.indexOf(this);
    }

    public boolean getSubmit() {
        return this.submitWarrior;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int sc) {
        this.score = sc;
    }

    public void addScore(int sc) {
        this.score += sc;
    }

    synchronized public void setPseudo(String p) {
        this.pseudo = p;
        Server.pw.get(this.getId()).println("Server Info => Your new nickname is : " + this.pseudo + "#" + this.getId());
    }

    public void sendAll(String message) {
        for (int i = 0; i < Server.clients.size(); i++) {
            if (Server.pw.get(i) != null && i != this.getId()) {
                Server.pw.get(i).println(this.pseudo+"#"+this.getId()+" => "+message);
            }
        }
    }

    //Methode relative aux commandes
    public void help() {
        String h = "Server Help =>\n";
        h+= "~~ CoreWar commands ~~\n";
        h+="!list : return list of players on this server\n";
        h+="!pseudo : change your nickname\n";//!remove !ranking !warriorsubmit !info ! list
        h+="!remove : ?";
        h+="!warriorsubmit : submit a warrior";
        h+="!info : ?";
        Server.pw.get(this.getId()).println(h);
    }

    public void list() {
        String l = "Server Connected Players =>\n";
        for (Player user : Server.clients) {
            l += "  " + user.getPseudo() + "#" + user.getId() + "\n";
        }
        Server.pw.get(this.getId()).println(l);
    }

    public void privateMSG(int id, String msg) {
        if (id < Server.clients.size()) {
            String mes = "Private message from " + this.pseudo+"#"+this.getId()+" => " + msg;
            Server.pw.get(id).println(mes);
        }
        else {
            Server.pw.get(this.getId()).println("Server Info => This user doesn't exist.");
        }
    }

    public void warriorCreate() throws IOException {
        try{
            String contenu = "";
            while(true)
            {
                String temp = this.buffered_reader.readLine();
                if(temp.equals("!end")){break;}
                contenu += temp+"\n";
            }

            // System.out.println(contenu);  Permet de regarder le contenue de la String
            if(this.filePath == "") {
                int rand =(int) (Math.random()*100+1);
                this.filePath = "C:/Users/Jonathan/IdeaProjects/ProjetFIN/src/DefaultWarrior/"+this.pseudo+"#"+rand+".asm";
            }
            File fichier = new File(this.filePath);
            FileWriter fw =new FileWriter(fichier);
            fw.write(contenu.trim());
            fw.close();
            fichier.createNewFile();
            this.submitWarrior = true;
            Server.pw.get(this.getId()).println("Server Info => Your new warrior is saved.");
        }catch(IOException e){e.printStackTrace();}
        Server.game();
    }

    public void removeWarrior() {
        try {
            if (submitWarrior) {
                File fichier = new File(this.filePath);
                if (fichier.delete()) {
                    this.filePath = "";
                    this.submitWarrior = false;
                    Server.pw.get(this.getId()).println("Server Info => Your warrior was remove.");
                }
            }
            else {
                Server.pw.get(this.getId()).println("Server Info => You haven't saved a warrior.");
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void serverInfo(){
        String s = "\n";
        s += "  Players Connected : " + Server.clients.size() + "/" + Server.maxClients + "\n";
        s += "  Memory Size : " + Server.MEMORY_SIZE + "\n";
        s += "  Max cycle : " + Server.MAX_CYCLE + "\n";
        s += "  number of fights : " + Server.combat + " \n";
        s += "\n";
        Server.pw.get(this.getId()).println("Server Info => " + s);
    }

    //Methode relative aux commandes

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                String mes = this.buffered_reader.readLine();
                System.out.println("Send by " + this.pseudo+"#"+this.getId()+" => "+mes);
                if (mes.equals("!quit")) {
                    for (int i = 0; i < Server.clients.size(); i++) {
                        if (Server.pw.get(i) != null && i != this.getId()) {
                            Server.pw.get(i).println("Server Info => "+this.pseudo+"#"+this.getId()+" is disconnected !");
                            System.out.println("Server Info => "+this.pseudo+"#"+this.getId()+" is disconnected !");
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
                                Server.pw.get(this.getId()).println("Server Info => method arguments incorrect.");
                            }
                            break;
                        }
                        case "!list": {
                            list();
                            break;
                        }
                        case "!info": {
                            this.serverInfo();
                            break;
                        }
                        case "!pseudo": {
                            if (scanner.hasNext()) {
                                this.setPseudo(scanner.next());
                            }
                            else {
                                Server.pw.get(this.getId()).println("Server Info => method arguments incorrect.");
                            }
                            break;
                        }
                        case "!ranking": {
                            Server.ranking(this.getId());
                            break;
                        }
                        case "!remove": {
                            this.removeWarrior();
                            break;
                        }
                        case "!warriorsubmit": {
                            this.warriorCreate();
                            break;
                        }
                        default:
                            Server.pw.get(this.getId()).println("Server Error => No commands founds. Try !help");
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
            this.removeWarrior();
            Server.remove(this.getId());
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}