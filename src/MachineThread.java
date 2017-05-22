
package os;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineThread extends Thread
{
    public void run()
    {
        //boolean kk = false;
        while(!OS.osEnd)
        {
            if(OS.startInput)
            {
                
                
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
                //System.out.println("zingsnis: " + zingsnis);
                OS.cpu();
                //rmMemory[0].setState(false);
                //rmMemory[80].setState(false);
                /*int idd = OS.kernel.getProcDesc().getProcessName();
                int index = OS.kernel.findProc(idd, processDesc);
                System.out.println("einamas procesas: " + OS.processDesc.get(index).getName());
                for(int i = 0; i < OS.kernel.getPps().getSize(); i++)
                {
                    int id = OS.kernel.getPps().getList().get(i).processId;
                    id = OS.kernel.findProc(id, processDesc);
                    String name = OS.processDesc.get(id).getName();
                    System.out.println(name + " prioritetas: " + OS.processDesc.get(id).getPriority());
                }
                for(int i = 0; i < OS.processDesc.size(); i++)
                {
                    System.out.println("proceso vardas: " + OS.processDesc.get(i).getName() + "proceso busena: " + OS.processDesc.get(i).getState());
                }*/
                Thread.sleep(200);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
