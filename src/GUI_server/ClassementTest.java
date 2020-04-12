package GUI_server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

public class ClassementTest
{
    public static void main(String[] args) throws IOException {
        ClassementJ cj = new ClassementJ();
        System.out.println(cj.afficherClass());
        cj.Save();

        String ip = null;
        try
        {
            InetAddress myadress = InetAddress.getLocalHost();
            ip = myadress.getHostAddress();
        } catch(UnknownHostException e){e.printStackTrace();}
        System.out.println(ip);
    }
}
