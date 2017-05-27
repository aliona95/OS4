
package os;


import static java.lang.String.*;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OS { 

    /**
     * @param args the command line arguments
     */  
    
    public static boolean osEnd = false;
    
    public static int interruptedGovernor;
    
    public static int governorPtr;
    public static int governorId;
    
    public static int governorIc = 10;
    public static int vmIc = 20;
    
    public static ArrayList<ArrayList<Integer>> loaderOpreatingBlocks = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> loaderExternalBlocks = new ArrayList<>();
    
    public static ArrayList<Integer> askMemoryVmId = new ArrayList<>();
    public static int askMemorySize = 0;
    public static ArrayList<String> askMemoryR = new ArrayList<>();
    public static ArrayList<Integer> askMemoryPtr = new ArrayList<>();
    
    public static ArrayList<String> outputDataStrings = new ArrayList<>();
    public static ArrayList<Integer> outputDataPtr = new ArrayList<>();
    
    public static ArrayList<String> inputDataStrings = new ArrayList<>();
    public static ArrayList<Integer> inputDataPtr = new ArrayList<>();
    
    public static ArrayList<ArrayList<Integer>> uzduotisIsorinejeAtmintyje = new ArrayList<>();
    
    public static int blockedProcessId = -1; 
    public static boolean inputStreamOk = false;
    public static boolean startInput = false;
    public static boolean inputStarted = false;
    public static ArrayList<String> inputStream = new ArrayList<>();
    public static boolean outputStreamInUse = false;
    public static ArrayList<String> outputStream = new ArrayList<>();
    
    public static boolean plan = false;
    public static final int RM_MEMORY_SIZE = 1000;
    public static final int VM_MEMORY_SIZE = 100;
    public static final int EXTERNAL_MEMORY_SIZE = 4000;
    
   // public static GUI gui = new GUI();
    //agregatai
    public static RealMachine realMachine = new RealMachine();
    //public static VirtualMachine virtualMachine = new VirtualMachine();
    //public static final ArrayList myarr = new ArrayList();
    public static ArrayList<ProcessDescriptor> processDesc = new ArrayList<>();
    public static ArrayList<ResourseDescriptor> resourseDesc = new ArrayList<>();
    public static Kernel kernel = new Kernel();
    
    
    
    public static Paging paging = new Paging();
    //public static GUI OSgui = new GUI();
    public static ChannelDevice cd = new ChannelDevice();
    
    // REALIOS MAŠINOS ATMINTS
    public static Memory[] rmMemory = new Memory[RM_MEMORY_SIZE];
    // IŠORINĖ ATMINTIS
    public static Memory[] externalMemory = new Memory[EXTERNAL_MEMORY_SIZE];
    
    //galimos komandos
    public static String[] COMMANDS = {
    };
    
    
    public static void memoryInit(){
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ ){
            rmMemory[i] = new Memory();
        }
        for( int i = 0 ; i < EXTERNAL_MEMORY_SIZE ; i++ ){
            externalMemory[i] = new Memory();
        }
    }
    
    // ATMINTIES OUTPUT
    public static void memoryMonitoring(){
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ ){   
            System.out.println(i + " " + rmMemory[i]);
        }
    }
    
    //darbas su komandomis
    public static String getCommand()
    {
       // System.out.println("MACHINE_STATE = " +realMachine.isRegisterMOD());
        if (realMachine.isRegisterMOD()) {
            //turim ziureti pagal virtualia masina
            System.out.println("PTR = " + realMachine.getRegisterPTR());
            int adr = OS.paging.getRMadress(realMachine.getRegisterIC());
            return rmMemory[adr].getCell();
        }
        else {
            //turim ziuret pagal realia masina
            return rmMemory[realMachine.getRegisterIC()].getCell();
        }
    }
    public static int findCommand( String command ){
        
        for(char i = 0 ; i < COMMANDS.length; i++ ){
            if ( command.contains(COMMANDS[i]) ){
                return i;
            }
        }
        return -1;
    }
    public static String getValue(int commandNumber, String command)
    {
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
        }
    }
    public static void executeCommand(){        
        String command = getCommand();
        int commandNumber = findCommand(command);
        String value = getValue(commandNumber, command);
        /*
        System.out.println("COMMAND = " + command);
        System.out.println("COMMAND_NUMBER = " + commandNumber);
        System.out.println("VALUE = " + value);*/
        realMachine.setRegisterTI(realMachine.getRegisterTI() - 1);
        if(value.equals("PROCESS LINE"))
        {
            String line = rmMemory[realMachine.getRegisterIC()].getCell();
            if(line.matches("\\d+"))
            {
                int proc = OS.kernel.getProcDesc().getProcessName();
                proc = OS.kernel.findProc(proc, OS.processDesc);
                String name = OS.processDesc.get(proc).getName();
                switch(name){
                    case "StartStop":{
                        startStop(Integer.valueOf(line));
                        break;
                    }
                    case "ReadFromInterface":
                    {
                        readFromInterface(Integer.valueOf(line));
                        break;
                    }
                    case "JCL":
                    {
                        JCL(Integer.valueOf(line));
                        break;
                    }
                    case "JobToSwap":
                    {
                        JobToSwap(Integer.valueOf(line));
                        break;
                    }
                    case "MainProc": {
                        mainProc(Integer.valueOf(line));
                        break;
                    }
                    case "Loader":
                    {
                        loader(Integer.valueOf(line));
                        break;
                    }
                    case "Interrupt": {
                        mainProc(Integer.valueOf(line));
                        break;
                    }
                    case "PrintLine":
                    {
                        printLine(Integer.valueOf(line));
                        break;
                    }
                    
                    case "VirtualMachine":{
                        break;
                    }
                    case "JobGovernor":{
                        break;
                    }
                    case "ReadLine":
                    {
                        readline(Integer.valueOf(line));
                        break;
                    }
                    default:
                    {
                        
                    }
                }
            }
        }else if( !value.equals("COMMAND NOT FOUND")){
            /*if( realMachine.isRegisterMOD() )
            {
                // MOD = 1, paskiriame komandos vykdyma virtualiai masinai
                    virtualMachine.setRegisterIC(virtualMachine.getRegisterIC() + 1);
                    virtualMachine.doCommand(commandNumber, value );
            } 
            else
            {*/
                // MOD = 0, paskiriame komandos vykdyma realiai masinai
                    realMachine.setRegisterIC(realMachine.getRegisterIC() + 1);
                    realMachine.doCommand(commandNumber, value);
            //}
        } else 
        {
            // PI = 2, pertraukimo reikšmė dėl neleistino operacijos kodo
            /*OS.realMachine.setRegisterPI(2);
            if(realMachine.isRegisterMOD())
            {
                virtualMachine.setRegisterIC(virtualMachine.getRegisterIC() + 1);
            }
            else
            {*/

            realMachine.setRegisterIC(realMachine.getRegisterIC() + 1);
            //}
        }
        
    }
    public static void checkInterupts(){
        if (realMachine.getRegisterSI() != 0 || 
            realMachine.getRegisterPI() != 0 ||
            realMachine.getRegisterAI() != 0 ||
            realMachine.getRegisterTI() == 0){
        	opperateInterupts();
        }
    }
    public static void opperateInterupts(){
    	 OS.kernel.pertraukimuApdorotojas();
    }
    public static void cpu(){
         executeCommand();
        //out.println(realMachine.toString());
        checkInterupts();      
    }
    public static void initializeSystem() {
        //Kuriame StartStop
        CPU cpu = new CPU(false, 0, 0);
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
        
        // mos pabaiga
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "MosPabaiga");
        
        
        // vartotojo sasaja
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "VartotojoSasaja");
        
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "UzduotisSupervizorineiAtmintyje");
        
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "UzduotiesVykdymoParametraiSupAtm");
        
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "PakrovimoPaketas");
        
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "Pertraukimas");
        
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "EiluteAtmintyje");
      
        pa = new ArrList();
        //pa.addPa(1);
        OS.kernel.kurtiResursa(false, pa, adr, "UzduotisBugne");
        
        
        
        // turetume pakeisti i vartotojo ivedimas tikriausiai kuris ivestu uzduoti dar idedame i pasiruosusiu sarasa
        pa = new ArrList();
        pa.addPa(1, info);
        OS.kernel.kurtiResursa(true, pa, adr, "Laukimas");
        
        
        
    }
    
    public static void startStop(int line) {
        ArrList memory = new ArrList();
        ArrList resource = new ArrList();
        int priority = 0;
        CPU cpu = new CPU(false, 0 , 0);
        switch(line){
            case 0:{
                int ic = 1;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "ReadFromInterface");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "1";
                break;
            }
            case 1:{
                int ic = 2;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "JCL");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "2";
                break;
            }
            case 2:{
                int ic = 3;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "JobToSwap");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "3";
                break;
            }
            case 3: {
                int ic = 4;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "MainProc");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "4";
                break;
            }
            case 4:{
                int ic = 5;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "Loader");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "5";
                break;
            }
            case 5: {
                int ic = 6;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "Interrupt");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "6";
                break;
            }
            case 6: {
                int ic = 7;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "PrintLine");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "7";
                break;
            }
            case 7: {
                int ic = 8;
                cpu = new CPU(false, 0, ic);
                priority = 1;
                OS.kernel.createProcess(memory, resource, priority, cpu, "ReadLine");
                rmMemory[ic].cell = "0";
                rmMemory[0].cell = "8";
                break;
            }
            case 8: {
                /*for(int i = 0; i < OS.kernel.getPps().getSize(); i++)
                {
                    int id = OS.kernel.getPps().getList().get(i).processId;
                    id = OS.kernel.findProc(id, processDesc);
                    String name = OS.processDesc.get(id).getName();
                    System.out.println(name + " prioritetas: " + OS.processDesc.get(id).getPriority());
                }*/
                int res = OS.kernel.findResName("MosPabaiga", OS.resourseDesc);
                OS.kernel.prasytiResurso(res, 1);
                /*int id = OS.kernel.findRes(res, resourseDesc);
                for(int i = 0; i <OS.resourseDesc.get(id).getLaukianciu_procesu_sarasas().getSize(); i++ )
                {
                    System.out.println("darbo pabaigos resursas: " + OS.resourseDesc.get(id).getLaukianciu_procesu_sarasas().getList().get(i).processId);
                }*/
                rmMemory[0].cell = "9";
                break;
            }
            case 9: 
            {
                osEnd = true;
               // gui.shutdown();
                break;
            }
        }
    }
    
    public static void readFromInterface(int line) {
        switch(line) {
            case 0: {
            	// blokaviamsis laukiant resurso Vartotojo sasaja
                int res = OS.kernel.findResName("VartotojoSasaja", OS.resourseDesc);
                OS.kernel.prasytiResurso(res, 1);
                OS.rmMemory[1].cell = "1";
                break;
            }
            case 1:  {
            	// programos failu nuskaitymas ir suskaldymas i blokus
            	System.out.println("pradedu nuskaitinet progrmama????");
                break;
            }
            case 2:{
                // blokavimasis laukiant resurso supervizorine atmintis
                break;
            }
            case 3: {               
                // bloku kopijavimas i supervizorine atminti
                break;
            }
            case 4: {
                // sukuriamas ir atlaisvinamas resursas uzduotis supervizorineje atmintyje
                break;
            }
            
            //Naikintis turetu kazkaip???
            case 5:{
            	int res = OS.kernel.findResName("DARBO_PABAIGA", resourseDesc);
                OS.kernel.aktyvuotiR(res, 1, "");
                OS.rmMemory[1].cell = "0";
                break;
            }  
        }
    }
    public static void JCL(int line){
        switch(line){
            case 0:{
                String res = "UzduotisSupervizorineiAtmintyje";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[2].cell = "1";
                break;
            }
            // uzduotis vykdymo parametrai supervizorineje atmityje
            case 13:{
            	break;
            }
            
        }
        
        
    }
    
    
    public static void JobToSwap(int line){
        switch(line) {
            case 0: {
                String res = "UzduotiesVykdymoParametraiSupAtm";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[3].cell = "1";
                break;
            }
        }
        
        
    }
    public static void mainProc(int line){
        switch(line){
            case 0:{
                String res = "UzduotisBugne";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[4].cell = "1";
                break;
            }
        }
    }
    
    public static void loader(int line){
        switch(line){
            case 0:{
                String res = "PakrovimoPaketas";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[5].cell = "1";
                break;
            }            
        }
    }
    
    public static void interupt(int line){
        switch(line){
            case 0:{
                String res = "Petraukimas";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[6].cell = "1";
                break;
            }            
        }
    }
    public static void printLine(int line){
        switch(line){
            case 0:{
                String res = "EiluteAtmintyje";
                int id = OS.kernel.findResName(res, resourseDesc);
                OS.kernel.prasytiResurso(id, 1);
                OS.rmMemory[7].cell = "1";
                break;
            }            
        }
    }
    
    public static void readline(int line){
    	switch(line){
	        case 0:{
	            int res = OS.kernel.findResName("Laukimas", OS.resourseDesc);
	            OS.kernel.prasytiResurso(res, 1);
	            OS.rmMemory[8].cell = "1";
	            //OS.kernel.prasytiResurso(id, 1);
	            break;
	        }
	        case 1:{
	            String res = "Laukimas";
	            int id = OS.kernel.findResName(res, resourseDesc);
	            int proc = OS.kernel.getProcDesc().getProcessName();
	            OS.kernel.atlaisvintiResursa(id, 1, proc, "");
	            rmMemory[8].cell = "0";
	            break;
	        }
	        default:{
	            
	        }
      }
    }
    
    public static void jobGovernor( int line, int ic ){
    }
    
    public static void main(String[] args) {
    	RM rm = new RM();
    	rm.main();
        memoryInit();
        rmMemory[0].cell = "0";
        // inicializuojame resursus ir sukuriame START/STOP procesa
        initializeSystem();
        
        for(int i = 0; i < OS.resourseDesc.size(); i++){
            System.out.println("resurso vardas: " + OS.resourseDesc.get(i).getName());
            System.out.println("prieinamu resursu : " + OS.resourseDesc.get(i).getPrieinamu_resursu_sarasas().getSize());
        }
        
       /*
        int id = OS.kernel.findResName("OPERATYVIOJI_ATMINTIS", resourseDesc);
        int index = OS.kernel.findRes(id, resourseDesc);
        for(int i = 0; i < 14; i ++)
        {
            OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getList().remove(0);
        }
        for (int i = 0; i < OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getSize(); i++)
        {
            System.out.println("OA: " + OS.resourseDesc.get(index).getPrieinamu_resursu_sarasas().getList().get(i).part_of_resourse);
        }
        */
        OS.kernel.planuotojas();
        OS.plan = false;
        MachineThread machineThread = new MachineThread();
        machineThread.start();
        while(!osEnd) {
        	for( int i = 0; i < OS.processDesc.size();i++){
	    		rm.setProcessTable(i, OS.processDesc.get(i).getName(), OS.processDesc.get(i).getPriority(),OS.processDesc.get(i).getState());
            }
            while(outputStreamInUse);
                if(!outputStream.isEmpty()){
                    for( String output : outputStream ){
                      ///  OS.gui.getOutputArea().append(output);
                    }
                    outputStream = new ArrayList<>();
            }
               
            try {    
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
          //  OS.gui.refreshGUI();
        } 
    }
    
}
