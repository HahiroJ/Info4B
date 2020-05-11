import javax.swing.*;
import java.lang.Thread;

public class Actu implements Runnable
{
    private boolean running;
    private int delai;
    private Runnable currentF;
    private JFrame currentJF;

    public Actu(Runnable t)
    {
        delai = 1000;
        this.currentF = t;
        //this.currentJF = jf;
    }

    public void pauseActu(int d)
    {
        running = false;
        boolean ok = false;
        boolean end = false;
        while(!ok)
        {
            try{
                Thread.sleep(d);
                ok = true;
            }catch(InterruptedException e ){e.printStackTrace(); end = true;}
        }
        if(end)
        {
            Thread.currentThread().interrupt();
        }
        running = true;
    }

    public void setDelai(int d) { this.delai = d;}

    @Override
    public void run()
    {
        while(running)
        {
            //actualiser();
            /*if(currentF.() == Thread.State.WAITING)
            {
                synchronized(this) {
                    System.out.println("Actu : Je réveille");
                    currentF.notify();
                }
            }
            System.out.println("Je boucle\n");
            pauseActu(delai);*/
        }
    }

    public void start()
    {
        Thread a = new Thread(null, this);
        running = true;
        a.start();
    }
}
