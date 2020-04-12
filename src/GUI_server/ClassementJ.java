package GUI_server;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ClassementJ extends Classement
{
    private ArrayList<Joueur> liste_joueur; //Liste de Joueurs

    public ClassementJ()  throws IOException
    {
        this.fr = new BufferedReader(new FileReader("C:/Users/Jonathan/IdeaProjects/ProjetFIN/src/Classements/ClassementJ.txt"));
        this.liste_joueur = new ArrayList<Joueur>();
        this.ligne = null;
        Remplir();
        this.bw = new BufferedWriter(new FileWriter("C:/Users/Jonathan/IdeaProjects/ProjetFIN/src/Classements/ClassementJ.txt", false));
        /*
        Le false supprime tout ce qui se trouve dans le
        fichier ici peu importe grace à la méthode Save()
        si on oublie de faire Save() à la fin, les données
        sont perdu
         */
    }

    public void Remplir() throws IOException
    {
        Joueur newJ;
        while ((ligne = this.fr.readLine()) != null)
        {
            StringTokenizer str = new StringTokenizer(ligne);
            newJ = new Joueur(str.nextToken());
            newJ.setIp(str.nextToken());
            newJ.addScore(Long.parseLong(str.nextToken()));
            liste_joueur.add(newJ);
        }
    }

    public void ajoutJoueur(Joueur j) { this.liste_joueur.add(j); }

    public void Save() throws IOException
    {
        for(Joueur j : this.liste_joueur)
        {
            this.bw.write(j.getName()+" "+j.getIp()+" "+j.getScore()+"\n");
        }
        this.bw.close();
        this.fr.close();
    }

    public String afficherClass() throws IOException
    {
        triListeRapide();
        String output = "";
        for(Joueur j : this.liste_joueur)
        {
            output += j.getName()+" à un score de "+j.getScore()+" et son IP est :"+j.getIp()+"\n";
        }
        return output;
    }

    /*
     Choix du triRapide car généralement nous avons peu de joueurs
     */
    public void triListeRapide()
    {
        Joueur Jmax = null;
        int index = 0;
        long Smax = 0;
        for(int i =0; i<this.liste_joueur.size(); i++)
        {
            for(int j = i; j<this.liste_joueur.size(); j++)
            {
                if(this.liste_joueur.get(j).getScore() > Smax)
                {
                    Smax = this.liste_joueur.get(j).getScore();
                    Jmax = this.liste_joueur.get(i);
                    index = j;
                }
            }
            this.liste_joueur.set(i,this.liste_joueur.get(index));
            this.liste_joueur.set(index, Jmax);
            Smax = 0;
        }
    }


}
