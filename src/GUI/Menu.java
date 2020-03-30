import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

public class Menu extends JMenuBar implements ActionListener
{

    private JMenuBar mb;

    private JMenu menu1;
    private JMenuItem warriorsubmit;
    private JMenuItem warriorremove;

    private JMenu menu2;
    private JMenuItem changeUSER;
    private JMenuItem info;
    private JMenuItem list;
    private JMenu sousmenu2;
        private JMenuItem rankJ;
        private JMenuItem rankP;

    private JMenu menu3;
    private JMenuItem help;

    private PrintWriter pw;
    private PreDisplay pd;

    public Menu(PrintWriter pw, PreDisplay pd)
    {
        mb = new JMenuBar();
        this.pw = pw;
        this.pd = pd;

        menu1 = new JMenu("Partie");
        //--------
        warriorsubmit = new JMenuItem("Soumettre un Guerrier");
        warriorsubmit.addActionListener(this);

        warriorremove = new JMenuItem("Supprimer un Guerrier");
        warriorremove.addActionListener(this);

        menu1.add(warriorsubmit); menu1.add(warriorremove);

        menu2 = new JMenu("Informations");
        //--------
        changeUSER = new JMenuItem("Changer pseudo");
        changeUSER.addActionListener(this);

        info = new JMenuItem("Info du serveur");
        info.addActionListener(this);

        list = new JMenuItem("Liste des joueurs");
        list.addActionListener(this);

        sousmenu2 = new JMenu("Classements");
            //---------
            rankJ = new JMenuItem("Joueurs");
            rankJ.addActionListener(this);

            rankP = new JMenuItem("Programmes");
            rankP.addActionListener(this);

        sousmenu2.add(rankJ); sousmenu2.add(rankP);
        menu2.add(changeUSER); menu2.add(info); menu2.add(list); menu2.add(sousmenu2);

        menu3 = new JMenu("Aide");
        //--------
        help = new JMenuItem("Liste des commandes");
            help.addActionListener(this);
        menu3.add(help);

        mb.add(menu1); mb.add(menu2); mb.add(menu3);

    }

    public JMenuBar getMB(){return this.mb;}
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if(source == warriorsubmit)
        {

        }
        else if(source == warriorremove)
        {

        }
        else if(source == info)
        {
            pw.println("!info");
        }
        else if(source == list)
        {
            pw.println("!list");
        }
        else if(source == rankJ)
        {
            pw.println("!rank");
        }
        else if(source == rankP)
        {

        }
        else if(source == help)
        {
            pw.println("!help");
        }
        else if(source == changeUSER)
        {
            this.pd.setVisible(true);
        }
    }
}
