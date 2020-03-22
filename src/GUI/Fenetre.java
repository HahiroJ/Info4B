import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Fenetre extends JFrame
{
    private final Actu actualiseur = new Actu();
    //private Menu menu;

    public Fenetre()
    {
        build();
    }

    void build()
    {
        /*this.setLayout(new BorderLayout());
        if(object != null)
        {
            build_CPU();
        }*/
        menu();
        sizeF();
        title();
        this.setBackground(Color.cyan);
    }

    void sizeF(){ this.setSize(1000,1000); }
    void visibility() { this.setVisible(true); } //visible
    void title() { this.setTitle("CoreWar");} //titre

    private void menu()
    {
        JMenuBar mb = new JMenuBar();
        JMenuItem i11, i12, i21, i22;
        JMenu menu1 = new JMenu("Partie");

        i11 = new JMenuItem("Nouvelle Partie");
        i12 = new JMenuItem("Créer un nouveau guerrier");
        menu1.add(i11); menu1.add(i12);

        JMenu menu2 = new JMenu("Classements");

        i21 = new JMenuItem("Classement des joueurs");
        i22 = new JMenuItem("Classement des programmes");
        menu2.add(i21); menu2.add(i22);

        JMenu menu3 = new JMenu("En savoir plus");

        mb.add(menu1); mb.add(menu2); mb.add(menu3);
        this.setJMenuBar(mb);
        this.setBackground(Color.cyan);
    }

    private void build_CPU() {
        ArrayList<JButton> lb = new ArrayList<JButton>();

        //--Rappel c'est un BorderLayout--

        JPanel top = new JPanel();
        GridLayout gl = new GridLayout(64, 125);
        top.setLayout(gl);

        for (int i = 0; i < 8000; i++) {
            lb.add(new JButton(""));
            top.add(lb.get(i));
        }
        this.getContentPane().add(top, BorderLayout.NORTH);
    }

    public void actualiser()
    {
        /* ---
        Actualisation du plateau de jeu
        pour voir les guerriers se deplacer
        --- */

    }

    /* ---
        Début de la config du GUI
     --- */

    class Actu implements Runnable
    {
        /* ---
            Actualisation de la page JFrame
         --- */

        private boolean running;
        private int delay;

        public Actu()
        {
            delay = 10;
        }

        public void setDelay(int d)
        {
            this.delay = d;
        }

        @Override
        public void run()
        {
            while(running)
            {
                actualiser();
                try
                {
                    Thread.sleep(delay);
                }catch(InterruptedException e){interrupt();}
            }
        }

        public void start()
        {
            Thread a = new Thread(null, this);
            running = true;
            a.start();
        }

        /*
            Appuyer sur des boutons
         */

        public void interrupt()
        {
            Thread.currentThread().interrupt();
        }
    }
}
