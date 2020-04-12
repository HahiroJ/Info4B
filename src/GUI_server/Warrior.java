package GUI_server;

public class Warrior
{
    private String name;
    private long score;
    private int victoire;

    public Warrior(String n)
    {
        this.name = n;
        this.score = 0;
        this.victoire = 0;
    }

    public Warrior(String n, long s, int v)
    {
        this.name = n;
        this.score = s;
        this.victoire = v;
    }

    public void setName(String s) { this.name = s;}
    public void setScore(long l) {this.score = l;}
    public void setVictoire(int i) {this.victoire = i;}

    public String getName(){return this.name;}
    public long getScore(){return this.score;}
    public int getVictoire(){return this.victoire;}

}
