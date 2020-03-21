package MARS_CORE;

/**
 * Argument, regroupe le mode d'execution et l'adresse d'un argument de l'instruction. 
 */

public class Argument {

    private String mode;
    private Register register;
    
    public Argument() {
        this.mode = "#";
        this.register = new Register();
    }

    public Argument(int mem_size) {
        this.mode = "#";
        this.register = new Register();
        this.register.setMEMORY_SIZE(mem_size);
    }

    public String getMode() {
        return this.mode;
    }

    public Register getRegister() {
        return this.register;
    }

    public void setRegister(Register reg) {
        this.register = reg;
    }

    public boolean equals(Argument arg) {
        return (this.mode.equals(arg.mode) && this.register.equals(arg.register));
    }
    
}