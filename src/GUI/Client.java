package GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public final class Client
{

    static int port = 8080;
    static String ip = "127.0.0.1";
    static String pseudo = "";
    static boolean help = false;
    static boolean stop = false;


    public static void main(String[] args)
    {

        for (int i = 0; i < args.length; i++)
        {
            switch (args[i]) {
                case "-p": {
                    port = Integer.parseInt(args[i + 1]);
                    break;
                }
                case "-ip": {
                    ip = args[i + 1];
                    break;
                }
                case "-pseudo": {
                    pseudo = args[i + 1];
                    break;
                }
                case "-h":
                case "--help": {
                    help = true;
                    break;
                }
                default: {
                    System.out.println("Error, No arguments found.");
                    break;
                }
            }
        }

       /* Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (pseudo.equals("")) {
            System.out.print("Choose a pseudo : ");
            pseudo = scanner.nextLine();
        }*/
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Socket => " + socket);

            BufferedReader bufferer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            //
            Fenetre windows = new Fenetre(pw);
            Thread f = new Thread(windows);
            f.start();
            //
            pw.println(pseudo);

            while (!stop)
            {
                String mes = bufferer_reader.readLine();
                System.out.println(mes);
                windows.ReadServ(mes);
            }

            System.out.println("!quit");
            bufferer_reader.close();
            pw.close();
            socket.close();
            System.exit(0);

        } catch (IOException e) { e.printStackTrace();}
    }
}