
public class Kernel {
	private ProcessorDeskriptor procDes = new ProcessorDeskriptor(); 
	
	public void createProcess(ProcWaitingList memory, ProcWaitingList resource, int priority, String name){
		PocessDescriptor process = new ProcessDescriptor();
		process.setName(name);
		
	}
}
