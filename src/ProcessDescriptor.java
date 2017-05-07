import java.util.ArrayList;

public class ProcessDescriptor {
	private static int processIdCounter = 0;
	private final int id = processIdCounter++;
	private String name = "";
	
	private String state; // proceso busena
	private int priority; // proceso prioritetas
	private int fatherProcessor; // tevinio proceso id
	
	private ArrayList<Integer> sonsProcesses = new ArrayList(); // sunu sarasas
	private ProcWaitingList resource = new ProcWaitingList(); // esami proceso resursai
	private int listWhereProcessIs; // nuoroda, kuriama sarase yrs procesas (LPS, PPS)
	private ArrayList<Integer> createdResourses = new ArrayList<>(); // sukurtu resursu sarasas
	
	private ProcWaitingList operatingMemory = new ProcWaitingList();
	
	private String info = "";
	
	///CPU!!!!!!!!!!!!
	
	void addSon(int index){
		this.sonsProcesses.add(index);
	}
	
	//setCpu !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
	
	
	//getCpu !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	
			
	

}
