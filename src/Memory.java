/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Algirdas
 */
public class Memory 
{
    boolean state;
    String cell;

    public Memory() 
    {
        this.state = false;
        this.cell = "";
    }
    
    
    //setters
    public void setState(boolean state)
    {
        this.state = state;
    }

    public void setCell(String cell) 
    {
        if( this.isState() )
        {
          //  OS.realMachine.setRegisterPI(1);
        } else
        {
            this.cell = cell;
            this.setState(!state);
        }
    }
    
    //getters
    public boolean isState() {
        return state;
    }
    
    public String getCell()
    {
        return cell;
    }

    @Override
    public String toString() {
        return "Memory{" + "state=" + state + ", cell=" + cell + '}';
    }

    public void freeCell()
    {
        this.cell = "";
        this.state = false;
    }
}
