package MARS_CORE;

/**
 * CPU est classe qui contient la mémoire sous forme de 
 * tableau d'instructions.
 * On y trouve :
 *  La lecture des registres.
 *  La definition des types d'instructions
 *  L'execution des instructions
 */

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

    public Process[] getMemory() {
        return this.Memory;
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
                    Memory[reg.plus(first).getAdress()].getArg_A().setRegister(Memory[reg.plus(first).getAdress()].getArg_A().getRegister().minus(new Register(1,this.MEMORY_SIZE)));
                }
                first = Memory[reg.plus(first).getAdress()].getArg_A().getRegister().plus(first);
            } 
        }

        return first;
    }

    //Evaluation de la seconde adresse
    public Register evaluateSecondRegister(Register reg) {
        Register second = new Register();
        second.setMEMORY_SIZE(this.MEMORY_SIZE);
        if (Memory[reg.getAdress()].getArg_B().getMode() == "#") {
            second = new Register(0,this.MEMORY_SIZE);    
        } 
        else {
            second = Memory[reg.getAdress()].getArg_B().getRegister();
            if (Memory[reg.getAdress()].getArg_B().getMode() != "_") {
                if (Memory[reg.getAdress()].getArg_B().getMode() == "<") {
                    Memory[reg.plus(second).getAdress()].getArg_B().setRegister(Memory[reg.plus(second).getAdress()].getArg_B().getRegister().minus(new Register(1,this.MEMORY_SIZE)));
                }
                second = Memory[reg.plus(second).getAdress()].getArg_B().getRegister().plus(second);
            }
        }

        return second;
    }

    //Execution de l'instruction au registre passé en argument
    //Retourne le registre de l'instruction suivante à executer
    public Register execute_process(Register reg) {
        Process process = Memory[reg.getAdress()];
        Register firstRegister = evaluateFirstRegister(reg);
        Register secondRegister = evaluateSecondRegister(reg);

        switch (process.getInstruction()) {
            case DAT: {
                return reg;
            }
            case MOV: {
                return MOV(process, reg, firstRegister, secondRegister);
        }
            case ADD: {
                return ADD(process, reg, firstRegister, secondRegister);
            }
            case SUB: {
                return SUB(process, reg, firstRegister, secondRegister);
            }
            case JMP: {
                return JMP(process, reg, firstRegister, secondRegister);
            }
            case JMZ: {
                return JMZ(process, reg, firstRegister, secondRegister);
            }
            case JMN: {
                return JMN(process, reg, firstRegister, secondRegister);
            }
            case CMP: {
                return CMP(process, reg, firstRegister, secondRegister);
            }
            case DJN: {
                return DJN(process, reg, firstRegister, secondRegister);
            }
            case DJZ: {
                return DJZ(process, reg, firstRegister, secondRegister);
            }
            case SPL: {
                return SPL(process, reg, firstRegister, secondRegister);
            }
            default:
                return null;
        }
    }

    //ici on declare les differentes méthodes relatives aux instructions RedCode
    //la declaration de nouvelles instructions doit respecter le même format
    //et doit impérativement retourner le registre de la prochaine
    //instruction à executer.

    //RedCode Instructions methods  
    
    public Register DAT(Process proc, Register reg, Register fReg, Register sReg) {
        return reg;
    }

    public Register MOV(Process proc, Register reg, Register fReg, Register sReg) {
        if (proc.getArg_A().getMode() == "#") {
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.getAdress()].getArg_A().getRegister());
        } 
        else {
            Memory[reg.plus(sReg).getAdress()].setInstruction(Memory[reg.plus(reg).getAdress()].getInstruction());
            Memory[reg.plus(sReg).getAdress()].getArg_A().setMode(Memory[reg.plus(fReg).getAdress()].getArg_A().getMode());
            Memory[reg.plus(sReg).getAdress()].getArg_A().setRegister(Memory[reg.plus(fReg).getAdress()].getArg_A().getRegister());
            Memory[reg.plus(sReg).getAdress()].getArg_B().setMode(Memory[reg.plus(fReg).getAdress()].getArg_B().getMode());
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(fReg).getAdress()].getArg_B().getRegister());    
        }
        return reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register ADD(Process proc, Register reg, Register fReg, Register sReg) {
        if (proc.getArg_A().getMode() == "#") {
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().plus(Memory[reg.getAdress()].getArg_A().getRegister()));    
        } 
        else {
            Memory[reg.plus(sReg).getAdress()].getArg_A().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_A().getRegister().plus(Memory[reg.plus(fReg).getAdress()].getArg_A().getRegister()));
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().plus(Memory[reg.plus(fReg).getAdress()].getArg_B().getRegister()));            
        }
        return reg.plus(new Register(1, this.MEMORY_SIZE));
    }

    public Register SUB(Process proc, Register reg, Register fReg, Register sReg) {
        if (proc.getArg_A().getMode() == "#") {
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().minus(Memory[reg.getAdress()].getArg_A().getRegister()));
        }
        else {
            Memory[reg.plus(sReg).getAdress()].getArg_A().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_A().getRegister().minus(Memory[reg.plus(fReg).getAdress()].getArg_A().getRegister()));
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().minus(Memory[reg.plus(fReg).getAdress()].getArg_B().getRegister()));
        }
        return reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register JMP(Process proc, Register reg, Register fReg, Register sReg) {
        return reg.plus(fReg);
    }

    public Register JMZ(Process proc, Register reg, Register fReg, Register sReg) {
        return (Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().getAdress() == 0) ? reg.plus(fReg) : reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register JMN(Process proc, Register reg, Register fReg, Register sReg) {
        return (Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().getAdress() != 0) ? reg.plus(fReg) : reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register CMP(Process proc, Register reg, Register fReg, Register sReg) {
        boolean temp;
        if(proc.getArg_A().getMode() == "#") {
            temp = (Memory[reg.getAdress()].getArg_A().getRegister().equals(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister()));
        }
        else {
            boolean A = (Memory[reg.plus(fReg).getAdress()].equals(Memory[reg.plus(sReg).getAdress()]));
            boolean B = (Memory[reg.plus(fReg).getAdress()].getArg_A().getMode().equals(Memory[reg.plus(sReg).getAdress()].getArg_A().getMode()));
            boolean C = (Memory[reg.plus(fReg).getAdress()].getArg_A().getRegister().equals(Memory[reg.plus(sReg).getAdress()].getArg_A().getRegister()));
            boolean D = (Memory[reg.plus(fReg).getAdress()].getArg_B().getMode().equals(Memory[reg.plus(sReg).getAdress()].getArg_B().getMode()));
            boolean E = (Memory[reg.plus(fReg).getAdress()].getArg_B().getRegister().equals(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister()));
            temp = A && B && C && D && E;
        }
        return (temp) ? reg.plus(new Register(1,this.MEMORY_SIZE).plus(new Register(1,this.MEMORY_SIZE))) : reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register DJN(Process proc, Register reg, Register fReg, Register sReg) {
        Register temp = new Register(1, this.MEMORY_SIZE);
        if (proc.getArg_B().getMode() == "#") {
            temp = Memory[reg.getAdress()].getArg_B().getRegister();    
        } 
        else {
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().minus(new Register(1,this.MEMORY_SIZE)));
            temp = Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister();
        }
        return (temp.getAdress() != 0) ? reg.plus(fReg) : reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register DJZ(Process proc, Register reg, Register fReg, Register sReg) {
        Register temp = new Register(1, this.MEMORY_SIZE);
        if (proc.getArg_B().getMode() == "#") {
            temp = Memory[reg.getAdress()].getArg_B().getRegister();    
        } 
        else {
            Memory[reg.plus(sReg).getAdress()].getArg_B().setRegister(Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister().minus(new Register(1,this.MEMORY_SIZE)));
            temp = Memory[reg.plus(sReg).getAdress()].getArg_B().getRegister();
        }
        return (temp.getAdress() == 0) ? reg.plus(fReg) : reg.plus(new Register(1,this.MEMORY_SIZE));
    }

    public Register SPL(Process proc, Register reg, Register fReg, Register sReg) {
        return reg.plus(fReg);
    }

    //RedCode Instructions methods
    
}