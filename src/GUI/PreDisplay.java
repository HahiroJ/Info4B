import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class PreDisplay extends JFrame
{
    private JTextField username;
    private JButton entrer;
    private JLabel chose_user;
    private JPanel prepane1;
    private JFrame Jmain;

    private PrintWriter pw;

    public PreDisplay(PrintWriter pw, JFrame main)
    {
        this.Jmain = main;
        this.setTitle("CoreWar");
        this.setLayout(new BorderLayout());
        this.username = new JTextField(15);
        this.entrer = new JButton("Entrer dans le serveur de jeu");
        this.chose_user = new JLabel("Pseudo :");
        //TODO Action listener
        this.prepane1 = new JPanel(new GridBagLayout());
        this.setResizable(false);

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prepane1.add(chose_user, preLeft);
        prepane1.add(username, preRight);
        this.add(BorderLayout.CENTER, prepane1);
        this.add(BorderLayout.SOUTH, entrer);
        this.setSize(300, 300);

        this.pw = pw;
        this.setVisible(true);
        entrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsernameSetup();
            }
        });
    }

    public void UsernameSetup()
    {
        if(!(username.getText().equals("")))
        {
            this.setVisible(false);
            pw.println("!pseudo "+username.getText());
            username.setText("");
            if(!(Jmain.isVisible()))
            {
                Jmain.setVisible(true);
            }
            this.getDefaultCloseOperation();

        }
    }
}
