import java.util.ArrayList;

public class Kernel {
	private ProcessorDeskriptor procDesc = new ProcessorDeskriptor(); 
	private ArrList pps = new ArrList();
	
	public void createProcess(ArrList memory, ArrList resource, int priority,CPU cpu, String name){
		// Veiklumo aprasas (procesu deskriptorius)
		ProcessDescriptor process = new ProcessDescriptor();
		process.setName(name);
		process.cpu = cpu;
        process.setOperatingMemory(memory);
        process.setResource(resource);
        process.setPriority(priority);
        process.setState("READY");
        process.setListWhereProcessIs(-1);
        
        if(OS.processDesc.size() != 0){
            int father = OS.kernel.procDesc.getProcessName();
            father = OS.kernel.findProc(father, OS.processDesc);
            process.setFatherProcessor(OS.processDesc.get(father).getId());
            OS.processDesc.get(father).addSon(process.getId());
        }else{
            process.setFatherProcessor(process.getId());
        }
        OS.kernel.pps.addPps(process.getId(), process.getPriority());
        OS.processDesc.add(process);  // Dedame proceso deskriptoriu i procesoriaus deskriptoriu
	}
	
	public int findProc(int id, ArrayList<ProcessDescriptor> list){
		int index = -1;
	    for(ProcessDescriptor obj : list){
	       index++;
	       if(obj.getId() == id){
	    	   return index;
	       }
	     }
	     return -1;
	}
	
	public ArrList getPps(){
        return pps;
    }
	
	 //resursu primityvai
    public void kurtiResursa(boolean pakartotinio, ArrList prienamumo_aprasymas, int adr, String name){
        //System.out.println("kuriamo resurso prienamumo argumento dydis: " + prienamumo_aprasymas.getSize());
        ResourseDescriptor resDesc = new ResourseDescriptor();
        resDesc.setName(name);
        resDesc.setRes_dist_addr(adr);
        resDesc.setRepeated_use(pakartotinio);
        resDesc.setInfo("");
        resDesc.setFather_resource(OS.kernel.procDesc.getProcessName());
        resDesc.setPrieinamu_resursu_sarasas(prienamumo_aprasymas);
        //System.out.println("kuriamo resurso uzpildymo dydis: " + resDesc.getPrieinamu_resursu_sarasas().getSize());
        int index = OS.kernel.procDesc.getProcessName();
        index = OS.kernel.findProc(index, OS.processDesc);
        ArrayList old = OS.processDesc.get(index).getCreatedResourses();
        old.add(resDesc.getRs());
        OS.processDesc.get(index).setCreatedResourses(old);
        OS.resourseDesc.add(resDesc);
        //System.out.println("-----------------");
        for(int i = 0; i < OS.resourseDesc.size(); i++){
           System.out.println("vardas: " + OS.resourseDesc.get(i).getName());
           //System.out.println("sisteminiai procesai prieinamu procesu saraso dydis: " + OS.resourseDesc.get(i).getPrieinamu_resursu_sarasas().getSize());
        }
        //System.out.println("paskutinis elementas : " + OS.resourseDesc.get(OS.resourseDesc.size()-1).getPrieinamu_resursu_sarasas().getSize());
    }
    
    public int findRes(int id,ArrayList<ResourseDescriptor> list){
        int index = -1;
        for(ResourseDescriptor obj : list)
        {
            index++;
            if(obj.getRs() == id)
            {
                return index;
            }
        }
        return -1;
    }
    
    public int findResName(String name,ArrayList<ResourseDescriptor> list){
        for(ResourseDescriptor obj : list)
        {
            if(obj.getName().equals(name))
            {
                return obj.getRs();
            }
        }
        return -1;
    }
    public void acivateProc( int index ){
    	int ind = OS.kernel.findProc(index, OS.processDesc);
        if(OS.processDesc.get(ind).getState().equals("READYS")){
            OS.processDesc.get(ind).setState("READY");
            int prior = OS.processDesc.get(ind).getPriority();
            OS.kernel.pps.addPps(index, prior);
            planuotojas();
        } else {
            OS.processDesc.get(ind).setState("BLOCKED");
        }
        /*if( OS.processDesc.get(ind).equals("READY")){
            planuotojas();
        }*/
    }
    
    public void planuotojas(){
        //OS.plan = true;
        int processNumber = OS.kernel.procDesc.getProcessName();
        int index = OS.kernel.findProc(processNumber, OS.processDesc);
        System.out.println("EINAMO PROCESO_NUMERIS = " + index);
        System.out.println("EINAMO PROCESO_VARDAS = " + OS.processDesc.get(index).getName());
        //TODO gali reikt tikrint ar ne null
        if (!OS.processDesc.get(index).getState().equals("BLOCKED"))
        {
            if(OS.processDesc.get(index).getState().equals("READYS")){
                int next_process = OS.kernel.pps.removeFirst();
                int next_process1 = OS.kernel.findProc(next_process, OS.processDesc);
                System.out.println("SEKANCIO, jei einamas buvo readys, PROCESO_NUMERIS = " + next_process1);
                System.out.println("SEKANCIO, jei einamas buvo readys, PROCESO_VARDAS = " + OS.processDesc.get(next_process1).getName());
                OS.processDesc.get(next_process1).getCpu();
                //OS.processDesc.get(next_process1).setState("RUN");
                OS.kernel.procDesc.setProcessName(OS.processDesc.get(next_process1).getId());
            }else
            {
                OS.processDesc.get(index).setState("READY");
                OS.processDesc.get(index).setCPU();
                OS.kernel.pps.addPps(OS.processDesc.get(index).getId(), OS.processDesc.get(index).getPriority());
                int next_process = OS.kernel.pps.removeFirst();
                int next_process1 = OS.kernel.findProc(next_process, OS.processDesc);
                System.out.println("SEKANCIO, jei einamas buvo ready, PROCESO_NUMERIS = " + next_process1);
                System.out.println("SEKANCIO, jei einamas buvo ready, PROCESO_VARDAS = " + OS.processDesc.get(next_process1).getName());
                OS.processDesc.get(next_process1).getCpu();
               ///!!!! System.out.println("IC = " + OS.realMachine.getRegisterIC());
                OS.processDesc.get(next_process1).setState("RUN");
                OS.kernel.procDesc.setProcessName(OS.processDesc.get(next_process1).getId());
            }
        }else
        {
            //OS.processDesc.get(index).setState("READY");
            OS.processDesc.get(index).setCPU();
            //OS.kernel.pps.addPps(processNumber, OS.processDesc.get(processNumber).getPriority());
            int next_process = OS.kernel.pps.removeFirst();
            int next_process1 = OS.kernel.findProc(next_process, OS.processDesc);
            System.out.println("SEKANCIO, jei einamas buvo blokuotas, PROCESO_NUMERIS = " + next_process1);
            System.out.println("SEKANCIO, jei einamas buvo blokuotas, PROCESO_VARDAS = " + OS.processDesc.get(next_process1).getName());
            if(OS.processDesc.get(next_process1).getName() == "VIRTUAL_MACHINE")
            {
                int hmmm = 5;
            }
            System.out.println("SEKANCIO, jei einamas buvo blokuotas, PROCESO_ID = " + OS.processDesc.get(next_process1).getId());
            OS.processDesc.get(next_process1).getCpu();
            //!!!System.out.println("IC = " + OS.realMachine.getRegisterIC());
            //!!!!System.out.println("MOD = " + OS.realMachine.isRegisterMOD());
            OS.processDesc.get(next_process1).setState("RUN");
            
            OS.kernel.procDesc.setProcessName(OS.processDesc.get(next_process1).getId());
        } 
    }
    
    public ProcessorDeskriptor getProcDesc(){
        return procDesc;
    }

}
