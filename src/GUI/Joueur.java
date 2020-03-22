import java.util.*;
public class Joueur
{
    private String name;
    private String programme;
    private long score;

    public Joueur(String n)
    {
        this.name = n;
        this.programme = "null";
        this.score = 0;
    }

    public Joueur(String n, String lien)
    {
        this.name = n;
        this.programme = lien;
        this.score = 0;
    }

    public void setName(String n) { this.name = n;}
    public void setProgramme(String p) { this.programme = p;}
    public void addScore(long s) { this.score += s; }

    public String getName() { return this.name;}
    public String getProgramme() {return this.programme;}
    public long getScore() {return this.score;}
}


