import javax.swing.SwingUtilities;
public final class GUI
{
    /* ---
        Lance le processur d'affichage
        Lancement du jeu
    --- */

    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Fenetre f = new Fenetre();
                f.setVisible(true);
            }
        });
    }
}
