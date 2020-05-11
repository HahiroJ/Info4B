package CoreWarGUI;

import MARS_CORE.CPU;
import MARS_CORE.Process;
import MARS_CORE.Register;
import MARS_CORE.Warrior;
import GUI.GUI;

import java.util.LinkedList;

/**
 * Représente l'affrontement de deux warriors dans le processeurs.
 */

public class Combat implements Runnable {

    private Warrior warrior1;
    private Warrior warrior2;

    private LinkedList<Register> registerW1;
    private LinkedList<Register> registerW2;

    private int MEMORY_SIZE;
    //private long speed;
    private int beginner;
    private int CYCLE_MAX;
    private int[] scores;
    private CPU cpu;

    public Combat(LinkedList<Process> listP1, LinkedList<Process> listP2, int[] tScores, int begin, int mem_size, int max_cycle) {
        super();
        this.warrior1 = new Warrior(listP1);
        this.warrior2 = new Warrior(listP2);
        this.beginner = begin;
        this.MEMORY_SIZE = mem_size;
        this.CYCLE_MAX = max_cycle;
        this.scores = tScores;
        this.cpu = new CPU(mem_size);
    }

    public void setCycle(int cycle_max) {
        this.CYCLE_MAX = cycle_max;
    }

    public Warrior getWar1(){return this.warrior1;}
    public Warrior getWar2(){return this.warrior2;}
    public int  getMemSize() {return this.MEMORY_SIZE;}

    public CPU getCPU() {return this.cpu;}

    @Override
    public void run() {

        /*this.actualiseur = new Actu(this);
        this.actualiseur.start();*/

        GUI jf = new GUI(this);
        jf.setVisible(true);


        // TODO Auto-generated method stub
       // CPU cpu = new CPU(this.MEMORY_SIZE);
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
            while (!this.warrior1.getRegisters().isEmpty() && !this.warrior2.getRegisters().isEmpty() && cycle < this.CYCLE_MAX)
            {
                cycle++;
                registerW1 = this.warrior1.getRegisters();
                this.warrior1.execute(cpu);

                try{
                    jf.actu(this.cpu.getMemory(), cycle, registerW1);
                    Thread.sleep(100);
                }catch(InterruptedException e){ Thread.currentThread().interrupt();}

                if (!this.warrior1.getRegisters().isEmpty()) {
                    registerW2 = this.warrior2.getRegisters();
                    this.warrior2.execute(cpu);

                    try{
                        jf.actu(this.cpu.getMemory(), cycle, registerW2);
                        Thread.sleep(100);
                    }catch(InterruptedException e ){ Thread.currentThread().interrupt();}
                }

            }
        }
        else {
            while (!this.warrior1.getRegisters().isEmpty() && !this.warrior2.getRegisters().isEmpty() && cycle < this.CYCLE_MAX)
            {
                cycle++;
                registerW2 = this.warrior2.getRegisters();
                this.warrior2.execute(cpu);

                try{
                    jf.actu(this.cpu.getMemory(), cycle, registerW2);
                    Thread.sleep(100);
                }catch(InterruptedException e){ Thread.currentThread().interrupt();}

                if (!this.warrior2.getRegisters().isEmpty()) {
                    registerW1 = this.warrior1.getRegisters();
                    this.warrior1.execute(cpu);

                    try{
                        jf.actu(this.cpu.getMemory(), cycle, registerW1);
                        Thread.sleep(100);
                    }catch(InterruptedException e ){Thread.currentThread().interrupt();}
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