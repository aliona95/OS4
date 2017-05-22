/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import static java.lang.String.*;

/**
 *
 * @author Algirdas
 */
public class VirtualMachine 
{

    public VirtualMachine(int memoryRequired) 
    {
        
    }
    //registers
    private String registerR;
    private int registerCT;
    private int registerIC;
    private int registerSP;
    private boolean registerC;
    
    //other Fields
    private final int memorySize = 100;
    public Memory[] memory = new Memory[this.memorySize];
    String registers[] = {"CT", "IC", "SP", "C", "R"};
    
    //constructor

    public VirtualMachine() {
        this.registerR = "0";
        this.registerCT = 0;
        this.registerIC = 0;
        this.registerSP = 99;
        this.registerC = false;
        memoryInit();
    }
    
    //memory work
    private void memoryInit()
    {
        for(int i = 0; i < memorySize; i++)
        {
            this.memory[i] = new Memory();
        }
    }
    
    public void printMemory()
    {
        for(int i = 0; i < memorySize; i++)
        {
            System.out.println(i + " " + memory[i]);
        }
    }
    
    //setters
    public void setRegisterR(String registerR) {
        this.registerR = registerR;
    }

    public void setRegisterCT(int registerCT) {
        this.registerCT = registerCT;
    }

    public void setRegisterIC(int registerIC) {
        this.registerIC = registerIC;
    }

    public void setRegisterSP(int registerSP) {
        this.registerSP = registerSP;
    }

    public void setRegisterC(boolean registerC) {
        this.registerC = registerC;
    }
    
    //getters

    public String getRegisterR() {
        return registerR;
    }

    public int getRegisterCT() {
        return registerCT;
    }

    public int getRegisterIC() {
        return registerIC;
    }

    public int getRegisterSP() {
        return registerSP;
    }

    public boolean isRegisterC() {
        return registerC;
    }
    
    //toString

    @Override
    public String toString() {
        return "VirtualMachine{" + "registerR=" + registerR + ", registerCT=" + registerCT + ", registerIC=" + registerIC + ", registerSP=" + registerSP + ", registerC=" + registerC + '}';
    }
    
    // TODO 
    //other methods
    public void changeR()
    {
        this.registerR = memory[this.registerIC].getCell();
        this.registerIC = this.registerIC + 1;
    }
    public void loadR(int memCell)
    {
        this.registerR = memory[memCell].getCell();
    }
    public void saveR(int memCell)
    {
        if(memory[memCell].isState())
        {
            OS.realMachine.setRegisterPI(1);
            return;
        }
        else
        {
            memory[memCell].setCell(this.registerR);
            //memory[memCell].setState(true);
            changeRMmem(memCell, this.registerR);
        }
    }
    public void loadRR(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerR = String.valueOf(this.registerCT);
                            return;
                    case 1: this.registerR = String.valueOf(this.registerIC);
                            return;
                    case 2: this.registerR = String.valueOf(this.registerSP);
                            return;
                    case 3: this.registerR = String.valueOf(this.registerC);
                            return;
                    case 4: this.registerR = String.valueOf(this.registerR);
                            return;
                }
            }
        }
    }
    public void addR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) + Integer.valueOf(memory[memCell].getCell()));
    }
    public void subR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) - Integer.valueOf(memory[memCell].getCell()));
    }
    public void mulR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) * Integer.valueOf(memory[memCell].getCell()));
    }
    public void divR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) / Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpR(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) == Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpRL(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) < Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpRG(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) > Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpZ(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerC = (this.registerCT == 0 );
                            return;
                    case 1: this.registerC = (this.registerIC == 0 );
                            return;
                    case 2: this.registerC = (this.registerSP == 0 );
                            return;
                    case 3: this.registerC = (this.registerC == false );
                            return;
                    case 4: this.registerC = (Integer.valueOf(this.registerR) == 0 );
                            return;
                }
            }
        }
    }
    public void jumpIf(int memCell)
    {
        if(this.registerC)
        {
            this.registerIC = memCell;
        }
    }
    public void jump(int memCell)
    {
        this.registerIC = memCell;
    }
    public void call(int memCell)
    {
        push("C");
        push("R");
        push("IC");
        push("SP");
        push("CT");
        jump(memCell);
    }
    public void push(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: changeRMmem(this.registerSP,String.valueOf(this.registerCT));
                            memory[this.registerSP].setCell(String.valueOf(this.registerCT));
                            //memory[this.registerSP].setState(true);
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 1: changeRMmem(this.registerSP,String.valueOf(this.registerIC));
                            memory[this.registerSP].setCell(String.valueOf(this.registerIC));
                            //memory[this.registerSP].setState(true);
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 2: changeRMmem(this.registerSP,String.valueOf(this.registerSP));
                            memory[this.registerSP].setCell(String.valueOf(this.registerSP));
                            //memory[this.registerSP].setState(true);
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 3: changeRMmem(this.registerSP,String.valueOf(this.registerC));
                            memory[this.registerSP].setCell(String.valueOf(this.registerC));
                            //memory[this.registerSP].setState(true);
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 4: changeRMmem(this.registerSP,String.valueOf(this.registerR));
                            memory[this.registerSP].setCell(this.registerR);
                            //memory[this.registerSP].setState(true);
                            this.registerSP = this.registerSP - 1;
                            return;
                }
            }
        }
    }
 public void pop(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerSP = this.registerSP + 1;
                            this.registerCT = Integer.valueOf(memory[this.registerSP].getCell()); 
                            memory[this.registerSP].freeCell();
                            return;
                    case 1: this.registerSP = this.registerSP + 1;
                            this.registerIC = Integer.valueOf(memory[this.registerSP].getCell());
                            memory[this.registerSP].freeCell();
                            return;
                    case 2: this.registerSP = this.registerSP + 1;
                            this.registerSP = Integer.valueOf(memory[this.registerSP].getCell());
                            memory[this.registerSP].freeCell();
                            return;
                    case 3: this.registerSP = this.registerSP + 1;
                            this.registerC = Boolean.valueOf(memory[this.registerSP].getCell());
                            memory[this.registerSP].freeCell();
                            return;
                    case 4: this.registerSP = this.registerSP + 1;
                            this.registerR = memory[this.registerSP].getCell();
                            memory[this.registerSP].freeCell();
                            return;
                }
            }
        }
    }
    public void returnC ()
    {          
        pop("CT");
        pop("SP");
        pop("IC");
        pop("R");
        pop("C");
    }
    public void systemCall(int number)
    {
        OS.realMachine.setRegisterSI(number);
    }
    public void loop(int memCell)
    {
        if(this.registerCT != 0)
        {
            this.registerCT = this.registerCT - 1;
            jump(memCell);
        }
    }
    public void doCommand(int number, String value)
    {
        if(value.matches("\\d+"))
        {
            if(Integer.valueOf(value) < 0 || Integer.valueOf(value) >= OS.VM_MEMORY_SIZE)
            {
                OS.realMachine.setRegisterPI(1);
                return;
            }
        }
        switch(number)
         { 
            case 0 : changeR();
                     return;
            case 1 : loadR(Integer.valueOf(value));
                     return;
            case 2 : saveR(Integer.valueOf(value));
                     return;
            case 3 : loadRR(value);
                     return;            
            case 4 : addR(Integer.valueOf(value));
                     return;
            case 5 : subR(Integer.valueOf(value));
                     return;
            case 6 : mulR(Integer.valueOf(value));
                     return;
            case 7 : divR(Integer.valueOf(value));
                     return;
            case 8 : cmpR(Integer.valueOf(value));
                     return;
            case 9 : cmpRL(Integer.valueOf(value));
                     return;
            case 10: cmpRG(Integer.valueOf(value));
                     return;
            case 11: cmpZ(value);
                     return;
            case 12: jumpIf(Integer.valueOf(value));
                     return;
            case 13: jump(Integer.valueOf(value));
                     return;
            case 14: call(Integer.valueOf(value));
                     return;
            case 15: push(value);
                     return;
            case 16: pop(value);
                     return;
            case 17: returnC();
                     return;
            case 18: systemCall(Integer.valueOf(value));
                     return;
            case 19: loop(Integer.valueOf(value));
                     return;
            default: OS.realMachine.setRegisterPI(2);
         }
    }
    public void changeRMmem(int memCell, String value)
    {
        OS.rmMemory[OS.paging.getRMadress(memCell)].setCell(value);
        //OS.rmMemory[OS.paging.getRMadress(memCell)].setState(true);
    }
    public void checkAdrr(int value)
    {
        if(value >= 0 && value < OS.VM_MEMORY_SIZE)
        {
            if(memory[value].isState())
            {
                
            }
        }
    }
}
