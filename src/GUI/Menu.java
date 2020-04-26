package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javax.swing.*;

public class Menu extends JMenuBar implements ActionListener
{
    /*
    Class qui initialise le menu et tout ses composants, c'est la classe
    moteur de notre Client et qui va mettre en relation toute les autres
    classes pour permettre au GUI d'être propre
    */

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

    private Boolean warrior;

    //Ci-dessus la nomenclature du menu

    public Menu(PrintWriter pw, PreDisplay pd)
    {
        /*
        Constructeur du menu, ici on initialise tout les composants
        du menu et on créer des instances pour toute nos variables
         */

        mb = new JMenuBar();
        this.pw = pw;
        this.pd = pd;
        this.warrior = false; //Utile par la suite pour vérifier si un guerrier à été soumis

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
        /*
        Classe très importante qui va gérer la navigation et les actions
        d'un click de souris sur une partie spécifique du menu
         */
        Object source = e.getSource();

        if(source == warriorsubmit) //Soumission d'un guerrier
        {
            //----Début de la construction d'une JDialog
            //----pour la soumission + nomenclature
            JDialog jd = new JDialog();
            jd.setModal(true);
            jd.setLayout(new BorderLayout());
            JFileChooser JF = new JFileChooser();
            JButton submit = new JButton("Choisir ce guerrier");
            JLabel chose_user = new JLabel("Sélectionner un guerrier :");
            JPanel prepane1 = new JPanel(new GridBagLayout());
            jd.setResizable(false);

            GridBagConstraints preRight = new GridBagConstraints();
            preRight.insets = new Insets(0, 0, 0, 10);
            preRight.anchor = GridBagConstraints.EAST;
            GridBagConstraints preLeft = new GridBagConstraints();
            preLeft.anchor = GridBagConstraints.WEST;
            preLeft.insets = new Insets(0, 10, 0, 10);
            preRight.fill = GridBagConstraints.HORIZONTAL;
            preRight.gridwidth = GridBagConstraints.REMAINDER;

            prepane1.add(chose_user, preLeft);
            prepane1.add(JF, preRight);
            jd.add(BorderLayout.CENTER, prepane1);
            jd.add(BorderLayout.SOUTH, submit);
            jd.setSize(700, 300);

            //----Fin de la construction de la JDialog

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    /*
                    Lorsqu'on appuie sur le bouton après avoir sélectionner notre guerrier
                    envoie au serveur la commande pour ajouter un guerrier à la partie
                     */
                    //TODO Vérfier que le fichier est compilable pour savoir si c'est un guerrier utilisable, sinon, non validation
                    pw.println("!warriorsubmit "+JF.getSelectedFile().getAbsolutePath());
                    jd.dispose();
                }
            });

            this.warrior = true;
            jd.setVisible(true);
        }
        else if(source == warriorremove)
        {
            pw.println("!remove");

            if(this.warrior)
            {
            }
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
            pw.println("!ranking");
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
