package MARS_CORE;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Compiler {

    private int MEMORY_SIZE;
    private String IRegex;
    private String MRegex;

    public Compiler() {
        this.MEMORY_SIZE = 8000;
        this.IRegex = "(?i)(MOV|ADD|SUB|JMP|JMZ|JMN|DJN|DJZ|CMP|DAT|SPL)";
        this.MRegex = "#|@|<";
    }

    public Compiler(int mem_size) {
        this.MEMORY_SIZE = mem_size;
        this.IRegex = "(?i)(MOV|ADD|SUB|JMP|JMZ|JMN|DJN|DJZ|CMP|DAT|SPL)";
        this.MRegex = "#|@|<";
    }

    public LinkedList<Process> compile(String filePath) {

        LinkedList<Process> listProcess = new LinkedList<Process>();
        int position = 0;

        try {
            HashMap<String, Integer> labels = getLabelList(filePath);
            Scanner scanner = new Scanner(new FileReader(filePath));

            while (scanner.hasNext()) {
                String line = removeSpaces(scanner.nextLine());
                Process p = processCompile(labels, line, position);
                //si une instruction existe, on l'ajoute au fil
                //d'instructions du Warrior
                if (p != null) {
                    listProcess.add(p);
                    position++;
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            //TODO: handle exception
            System.err.println("Error #1, invalid file or filePath");
            System.err.println(e);
        }

        return listProcess;
        
    }

    public Process processCompile(HashMap<String, Integer> labels, String line, int position) {
        if (!line.startsWith(";")) {
            Scanner scanner = new Scanner(line);
            String instructionsRegex = this.IRegex;
            String modeRegex = this.MRegex;
            Process process = new Process(this.MEMORY_SIZE);
            
            try {
                if (!scanner.hasNext(Pattern.compile(instructionsRegex))) {
                    scanner.next();
                }

                //Extraction de l'instruction
                String instruction = scanner.next();
                if (Instructions.DAT.equals(instruction)) {
                    process.setInstruction(Instructions.DAT);
                }
                else if (Instructions.MOV.equals(instruction)) {
                    process.setInstruction(Instructions.MOV);
                }
                else if (Instructions.ADD.equals(instruction)) {
                    process.setInstruction(Instructions.ADD);
                }
                else if (Instructions.SUB.equals(instruction)) {
                    process.setInstruction(Instructions.SUB);
                }
                else if (Instructions.JMP.equals(instruction)) {
                    process.setInstruction(Instructions.JMP);
                }
                else if (Instructions.JMN.equals(instruction)) {
                    process.setInstruction(Instructions.JMN);
                }
                else if (Instructions.JMZ.equals(instruction)) {
                    process.setInstruction(Instructions.JMZ);
                }
                else if (Instructions.CMP.equals(instruction)) {
                    process.setInstruction(Instructions.CMP);
                }
                else if (Instructions.DJN.equals(instruction)) {
                    process.setInstruction(Instructions.DJN);
                }
                else if (Instructions.DJZ.equals(instruction)) {
                    process.setInstruction(Instructions.DJZ);
                }
                else if (Instructions.SPL.equals(instruction)) {
                    process.setInstruction(Instructions.SPL);
                }
                else {
                    System.err.println("Error #2, invalid instruction : " + instruction);
                }

                //Extraction du premier argument
                //extraction du mode
                String modeA = scanner.findWithinHorizon(Pattern.compile(modeRegex), 2);
                if (modeA == null) {
                    process.getArg_A().setMode("_");
                }
                else {
                    process.getArg_A().setMode(modeA);
                }
                //extraction de l'adresse
                scanner.useDelimiter(",|[ \t]");
                if (!scanner.hasNextInt()) {
                    //l'adresse est une reference a un label
                    String labelA = scanner.next();
                    if (labels.get(labelA) == null) {
                        System.err.println("Error #3, invalid label : " + labelA);
                    }
                    else {
                        process.getArg_A().setRegister(new Register(labels.get(labelA) - position, this.MEMORY_SIZE));
                    }
                }
                else {
                    process.getArg_A().setRegister(new Register(scanner.nextInt(),this.MEMORY_SIZE));
                }
                scanner.useDelimiter("[ \t]");

                //Extraction du second argument, s'il existe
                String comma = ",";
                if (comma.equals(scanner.findWithinHorizon(comma, 1))) {
                    //extraction du mode
                    String modeB = scanner.findWithinHorizon(Pattern.compile(modeRegex), 2);
                    if (modeB == null) {
                        process.getArg_B().setMode("_");
                    }
                    else {
                        process.getArg_B().setMode(modeB);
                    }

                    //extraction de l'adresse
                    if (!scanner.hasNextInt()) {
                        //l'adresse est une reference a un label
                        String labelB = scanner.next();
                        if (labels.get(labelB) == null) {
                            System.err.println("Error #4, invalid label : " + labelB);
                        }
                        else {
                            process.getArg_B().setRegister(new Register(labels.get(labelB) - position, this.MEMORY_SIZE));
                        }
                    }
                    else {
                        process.getArg_B().setRegister(new Register(scanner.nextInt(),this.MEMORY_SIZE));
                    }   
                }

                scanner.close();
                return process;

            } catch (NoSuchElementException e) {
                //TODO: handle exception
                if (!line.matches("[ \t\n\f\r]+") && !line.equals("")) {
                    //si les lignes ne sont pas vides
                    System.err.println("Error #5 in line : " + line);
                    System.err.println(e);
                }
                scanner.close();
                return null;
            }
        } 
        else {
            //Si la ligne est un commentaire, on ne compile
            //aucune instruction.
            return null;
        }
    }

    //Extrait les labels (RedCode '94 Standard) et sauvegarde leur position
    //dans le fichier.
    public HashMap<String, Integer> getLabelList(String filePath) {
        
        HashMap<String, Integer> labels = new HashMap<String, Integer>();
        String instructionsRegex = this.IRegex;
        int positionLabel = 0;

        try {
            Scanner scanner = new Scanner(new FileReader(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = removeSpaces(line);
                Scanner lineScanner = new Scanner(line);
                if (!line.startsWith(";") && lineScanner.hasNext(Pattern.compile(instructionsRegex))) {
                    positionLabel++;
                }
                else {
                    String l;
                    try {
                        l = lineScanner.next();
                        labels.put(l, positionLabel);
                        positionLabel++;
                    } catch (NoSuchElementException e) {
                        //TODO: handle exception
                        if (!line.matches("[ \t\n\f\r]+") && !line.equals("")) {
                            //si les lignes ne sont pas vides
                            System.err.println("Error #6 in line : " + line);
                            System.err.println(e);
                        }
                    }
                }
                lineScanner.close();
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            //TODO: handle exception
            System.err.println("Error #7, invalid file or filePath");
            System.err.println(e);
        }

        return labels;
    }

    //Supprime les espaces inutile.
    private String removeSpaces(String line) {
        String result = line.trim();
        result = result.replaceAll("[ \t]+", " ");
        result = result.replaceAll("[ \t]+,", ",");
        return result;
    }

}