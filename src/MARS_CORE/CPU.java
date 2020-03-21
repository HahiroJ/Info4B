package MARS_CORE;

public class CPU {

    private int MEMORY_SIZE;
    private Process[] Memory;

    public CPU(int mem_size) {
        this.MEMORY_SIZE = mem_size;
        this.Memory = new Process[this.MEMORY_SIZE];
        for (int i = 0; i < this.MEMORY_SIZE; i++) {
            this.Memory[i] = new Process(this.MEMORY_SIZE);
        }
    }

    //Evaluation de la première adresse
    public Register evaluateFirstRegister(Register reg) {
        Register first = new Register();
        first.setMEMORY_SIZE(this.MEMORY_SIZE);

        if (Memory[reg.getAdress()].getArg_A().getMode() == "#") {
            first = new Register(0,this.MEMORY_SIZE);
        }
        else {
            first = Memory[reg.getAdress()].getArg_A().getRegister();
            if (Memory[reg.getAdress()].getArg_A().getMode() != "_") {
                if(Memory[reg.getAdress()].getArg_A().getMode() == "<") {
                    Memory[reg.plus(first).getAdress()].getArg_B().setRegister(Memory[reg.plus(first).getAdress()].getArg_B().getRegister().minus(new Register(1,this.MEMORY_SIZE)));
                }
                first = Memory[reg.plus(first).getAdress()].getArg_B().getRegister().plus(first);
            } 
        }

        return first;
    }
}