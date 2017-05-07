
public class OS {
	public static Kernel kernel = new Kernel();
	public static void initializeSystem(){
		//CPU cpu = new CPU(); TURIM 
		ProcWaitingList memory = new ProcWaitingList();
		ProcWaitingList resource = new ProcWaitingList();
		kernel.createProcess(memory, resource, 0, "START/STOP");
		
	}

}
