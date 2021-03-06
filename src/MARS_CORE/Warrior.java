package MARS_CORE;

import java.util.LinkedList;

/**
 * la classe Warrior represente la liste des adresses à executer 
 * relative au Warrior.
 * Le déroulement des adresses à executer est penser comme un buffer circulaire.  
 */

public class Warrior {

    private LinkedList<Register> registers;
    private LinkedList<Process> process;

    public Warrior(LinkedList<Process> listP2) {
        this.registers = new LinkedList<Register>();
        this.process = listP2;
    }

    public LinkedList<Register> getRegisters() {
        return this.registers;
    }

    public LinkedList<Process> getProcess() {
        return this.process;
    }

    public void execute(CPU cpu) {
        Register reg_A = this.registers.poll();
        //Process[] tempProcess = cpu.getMemory();
        Instructions instruction = cpu.getMemory()[reg_A.getAdress()].getInstruction();
        Register reg_B = cpu.execute_process(reg_A);

        if (instruction != Instructions.DAT && instruction != Instructions.SPL) {
            this.registers.addLast(reg_B);
        }
        else {
            this.registers.addLast(reg_A.plus(new Register(1)));
            this.registers.addLast(reg_B);
        }
    }

    //penser à modifier pour l'ajout de SPL
}