import javax.swing.JFrame;

abstract class Fenetre extends JFrame
{
    boolean vis; //Pour gérer la visibilité de la fenêtre plus tard

    public Fenetre()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    abstract void sizeF(); //size frame
    abstract void visibility(); //visible
    abstract void title(); //titre
    abstract void build(); //Construction plus complexe
}
