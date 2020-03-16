import java.util.ArrayList;
public class CentralPU
{
    private ArrayList<String> core = new ArrayList<String>();

    public CentralPU()
    {
        for(int i=0; i<= 7999; i++)
        {
            this.core.add("");
        }
    }

    public void ajoutMEM() //On imagine faire une vérification plus tard du code
    {

    }
    public ArrayList<String> getCPU() { return this.core; }
}
