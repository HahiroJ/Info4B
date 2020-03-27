package Client;

import java.io.*;
import java.util.Scanner;

public class DataEntry implements Runnable {
    
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;

    public DataEntry(PrintWriter pw) {
        this.buffered_reader = new BufferedReader(new InputStreamReader(System.in));
        this.print_writer = pw;
    }

    //Methode relative aux commandes
    public void ajoutG() throws IOException
    {
        System.out.println("Give your warrior file path :");
        String path = buffered_reader.readLine();
        fileRead(path);
    }
    //Methode relative aux commandes
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        String mes;
        try {
            while (!(mes = this.buffered_reader.readLine()).equals("!quit")) {
                if (mes.startsWith("!")) {
                    Scanner scanner = new Scanner(mes);
                    switch (scanner.next()) {
                        case "!help": {
                            this.print_writer.println(mes);
                            break;
                        }
                        case "!submit":{
                            ajoutG();
                            break;
                        }
                        default:
                            this.print_writer.println(mes);
                            break;
                    }
                    scanner.close();
                }
                else {
                    this.print_writer.println(mes);
                }
            }
            this.print_writer.println("!quit");
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        Client.stop = true;
    }

    public void fileRead(String path) throws IOException
    {
        String fichier ="";
        BufferedReader fr = new BufferedReader(new FileReader(path));
        String ligne;
        while ((ligne = fr.readLine()) != null)
        {
            fichier +=ligne+"\n";
        }

        System.out.println(fichier);
    }
}