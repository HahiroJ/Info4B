package MARS_CORE;

/**
 * Définition du type des instruction.
 * Les instructions sont codées dans le CPU. 
 * 
 * L'ajout d'instructions necessite la mise à jour du fichier CPU.java et Compiler.java
 */

public enum Instructions {
    MOV("MOV","mov"), 
    ADD("ADD","add"), 
    SUB("SUB","sub"), 
    JMP("JMP","jmp"), 
    JMZ("JMZ","jmz"), 
    JMN("JMN","jmn"), 
    DJN("DJN","djn"), 
    DJZ("DJZ","djz"), 
    CMP("CMP","cmp"), 
    DAT("DAT","dat");

    private String Maj = "";
    private String Min = "";

    Instructions(String maj, String min) {
        this.Maj = maj;
        this.Min = min;
    }

    public boolean equals(String ins) {
        boolean A = (this.Maj.equals(ins));
        boolean B = (this.Min.equals(ins));
        return (A || B);
    }

    public String toString() {
        return this.Maj;
    }
}
