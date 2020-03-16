import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CoreWar extends Fenetre
{
    private CentralPU object;

    public CoreWar()
    {
        build();
    }
    public CoreWar(CentralPU cpu)
    {
        this.object = cpu;
        build();
    }
    void build()
    {
        this.setLayout(new BorderLayout());
        if(object != null)
        {
            build_CPU();
        }
        menu();
        sizeF();
        visibility();
        title();
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
    }

    private void build_CPU()
    {
        ArrayList<JButton> lb = new ArrayList<JButton>();

        //--Rappel c'est un BorderLayout--

        JPanel top = new JPanel();
        GridLayout gl = new GridLayout(64, 125);
        top.setLayout(gl);

        System.out.println("test : "+this.object.getCPU().size());
        for(int i = 0; i< this.object.getCPU().size(); i++)
        {
            lb.add(new JButton(""+  this.object.getCPU().get(i) ));
            top.add(lb.get(i));
        }
        this.getContentPane().add(top, BorderLayout.NORTH);
    }
}
