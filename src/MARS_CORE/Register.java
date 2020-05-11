package MARS_CORE;

/**
 *  Register représente l'adresse d'un registre de la mémoire.
 *  contient les méthodes utiles. 
 */

public class Register {

    private int adress;
    private int MEMORY_SIZE;

    public Register() {
        this.adress = 0;
        this.MEMORY_SIZE = 8000;
    }

    public Register(int ad) {
        this.MEMORY_SIZE = 8000;
        this.adress = modulo(ad);
    }

    public Register(int ad, int mem_size) {
        this.MEMORY_SIZE = mem_size;
        this.adress = modulo(ad);
    }

    public int getAdress() {
        return modulo(this.adress);
    }

    public void setAdress(int ad) {
        this.adress = modulo(ad);
    }

    public void setMEMORY_SIZE(int mem_size) {
        this.MEMORY_SIZE = mem_size;
    }

    public int modulo(int n) {
        int temp = n % this.MEMORY_SIZE;
        return (temp < 0) ? temp + this.MEMORY_SIZE : temp;
    }

    public boolean equals(Register reg) {
        return (this.getAdress() == reg.getAdress());
    }

    public Register minus(Register reg) {
        Register temp = new Register(modulo(this.adress - reg.adress), this.MEMORY_SIZE);
        return (temp);
    }

    public Register plus(Register reg) {
        Register temp = new Register(modulo(this.adress + reg.adress), this.MEMORY_SIZE);
        return (temp);
    }
    
    @Override
    public String toString() {
        return("" + this.getAdress());
    }
}