package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DataEntry implements Runnable {
    
    private BufferedReader buffered_reader;
    private PrintWriter print_writer;

    public DataEntry(PrintWriter pw) {
        this.buffered_reader = new BufferedReader(new InputStreamReader(System.in));
        this.print_writer = pw;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        String mes;
        try {
            while (!(mes = this.buffered_reader.readLine()).equals("!END")) {
                this.print_writer.println(mes);
            }
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        Client.stop = true;
    }
}