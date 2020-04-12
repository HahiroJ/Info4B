package GUI_server;

import java.util.ArrayList;
import java.util.LinkedList;

import CoreWar.Match;
import MARS_CORE.Compiler;
import MARS_CORE.Process;

public class Game implements Runnable{

    //private int this.nWarrior;
    private int MEMORY_SIZE;
    private int MAX_CYCLE;
    private int combat;
    private ArrayList<LinkedList<Process>> warriors;
    private LinkedList<Player> players;

    public Game(int mem_size, int max_cycle, int ncombats, LinkedList<Player> p) {
        super();
        //this.nWarrior = nW;
        this.MEMORY_SIZE = mem_size;
        this.MAX_CYCLE = max_cycle;
        this.combat = ncombats;
        this.players = p;
        this.warriors = new ArrayList<LinkedList<Process>>();
        for (int i = 0; i < players.size(); i++) {
            try {
                Compiler compiler = new Compiler(this.MEMORY_SIZE);
                warriors.add(i, compiler.compile(players.get(i).getFilePath()));
            } catch (Exception e) {
                //TODO: handle exception
                System.err.println("Error, invalid file or filePath");
                System.err.println(e);
            }
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Server.sendAll("CoreWar with " + this.players.size() + " Warriors start !");
        System.out.println("--> CoreWar with " + this.players.size() + " Warriors start !");
        for (int i = 0; i < this.players.size(); i++) {
            for (int j = i+1; j < this.players.size(); j++) {
                Match match = new Match(this.warriors.get(i), this.warriors.get(j),this.MEMORY_SIZE,this.MAX_CYCLE,this.combat);
                int[] scores = match.fight();
                Server.sendAll("Match " + ((j-i) + i * (2 * this.players.size() - i - 1) / 2) + "/" + (this.players.size() * (this.players.size() - 1) / 2) + " is finished");
                Server.sendAll(""+this.players.get(i).getPseudo()+"#"+this.players.get(i).getId()+" = +"+scores[0]);
                Server.sendAll(""+this.players.get(j).getPseudo()+"#"+this.players.get(j).getId()+" = +"+scores[1]);
                System.out.println("--> Match " + ((j-i) + i * (2 * this.players.size() - i - 1) / 2) + "/" + (this.players.size() * (this.players.size() - 1) / 2) + " is finished");
                System.out.println("--> "+this.players.get(i).getPseudo()+"#"+this.players.get(i).getId()+" = +"+scores[0]);
                System.out.println("--> "+this.players.get(j).getPseudo()+"#"+this.players.get(j).getId()+" = +"+scores[1]);
                this.players.get(i).addScore(scores[0]);
                this.players.get(j).addScore(scores[1]);
            }
        }
    }
}