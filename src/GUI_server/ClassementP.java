package GUI_server;

import java.io.*;
import java.util.ArrayList;

public class ClassementP extends Classement
{
    private ArrayList<Warrior> liste_warrior;

    public ClassementP() throws IOException
    {
        this.fr = new BufferedReader(new FileReader("C:/Users/Jonathan/IdeaProjects/ProjetFIN/src/Classements/ClassementP.txt"));
        liste_warrior = new ArrayList<Warrior>();
        Remplir();
        this.bw = new BufferedWriter(new FileWriter("C:/Users/Jonathan/IdeaProjects/ProjetFIN/src/Classements/ClassementP.txt", false));
    }

    public void Save() throws IOException
    {
        this.bw.close();
        this.fr.close();
    }
    public String afficherClass() throws IOException
    {
        return "";
    }

    public void Remplir() throws IOException
    {

    }
}
