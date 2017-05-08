import java.util.ArrayList;


public class ProcessDescriptor {
	private static int processIdCounter = 0;
	private final int id = processIdCounter++;
	private String name = "";
	
	private String state; // proceso busena
	private int priority; // proceso prioritetas
	private int fatherProcessor; // tevinio proceso id
	
	private ArrayList<Integer> sonsProcesses = new ArrayList(); // sunu sarasas
	private ArrList resource = new ArrList(); // esami proceso resursai
	private int listWhereProcessIs; // nuoroda, kuriama sarase yra procesas (LPS, PPS)
	private ArrayList<Integer> createdResourses = new ArrayList<>(); // sukurtu resursu sarasas
	
	private ArrList operatingMemory = new ArrList();
	
	private String info = "";
	
	CPU cpu;
	
	void addSon(int index){
		this.sonsProcesses.add(index);
	}
	
	void setName(String name){
		this.name = name;
	}
	
	//setCpu !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
	
	
	//getCpu !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	
	public void setOperatingMemory(ArrList operatingMemory) { 
        this.operatingMemory.myCopy(operatingMemory.getList());
    }
	
	public void setResource(ArrList resource) {
        this.resource.myCopy(resource.getList());
    }
	
	public void setPriority(int priority) {
		this.priority = new Integer(priority);
	}
	
	public void setState(String state) {
        this.state = state;
    }
	
	public void setListWhereProcessIs(int listWhereProcessIs) {
        this.listWhereProcessIs = new Integer(listWhereProcessIs);
    }
	
	public int getId(){
        return id;
    }
	
	public void setFatherProcessor(int fatherProcessor) {
        this.fatherProcessor = new Integer(fatherProcessor);
    }
	
	public int getPriority() {
        return priority;
    }
	
	public ArrayList getCreatedResourses() {
        return createdResourses;
    }
	
	public void setCreatedResourses(ArrayList<Integer> createdResourses) {
        this.createdResourses = new ArrayList<>(createdResourses);
    }
	
	public String getState() {
        return state;
    }
	
	public String getName(){
        return name;
    }
	
	public void getCpu(){
		/*
        OS.realMachine.setRegisterR(cpu.getRegisterR());
        OS.realMachine.setRegisterPTR(cpu.getRegisterPTR());
        OS.realMachine.setRegisterIC(cpu.getRegisterIC());
        OS.realMachine.setRegisterSP(cpu.getRegisterSP());
        OS.realMachine.setRegisterINT(cpu.getRegisterINT());
        OS.realMachine.setRegisterCT(cpu.getRegisterCT());
        //OS.realMachine.setRegisterPI(cpu.getRegisterPI());
        //OS.realMachine.setRegisterSI(cpu.getRegisterSI());
        OS.realMachine.setRegisterC(cpu.isRegisterC());
        OS.realMachine.setRegisterMOD(cpu.isRegisterMOD());
        */
    }
    public void setCPU(){
    	/*
        cpu.setRegisterR(OS.realMachine.getRegisterR());
        cpu.setRegisterPTR(OS.realMachine.getRegisterPTR());
        cpu.setRegisterIC(OS.realMachine.getRegisterIC());
        cpu.setRegisterSP(OS.realMachine.getRegisterSP());
        cpu.setRegisterINT(OS.realMachine.getRegisterINT());
        cpu.setRegisterCT(OS.realMachine.getRegisterCT());
        //cpu.setRegisterPI(OS.realMachine.getRegisterPI());
        //cpu.setRegisterSI(OS.realMachine.getRegisterSI());
        cpu.setRegisterC(OS.realMachine.isRegisterC());
        cpu.setRegisterMOD(OS.realMachine.isRegisterMOD());
        */   
    }

	
	
	
			
	

}
