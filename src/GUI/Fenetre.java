import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class Fenetre extends JFrame implements Runnable
{
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;
    private Menu menu; //La classe qui créer le menu
    private PreDisplay predisplay; //La classe qui créer la partie qui demande le pseudo
    private JFrame preFenetre;
    private JButton entrer;

    public Fenetre(PrintWriter pw)
    {
        this.buffered_reader = new BufferedReader(new InputStreamReader(System.in));
        this.print_writer = pw;
        build();
    }
    void build()
    {
        this.setLayout(new BorderLayout());
        build_CPU();
        build_Chat();

        this.predisplay = new PreDisplay(this.print_writer, this);
        this.menu = new Menu(this.print_writer, this.predisplay);

        this.setJMenuBar(menu.getMB());
        sizeF();
        title();
        this.setBackground(Color.cyan);
    }

    void sizeF(){ this.setSize(1000,1000); }
    void visibility() { this.setVisible(true); } //visible
    void title() { this.setTitle("CoreWar");} //titre

    private void build_Chat() {

        //--Rappel c'est un BorderLayout--
        JPanel right = new JPanel();
        JTextArea jta = new JTextArea();
        GridLayout gl_right = new GridLayout(1, 1);


        JPanel bot = new JPanel();
        bot.setBackground(Color.GRAY);
        JTextField messageBox = new JTextField();
        JButton envoyer = new JButton("Envoyer");

        GridLayout gl_bot = new GridLayout(1, 5);
        bot.setLayout(gl_bot);
        bot.add(messageBox);
        bot.add(envoyer);

        right.setLayout(gl_right);
        right.add(jta);

        this.getContentPane().add(right, BorderLayout.EAST);
        this.getContentPane().add(bot, BorderLayout.SOUTH);
    }

    private void build_CPU()
    {
        ArrayList<JButton> lb = new ArrayList<JButton>();

        //--Rappel c'est un BorderLayout--

        JPanel top = new JPanel();
        GridLayout gl = new GridLayout(64, 125);
        top.setLayout(gl);

        for (int i = 0; i < 8000; i++) {
            lb.add(new JButton(""));
            top.add(lb.get(i));
        }
        this.getContentPane().add(top, BorderLayout.CENTER);
    }

    public void actualiser()
    {
        /* ---
        Actualisation du plateau de jeu
        pour voir les guerriers se deplacer
        --- */

    }


    @Override
    public void run()
    {
        predisplay.setVisible(true);
    }
}

