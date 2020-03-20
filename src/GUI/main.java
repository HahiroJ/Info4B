import java.lang.reflect.Array;
import java.util.ArrayList;

public class main
{
    public static void main(String[] args)
    {
        System.out.println("-----Debut programme-----");
        CentralPU object = new CentralPU();
        ArrayList<String> CPU = object.getCPU();

        Fenetre windows = new CoreWar(object); //Instance d'une fenetre du jeu
        Fenetre classJ = new classementJ(); //Instance d'une fenetre de classement de joueurs
        Fenetre classP = new classementP(); //Instance d'une fenetre de classement de programmes

        System.out.println("------Fin programme------");
    }
}
