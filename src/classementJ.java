public class classementJ extends Fenetre
{
    public classementJ()
    {
        build();
    }
    void sizeF(){ this.setSize(450,450); }
    void visibility() { this.setVisible(false); } //visible
    void title() { this.setTitle("Classements des joueurs");} //titre
    void build()
    {
        sizeF();
        visibility();
        title();
    }
}
