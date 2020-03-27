import java.util.*;
public class Joueur
{
    private String name;
    private String Warriorme;
    private long score;

    public Joueur(String n)
    {
        this.name = n;
        this.Warriorme = "null";
        this.score = 0;
    }

    public Joueur(String n, String lien)
    {
        this.name = n;
        this.Warriorme = lien;
        this.score = 0;
    }

    public void setName(String n) { this.name = n;}
    public void setWarriorme(String p) { this.Warriorme = p;}
    public void addScore(long s) { this.score += s; }

    public String getName() { return this.name;}
    public String getWarriorme() {return this.Warriorme;}
    public long getScore() {return this.score;}
}


