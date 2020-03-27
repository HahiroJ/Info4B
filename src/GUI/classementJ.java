import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class ClassementJ
{
    BufferedReader fr;
    BufferedWriter bw;
    ArrayList<Joueur> lj;
    String ligne;

    public ClassementJ(String s)  throws IOException
    {

        this.fr = new BufferedReader(new FileReader(s));
        this.bw = new BufferedWriter(new FileWriter(s, true));
        this.lj = new ArrayList<Joueur>();
        this.ligne = null;
        remplirLJ();
    }

    public void remplirLJ() throws IOException
    {
        Joueur newJ;
        while ((ligne = this.fr.readLine()) != null)
        {
            StringTokenizer str = new StringTokenizer(ligne);
            newJ = new Joueur(str.nextToken());
            newJ.setWarriorme(str.nextToken());
            newJ.addScore(Long.parseLong(str.nextToken()));
            lj.add(newJ);
        }
    }

    public void ajoutJoueur(Joueur j) throws IOException
    {
        this.lj.add(j);
        this.bw.write("\n"+j.getName()+" "+j.getWarriorme()+" "+j.getScore());
        this.bw.close();
    }

    public void afficherClassement() throws IOException
    {
        triListeRapide(this.lj);
        for(Joueur j : this.lj)
        {
            System.out.println(j.getName()+" avec le Warriorme "+j.getWarriorme()+" a un score de "+j.getScore());
        }
        this.fr.close();
    }
/*
 Choix du triRapide car généralement nous avons peu de joueurs
 */
    public void triListeRapide(ArrayList<Joueur> listJ)
    {
        Joueur Jmax = null;
        int index = 0;
        long Smax = 0;
        for(int i =0; i<listJ.size(); i++)
        {
            for(int j = i; j<listJ.size(); j++)
            {
                if(this.lj.get(j).getScore() > Smax)
                {
                    Smax = this.lj.get(j).getScore();
                    Jmax = this.lj.get(i);
                    index = j;
                }
            }
            this.lj.set(i,this.lj.get(index));
            this.lj.set(index, Jmax);
            Smax = 0;
        }
    }
}
