import static java.lang.System.out;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OS {
	public static int counter;
	public static RM rm;
	public static boolean osEnd = false;
	    
	public final static int WORD_SIZE = 4;
	public final static int BLOCK_SIZE = 16;
	public final static int USER_BLOCKS = 48;
	private final static int BLOCKS = 64;
	
	public static boolean inputStreamOk = false;
	
	public static Kernel kernel = new Kernel();
	public static ArrayList<ProcessDescriptor> processDesc = new ArrayList<>();
	public static ArrayList<ResourseDescriptor> resourseDesc = new ArrayList<>();
	
	// realios masinos atmintis
	public static byte memory[] = new byte[BLOCKS * BLOCK_SIZE * WORD_SIZE];
	public static byte externalMemory[] = new byte[BLOCKS * BLOCK_SIZE * WORD_SIZE];
	
	public static int blockedProcessId = -1; 
    
	public static void initializeSystem(){
		//sukurtas start stop procesas
		byte PLR[] = {0, 15, 6, 1}; //pradine puslapiu lenteles reiksme
    	byte MODE = 1; // reiskia supervizoriaus rezima
    	CPU cpu = new CPU(MODE, PLR );
		ArrList memory = new ArrList();
		ArrList resource = new ArrList();
		OS.kernel.createProcess(memory, resource, 0, cpu, "StartStop");
		OS.kernel.getPps().removeFirst();
	    OS.processDesc.get(0).setState("RUN");
		String info= "";
		
		//kuriami resursai
        int adr = 0; //kolkas neaisku
        ArrList pa = new ArrList();
        //operatyvi atmintis
        
        // surasome procesus i atminti !!!! Reikai apsispresti po kiek dalint!!!!
        for(int i = 0; i < OS.memory.length / 16; i++){
            pa.addPa(i, info);
        }
        
        OS.kernel.kurtiResursa(true, pa, adr, "VARTOTOJO_SASAJA");
        OS.kernel.kurtiResursa(true, pa, adr, "Isorine atmintis");
        OS.kernel.kurtiResursa(true, pa, adr, "Supervizorine atmintis");
        OS.kernel.kurtiResursa(true, pa, adr, "Vartotojo atmintis");
     
        
        pa = new ArrList();
        pa.addPa(1, info);
        OS.kernel.kurtiResursa(true, pa, adr, "DARBO_PABAIGA");
        
        
        
        pa = new ArrList();
        pa.addPa(1, info);
    }
	public static void cpu(){
        executeCommand(); 
        //OSgui.refreshRegisterFields();
        /////////!!out.println(realMachine.toString());
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        ////Interuptai
	}
	public static void executeCommand(){       
		
        String command = getCommand();  // gauname komanda
        //int commandNumber = findCommand(command);
        
        //String value = getValue(commandNumber, command);
        String value = getValue();
       /*
        System.out.println("COMMAND = " + command);
        System.out.println("COMMAND_NUMBER = " + commandNumber);
        System.out.println("VALUE = " + value);
        realMachine.setRegisterTI(realMachine.getRegisterTI() - 1);
        */
        int line = counter;
        if(value.equals("PROCESS LINE")){
                int proc = OS.kernel.getProcDesc().getProcessName();
                proc = OS.kernel.findProc(proc, OS.processDesc);
                String name = OS.processDesc.get(proc).getName();
                //System.out.println("Procesas " + name);
                //System.out.println("Command counter:" + counter);
                switch(name){
                    case "StartStop":
                        startStop(Integer.valueOf(line));
                        break;
                    case "ReadFromInterface":
                    	System.out.println("AS CIA");
                    	readFromInterface(Integer.valueOf(line));
                        break;
                    default:
                    {
                        
                    }
                }
         }
    }
	
	public static String getValue(/*int commandNumber, String command*/){/*
        String registers[] = {"CT", "IC", "SP", "C", "R", "MOD", "PTR", "TI","SI","PI", "INT"}; 
        if( commandNumber >= 0 )
        {
            String commandBegin = COMMANDS[commandNumber];
            String maybe = command.replace(commandBegin, "");
            for(int i = 0; i < registers.length; i++)
            {
                if(maybe.endsWith(registers[i]))
                {
                    return maybe;
                }
                else if (maybe.matches("\\d+"))
                {
                    return maybe;
                }else if(maybe.equals(""))
                {
                    return maybe;
                }
            }
            OS.realMachine.setRegisterPI(2);
            return "COMMAND NOT FOUND";
        } 
        else 
        { 
            //OS.realMachine.setRegisterPI(2);
            return "PROCESS LINE";
        }*/
        return "PROCESS LINE";
    }
	
	//darbas su komandomis
    public static String getCommand(){
    	/*
    	System.out.println("MACHINE_STATE = " +realMachine.isRegisterMOD());
        if (realMachine.isRegisterMOD()){
            //turim ziureti pagal virtualia masina
            System.out.println("PTR = " + realMachine.getRegisterPTR());
            int adr = OS.paging.getRMadress(realMachine.getRegisterIC());
            return rmMemory[adr].getCell();
        }
        else{
            //turim ziuret pagal realia masina
            return rmMemory[realMachine.getRegisterIC()].getCell();
        }*/
    	return null;
    }
    
    
    public static void startStop(int line){
        ArrList memory = new ArrList();
        ArrList resource = new ArrList();
        int priority = 0;
        CPU cpu = new CPU();
        switch(line){
            case 0: 
            	counter++;
            	/*
                int ic = 1;
                */
                cpu = new CPU();
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "ReadFromInterface");
               
                
                //System.out.println("Sukuriamas procesas" + );
                /*//su atmintimi susieti
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "1";
                */
                break;
            case 1: 
            	counter++;
            	/*
                int ic = 3;
                cpu = new CPU(false, 0, ic);
                */
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "JCL ");
                /*
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "3";
                */
                break;
            case 2:
            	counter++;
            	/*
                int ic = 4;
                cpu = new CPU(false, 0, ic);
                */
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "JobToSwap");
                /*rmMemory[ic].cell = "0";
                rmMemory[0].cell = "4";*/
                break;
            case 3: 
            	counter++;
            	/*
                int ic = 5;
                cpu = new CPU(false, 0, ic);*/
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "MainProc");
                
            	/*
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "5";
                */
                break;
            case 4: 
            	counter++;
            	/*
                int ic = 6;
                cpu = new CPU(false, 0, ic);
                */
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "Loader");
                /*
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "6";
                */
                break;
                
            case 5: 
            	counter++;
            	/*
                int ic = 6;
                cpu = new CPU(false, 0, ic);
                */
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "Interrupt");
                /*
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "6";
                */
                break;
            case 6: 
            	counter++;
            	/*
                int ic = 6;
                cpu = new CPU(false, 0, ic);
                */
                priority = 2;
                OS.kernel.createProcess(memory, resource, priority, cpu, "PrintLine");
                /*
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "6";
                */
                int res = OS.kernel.findResName("DARBO_PABAIGA", OS.resourseDesc);
                OS.kernel.prasytiResurso(res, 1);
                break;
        }
    }
    
    public static void readFromInterface(int line){
    	System.out.println("AS CIA");
    	 switch(line){
    	 	case 0:
    	 		System.out.println("AS CIA");
    	 		int res = OS.kernel.findResName("VARTOTOJO_SASAJA", OS.resourseDesc);
    	 		OS.kernel.prasytiResurso(res, 1);
              //  OS.rmMemory[1].cell = "1";
    	 		counter++;
    	 	break;
    	 		
    	 }
    		
    	
    }
	public static void main(String args []){
		 rm = new RM();
		 rm.main();
		 initializeSystem();
		 for(int i = 0; i < OS.resourseDesc.size(); i++){
	            System.out.println("resurso vardas: " + OS.resourseDesc.get(i).getName());
	            System.out.println("prieinamu resursu : " + OS.resourseDesc.get(i).getPrieinamu_resursu_sarasas().getSize());
	     }
		
		 int id = OS.kernel.findResName("Vartotojo atmintis", resourseDesc);
	     int index = OS.kernel.findRes(id, resourseDesc);
	     for(int i = 0; i < 10; i ++){
	    	 OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getList().remove(0);
	     }
	     	     
	     //KAM????!!
	     /*
	     for (int i = 0; i < OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getSize(); i++){
	            System.out.println("OA: " + OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getList().get(i).part_of_resourse);
	     }*/
	     
	     OS.kernel.planuotojas();
	     //OS.plan = false;
	     MachineThread machineThread = new MachineThread();
	     machineThread.start();
	     
	     while(!osEnd){
	    	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	for( int i = 0; i < OS.processDesc.size();i++){
	    		rm.setProcessTable(i, OS.processDesc.get(i).getName(), OS.processDesc.get(i).getPriority(),OS.processDesc.get(i).getState());
	        }
	        OS.rm.frmMm.validate();
	     }
	}

}
