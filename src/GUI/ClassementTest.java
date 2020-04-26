import java.io.IOException;

public class ClassementTest
{
    public static void main(String[] args) throws IOException {
        ClassementJ cj = new ClassementJ("C:/Users/Jonathan/IdeaProjects/GUI/src/ClassementJ.txt");
        cj.afficherClassement();
        Joueur test = new Joueur("test");
        cj.ajoutJoueur(test);
        System.out.println();
        cj.afficherClassement();
    }
}
