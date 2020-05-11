package MARS_CORE;

/**
 * Process represente une instruction complete, avec l'instruction 
 * à executer et ses deux arguments. 
 */

public class Process {

    private Instructions instruction;
    private Argument arg_A;
    private Argument arg_B;

    public Process() {
        this.instruction = Instructions.DAT;
        this.arg_A = new Argument();
        this.arg_B = new Argument();
    }

    public Process(int mem_size) {
        this.instruction = Instructions.DAT;
        this.arg_A = new Argument(mem_size);
        this.arg_B = new Argument(mem_size);
    }

    public Instructions getInstruction() {
        return this.instruction;
    }

    public Argument getArg_A() {
        return this.arg_A;
    }
    
    public Argument getArg_B() {
        return this.arg_B;
    }

    public void setInstruction(Instructions i) {
        this.instruction = i;
    }

    public void setArg_A(Argument arg) {
        this.arg_A = arg;
    }

    public void setArg_B(Argument arg) {
        this.arg_B = arg;
    }

    public boolean equals(Process p) {
        return (this.instruction.equals(p.instruction) && this.arg_A.equals(p.arg_A) && this.arg_B.equals(p.arg_B));
    }

    @Override
    public String toString() {
        return "" + instruction.toString() + arg_A.getMode() + arg_A.getRegister().getAdress() + arg_B.getMode() + arg_B.getRegister().getAdress();
    }
}