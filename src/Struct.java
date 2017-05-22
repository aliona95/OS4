package os;

public class Struct implements Comparable<Struct>{
    int processId;
    int part_of_resourse;
    int priority;
    String info;
    
    @Override
    public int compareTo(Struct another) {
        if( this.priority > another.priority ){
            return 1;
        } else if(( this.priority == another.priority ))
        {
            return 0;
        }else
            return -1;
    }
}
