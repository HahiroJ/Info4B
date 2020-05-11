import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SwingUtilities;


public class Fenetre extends JFrame implements Runnable
{
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;
    private Menu menu; //La classe qui créer le menu
    private PreDisplay predisplay; //La classe qui créer la partie qui demande le pseudo
    private JFrame preFenetre;
    private JButton entrer;
    private JTextArea chatBox;


    public Fenetre()
    {
        this.buffered_reader = null;
        this.print_writer = null;
        build();
    }

    public Fenetre(PrintWriter pw)
    {
        this.buffered_reader = new BufferedReader(new InputStreamReader(System.in));
        this.print_writer = pw;
        build();
    }
    void build()
    {
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
       // build_CPU();
        build_Chat();

        this.predisplay = new PreDisplay(this.print_writer, this);
        this.menu = new Menu(this.print_writer, this.predisplay, this.chatBox);

        this.setJMenuBar(menu.getMB());
        sizeF();
        title();
        this.setBackground(Color.cyan);
    }

    void sizeF(){ this.setSize(500,500); }
    void visibility() { this.setVisible(true); } //visible
    void title() { this.setTitle("CoreWar");} //titre

    private void build_Chat() {

        //--Rappel c'est un BorderLayout--
        JTextField messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        JButton sendMessage = new JButton("Envoyer");

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());

        GridBagConstraints left = new GridBagConstraints();
        left.insets = new Insets(0, 10, 0, 0);
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(messageBox.getText().length() >= 1)
                {
                    print_writer.println(messageBox.getText());
                    chatBox.append("\n"+messageBox.getText());
                    messageBox.setText("");
                }
            }
        }); //Ligne qui appelle le constructeur qui permet les DataEntry

        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(new JScrollPane(chatBox), BorderLayout.CENTER);


    }

    private void build_CPU()
    {
        ArrayList<JButton> lb = new ArrayList<JButton>();

        JPanel top = new JPanel();
        JPanel top_mid = new JPanel();

        GridLayout gl = new GridLayout(64, 125);

        top.setLayout(new BorderLayout());
        top_mid.setLayout(gl);

        for (int i = 0; i < 8000; i++) {
            lb.add(new JButton(""));
            top_mid.add(lb.get(i));
        }

        top.add(top_mid, BorderLayout.CENTER);
        top.setBackground(Color.GRAY);
        this.getContentPane().add(top, BorderLayout.CENTER);

    }

    public void ReadServ(String msg)
    {
        this.chatBox.append("\n"+msg);
    }

    public void actualiser()
    {
        /* ---
        Actualisation du plateau de jeu
        pour voir les guerriers se deplacer
        --- */

    }

    public void sendMessageButtonListener()
    {

    }


    @Override
    public void run()
    {
        predisplay.setVisible(true);
        String mes;
        try {
            while (!(mes = this.buffered_reader.readLine()).equals("!quit"))
            {
                    this.print_writer.println(mes);
            }
           // this.print_writer.println("!quit");
            this.buffered_reader.close();
            this.print_writer.close();
            System.exit(0);
        } catch (IOException e) {  e.printStackTrace();}
        Client.stop = true;
    }

}

