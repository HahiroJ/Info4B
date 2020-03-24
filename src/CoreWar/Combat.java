package CoreWar;

import java.util.LinkedList;

import MARS_CORE.CPU;
import MARS_CORE.Warrior;
import MARS_CORE.Register;
import MARS_CORE.Process;

/**
 * Représente l'affrontement de deux warriors dans le processeurs.
 */

public class Combat implements Runnable {

    private Warrior warrior1;
    private Warrior warrior2;
    private int MEMORY_SIZE;
    private long speed;
    private int beginner;
    private int CYCLE_MAX;
    private int[] scores;

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin, int mem_size) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.speed = 0;
        this.MEMORY_SIZE = mem_size;
        this.CYCLE_MAX = 16000;
        this.scores = tScores;
    }

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.speed = 0;
        this.MEMORY_SIZE = 8000;
        this.CYCLE_MAX = 16000;
        this.scores = tScores;
    }

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin, int mem_size,
            long Pspeed) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.speed = Pspeed;
        this.MEMORY_SIZE = mem_size;
        this.CYCLE_MAX = 16000;
        this.scores = tScores;
    }

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin, int mem_size, int max_cycle, long Pspeed) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.speed = Pspeed;
        this.MEMORY_SIZE = mem_size;
        this.CYCLE_MAX = max_cycle;
        this.scores = tScores;
    }

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin, long Pspeed) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.speed = Pspeed;
        this.MEMORY_SIZE = 8000;
        this.CYCLE_MAX = 16000;
        this.scores = tScores;
    }

    public void setCycle(int cycle_max) {
        this.CYCLE_MAX = cycle_max;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        CPU cpu = new CPU(this.MEMORY_SIZE);
        Register reg = new Register(0, this.MEMORY_SIZE);

        this.warrior1.getRegisters().add(reg);
        int Size = 0;

        // On place le premier warrior au debut de la mémoire
        for (Process process : this.warrior1.getProcess()) {
            if (Size < 100) {
                Process[] tempMemory = cpu.getMemory();
                tempMemory[reg.getAdress()] = process;
                reg = reg.plus(new Register(1, this.MEMORY_SIZE));
                Size++;
            } else {
                System.err.println("Error, the warrior is too big");
                break;
            }
        }

        // On place le second warrion dans la mémoire,
        // cette fois ci de manière aléatoire
        int randomRegister = (int) (Math
                .floor(((this.MEMORY_SIZE - this.warrior1.getProcess().size() - this.warrior2.getProcess().size())
                        * Math.random()))
                + 1);
        reg = reg.plus(new Register(randomRegister, this.MEMORY_SIZE));

        this.warrior2.getRegisters().add(reg);
        for (Process process : this.warrior2.getProcess()) {
            if (Size < 100) {
                Process[] tempMemory = cpu.getMemory();
                tempMemory[reg.getAdress()] = process;
                reg = reg.plus(new Register(1, this.MEMORY_SIZE));
                Size++;
            } else {
                System.err.println("Error, the warrior is too big");
                break;
            }
        }

        // FIGHT !
        int cycle = 0;
        if (this.beginner == 0) {
            while (!this.warrior1.getRegisters().isEmpty() && !this.warrior2.getRegisters().isEmpty()
                    && cycle < this.CYCLE_MAX) {
                /*try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                cycle++;
                this.warrior1.execute(cpu);
                if (!this.warrior1.getRegisters().isEmpty()) {
                    this.warrior2.execute(cpu);
                }
            }
        }
        else {
            while (!this.warrior1.getRegisters().isEmpty() && !this.warrior2.getRegisters().isEmpty() && cycle < this.CYCLE_MAX) {
                /*try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                cycle++;
                this.warrior2.execute(cpu);
                if (!this.warrior2.getRegisters().isEmpty()) {
                    this.warrior1.execute(cpu);
                }
            }
        }

        synchronized(this) {
            if (this.warrior1.getRegisters().isEmpty()) {
                this.scores[0]++;
            }
            else if (this.warrior2.getRegisters().isEmpty()) {
                this.scores[1]++;
            }
            else {
                this.scores[0]++;
                this.scores[1]++;
            }
        }
    }
 }