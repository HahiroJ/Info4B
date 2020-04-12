package GUI_server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Joueur
{
    private String name;
    private String Warriorme;
    private String ip;
    private long score;

    public Joueur(String n)
    {
        this.name = n;
        this.Warriorme = "null";
        this.score = 0;
        this.ip = null;
    }

    public Joueur(String n, String lien)
    {
        this.name = n;
        this.Warriorme = lien;
        this.score = 0;
        this.ip = null;
    }

    public void setName(String n) { this.name = n;}
    public void setWarriorme(String p) { this.Warriorme = p;}
    public void addScore(long s) { this.score += s; }
    public void setIp(String ip){ this.ip = ip;}

    public String getIp() {return this.ip;}
    public String getName() { return this.name;}
    public String getWarriorme() {return this.Warriorme;}
    public long getScore() {return this.score;}
}


