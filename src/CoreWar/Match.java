package CoreWar;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import MARS_CORE.Process;

/**
 * Represente un match entre deux warriors
 * un match est constitué d'un certain nombres de combat
 * Chaque combat est réalisé en paralleles via des threads
 */

public class Match {

    private LinkedList<Process> warrior1;
    private LinkedList<Process> warrior2;
    private int[] scores;
    private int MEMORY_SIZE;
    private int MAX_CYCLE;
    private long speed;
    private int nCombat;

    public Match(LinkedList<Process> w1, LinkedList<Process> w2, int mem_size, int max_cycle, int combat, long pSpeed) {
        this.warrior1 = w1;
        this.warrior2 = w2;
        this.scores = new int[2];
        this.MEMORY_SIZE = mem_size;
        this.MAX_CYCLE = max_cycle;
        this.speed = pSpeed;
        this.nCombat = combat;
    }

    public int[] getScores() {
        return this.scores;
    }

    public int[] fight() {
        int availableCPU = Runtime.getRuntime().availableProcessors();
        availableCPU = (availableCPU == 1) ? 1 : availableCPU - 1;
        ExecutorService threadsPool = Executors.newFixedThreadPool(availableCPU);

        for (int i = 0; i < this.nCombat; i++) {
            Runnable worker = new Combat(this.warrior1,this.warrior2,this.scores,i%2,this.MEMORY_SIZE,this.MAX_CYCLE,this.speed);
            threadsPool.execute(new Thread(worker));
        }
        threadsPool.shutdown();
        while (!threadsPool.isTerminated()) {
            
        }
        return this.scores;
    }

    
}