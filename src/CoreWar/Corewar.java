package CoreWar;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import MARS_CORE.Compiler;
import MARS_CORE.Process;

public class Corewar {
    public static void main(String[] args) {
        int nWarrior = 0;
        int MEMORY_SIZE = 8000;
        int MAX_CYCLE = 1000000;
        long speed = 0;
        int combat = 40;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ArrayList<LinkedList<Process>> warriors = new ArrayList<LinkedList<Process>>();

        if (args.length == 0) {
            while (nWarrior == 0) {
                System.out.print("How many players ? : ");
                nWarrior = scanner.nextInt();
            }
            scanner.nextLine();
            for (int i = 0; i < nWarrior; i++) {
                while (warriors.size() < i + 1) {
                    System.out.print("FilePath for RedCode of Warrior #" + i + " : ");
                    try {
                        Compiler compiler = new Compiler(MEMORY_SIZE);
                        warriors.add(i, compiler.compile(scanner.nextLine()));
                    } catch (Exception e) {
                        //TODO: handle exception
                        System.err.println("Error, invalid file or filePath");
                        System.err.println(e);
                    }
                }
            }
        }

        //Execution de tous les matchs entre les differents warriors
        System.out.println("CoreWar with " + nWarrior + " Warriors start !");
        int[] finalScores = new int[nWarrior];
        for (int i = 0; i < nWarrior; i++) {
            for (int j = i+1; j < nWarrior; j++) {
                Match match = new Match(warriors.get(i), warriors.get(j),MEMORY_SIZE,MAX_CYCLE,combat,speed);
                int[] scores = match.fight();
                System.out.println("Match " + ((j-i) + i * (2 * nWarrior - i - 1) / 2) + "/" + (nWarrior * (nWarrior - 1) / 2) + " is finished");
                System.out.println("    Warrior #" + i + " = +" + scores[0]);
                System.out.println("    Warrior #" + j + " = +" + scores[1]);
                finalScores[i] += scores[0];
                finalScores[j] += scores[1];
            }
        }
        System.out.println("Scores :");
        for (int i = 0; i < finalScores.length; i++) {
            System.out.println("Warrior #" + i + " = " + finalScores[i]);
        }
    }
}