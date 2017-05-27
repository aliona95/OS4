/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

/**
 *
 * @author eimantas
 */
public class ChannelDevice {
    
    private int registerSB,
                registerDB,
                registerST,
                registerDT,
                registerSZ;
    
    private char blockToCopy,
                 blockToPaste,
                 objectToCopy,
                 objectToPaste,
                 cellsToCopy;
    
    private String[] copiedCells;
    
    boolean PerformTask;
    
    public ChannelDevice(){
        
        this.PerformTask = true;
        
    }
    
    public void DataExchange(){
        
        blockToCopy = (char) OS.realMachine.getSB();
        blockToPaste = (char) ((char) OS.realMachine.getDB()*10);
        objectToPaste = (char) OS.realMachine.getDT();
        cellsToCopy = (char) OS.realMachine.getSZ();
        
        copiedCells = new String[cellsToCopy];
        switch( OS.realMachine.getST() ){
            
            case 1 :
            {
                int index = 0;
                for( int i = blockToCopy ; i < (blockToCopy + cellsToCopy); i++ ){
                    copiedCells[index] = OS.rmMemory[blockToCopy+i].getCell();
                    System.out.println(copiedCells[index]);
                    index++;
                }
                
                if( objectToPaste == 1 ){
                    for( int i = 0 ; i < index ; i++ ){
                        OS.rmMemory[blockToPaste+i].setCell(copiedCells[i]);
                    }
                } else if( objectToPaste == 2 ){
                    for( int i = 0 ; i < index ; i++ ){
                        OS.externalMemory[blockToPaste+i].setCell(copiedCells[i]);
                    }
                } else if( objectToPaste == 3 ){
                        //OS.OSgui.exchangeToDisplay(copiedCells);
                }
                break;            
            }
            
            case 2 :
                
                int index = 0;
                for( int i = blockToCopy ; i < (blockToCopy + cellsToCopy); i++ ){
                    copiedCells[index] = OS.externalMemory[blockToCopy+i].getCell();
                    System.out.println(copiedCells[index]);
                    index++;
                }
                
                if( objectToPaste == 1 ){
                    for( int i = 0 ; i < index ; i++ ){
                        OS.rmMemory[blockToPaste+i].setCell(copiedCells[i]);
                    }
                } else if( objectToPaste == 2 ){
                    for( int i = 0 ; i < index ; i++ ){
                        OS.externalMemory[blockToPaste+i].setCell(copiedCells[i]);
                    }
                } else if( objectToPaste == 3 ){
                        //OS.OSgui.exchangeToDisplay(copiedCells);
                }
                break;
                
            case 3 :
                
                while( PerformTask ){
                    System.out.println("");
                }
                //OS.OSgui.InputToDisplay();
                break;
          
        }
        
    }

    public char getBlockToCopy() {
        return blockToCopy;
    }

    public char getBlockToPaste() {
        return blockToPaste;
    }

    public char getObjectToCopy() {
        return objectToCopy;
    }

    public char getObjectToPaste() {
        return objectToPaste;
    }

    public char getCellsToCopy() {
        return cellsToCopy;
    }

    public String[] getCopiedCells() {
        return copiedCells;
    }

    public boolean isPerformTask() {
        return PerformTask;
    }
    
    public int getRegisterSB() {
        return registerSB;
    }

    public int getRegisterDB() {
        return registerDB;
    }

    public int getRegisterST() {
        return registerST;
    }

    public int getRegisterDT() {
        return registerDT;
    }

    public int getRegisterSZ() {
        return registerSZ;
    }

}
