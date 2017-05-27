package os;
public class ProcesorDeskriptor
{
    private int processCount;
    private int processName;

    public ProcesorDeskriptor()
    {
        this.processCount = 1;
        this.processName = 0;
    }
    
    
    //setters

    public void setProcessCount(int processCount)
    {
        this.processCount = processCount;
    }

    public void setProcessName(int processName)
    {
        this.processName = processName;
    }

    public int getProcessCount()
    {
        return processCount;
    }

    public int getProcessName()
    {
        return processName;
    }
    
    public void setDescriptor(int count, int name)
    {
        setProcessCount(count);
        setProcessName(name);
    }
}
