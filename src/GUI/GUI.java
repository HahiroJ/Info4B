import CoreWarGUI.Combat;
import MARS_CORE.Process;
import MARS_CORE.CPU;
import MARS_CORE.Register;
import MARS_CORE.Warrior;
//import GUI_server.Server;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.concurrent.Flow;

public class GUI extends JFrame
{
    /*
    Classe qui permet de créer la fenêtre de jeu
    et qui contiendra toutes les informations à
    la partie
     */

    private Combat fight;

    private int cycle;
    private int size;
    private JLabel[] plateau;
    private JPanel informations;

    public GUI(Combat f)
    {
        this.fight = f;
        this.size = f.getMemSize();
        this.cycle = this.size;
        build();
    }

    private void build()
    {
        this.setSize(1520,980);
        JPanel game = new JPanel();
        game.setBackground(Color.lightGray);
            GridLayout gl_game = new GridLayout(25, 32);
        game.setLayout(gl_game);

        this.informations = new JPanel();
            GridLayout gl = new GridLayout(2,2);
        this.informations.setLayout(gl);
       // buildMenu();
        buildP1();
        buildP2();
        buildInfGame();

        this.plateau = new JLabel[size];

        for(int i = 0; i < size; i++)
        {
            String s = "<html><div style=\"width:70; height:15; border:solid 1px black; text-align: left;\"><font face = \"Times New Roman\" size = \"2\" color = \""

                    + "black"

                    + "\">"

                    + "DAT#0#0"

                    + "</font></div></html>";

            this.plateau[i] = new JLabel(s);
            game.add(this.plateau[i]);
        }


        this.add(game, BorderLayout.CENTER);
        this.add(informations, BorderLayout.SOUTH);
        this.informations.setBackground(Color.lightGray);
    }

   /*private void buildMenu()
    {
        JMenuBar menu = new JMenuBar();
            JMenu options = new JMenu("Options");
                JMenuItem x05 = new JMenuItem("x0.5");
                JMenuItem x2 = new JMenuItem("x2");
                JMenuItem pause_reprendre = new JMenuItem("Pause/Reprendre");
                JMenuItem fin = new JMenuItem("Aller à la fin");

        options.add(x05); options.add(x2); options.add(pause_reprendre); options.add(fin);
        menu.add(options);
    }*/

    private void buildP1()
    {
        JPanel joueur1 = new JPanel();
            FlowLayout glp1 = new FlowLayout();
            joueur1.setLayout(glp1);
            joueur1.setBackground(Color.lightGray);
        joueur1.add(new JLabel("Joueur 1 :"+fight.getWar1().toString())); //TODO nom
        //joueur1.add(new JLabel("Dernier cycle vu :")); //TODO
        this.informations.add(joueur1);
    }

    private void buildP2()
    {
        JPanel joueur2 = new JPanel();
            FlowLayout glp2 = new FlowLayout();
            joueur2.setLayout(glp2);
            joueur2.setBackground(Color.lightGray);
        joueur2.add(new JLabel("Joueur 2 :"+fight.getWar2().toString())); //TODO nom
        //joueur2.add(new JLabel("Dernier cycle vu :")); //TODO
        this.informations.add(joueur2);
    }

    private void buildInfGame()
    {
        JLabel jcycle = new JLabel("Cycle restant : "+this.cycle);
        this.informations.add(jcycle);
    }

    private void cycleMAJ(int c)
    {
        Component[] components = this.informations.getComponents();

        for(int i=0; i<components.length;i++)
        {
            if(components[i] instanceof JLabel)
            {
                JLabel jl = (JLabel)components[i];
                jl.setText("Cycles restant :"+(int)(8000-c));
            }
        }
    }

    public void actu(Process[] memoire, int c, LinkedList<Register> war)
    {
        for(int i = 0; i<size; i++)
        {
            synchronized (this)
            {
                String s = "<html><div style=\"width:70; height:15; border:solid 1px black; text-align: left;\"><font face = \"Times New Roman\" size = \"2\" color = \""

                        + "black"

                        + "\">"

                        + memoire[i].toString()

                        + "</font></div></html>";
                this.plateau[i].setText(s);
                //System.out.println(memoire[i].toString()+" "+i);
                /*float  a = (float)(new Random()).nextInt(255);
                float  b = (float)(new Random()).nextInt(255);
                float  c = (float)(new Random()).nextInt(255);
                this.plateau[i].setBackground(Color.getHSBColor(a,b,c));*/
                this.plateau[i].setBackground(Color.lightGray);
                this.plateau[i].setOpaque(true);
                cycleMAJ(c);
            }

            this.plateau[war.getLast().getAdress()].setBackground(Color.BLUE);
            this.plateau[i].repaint();
        }
        //System.out.println(memoire[0]);
    }
    /*for(int i = 0; i<size; i++)
        {
            this.plateau[i].setText(memory[i].toString());
        }*/
}
