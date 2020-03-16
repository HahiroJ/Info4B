public class classementP extends Fenetre
{
    public classementP()
    {
        build();
    }
    void sizeF(){ this.setSize(400,400); }
    void visibility() { this.setVisible(false); } //visible
    void title() { this.setTitle("Classement des programmes");} //titre
    void build()
    {
        sizeF();
        visibility();
        title();
    }
}
