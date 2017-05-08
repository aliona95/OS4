import java.util.ArrayList;
import java.util.Collections;
// laukianciu procesu sarasas

public class ArrList{
	
	private ArrayList<Struct> list = new ArrayList<Struct>();
	
	public ArrayList<Struct> getList(){
		return list;
	}
	
	public void myCopy(ArrayList copy){
        this.list = new ArrayList(copy);
    }
	
	public void addPps(int id, int prior){
        Struct struct = new Struct();
        struct.processId = id;
        struct.priority = prior;
        //struct.part_of_resourse = res;
        list.add(struct);
        Collections.sort(list);
    }
	
	public int removeFirst(){ //pasalinamas pirmas elementas, pps, lps
        int value = list.get(0).processId;
        list.remove(0);
        return value;
    }
	
	public void addPa( int part, String info ){
        //System.out.println("--------------------------> " + info);
        Struct struct = new Struct();
        struct.partOfResource = part;
        struct.info = info;
       // System.out.println("--------------------------> " + struct.info);
        list.add(struct);
        //Collections.sort(list);
    }
	
	public int getSize(){
        return list.size();
    }
}
