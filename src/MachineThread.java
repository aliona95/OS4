import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineThread extends Thread
{
    public void run()
    {
        //boolean kk = false;
        while(!OS.osEnd){
            if(OS.startInput){
                InputThread inputThread = new InputThread();
                inputThread.start();
                
            }
            if(OS.inputStreamOk)
            {
                System.out.println("aktivuojamo proceso ID: " + OS.blockedProcessId);
                OS.kernel.acivateProc(OS.blockedProcessId);
            }
            //zingsnis++;
            try
            {
                OS.cpu();
                Thread.sleep(200);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

