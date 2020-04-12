package GUI_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

abstract public class Classement
    /*
    Classe qui gère la lecture/écriture et
    contient les différentes méthodes pour
    créer/éditer/sauvegarder un classement
    de Joueurs de CoreWar
     */
{
    protected BufferedReader fr; //Lecture
    protected BufferedWriter bw; //Ecriture

    String ligne;
    //Variable pour la lecture de fichier ligne par ligne

    public Classement()
    {
        this.ligne = null;
    }

    abstract void Save() throws IOException;
    /*
        Appelé à la fin, cette méthode sauvegarde de la
        façon qu'on veut les données pour être utilisé
        lors d'une autre ouverture
         */

    abstract String afficherClass() throws IOException;
    /*
    Affiche le classement
     */

    abstract void Remplir() throws IOException;

    /*
        Convertie les lignes du fichier texte en données
        utilisable et maniable par les classes
         */
}


