
public class RealMachine {
    
    private String registerR;
    
    
    private int registerPTR,
                registerIC,
                registerSP,
                registerINT,
                registerCT,
                registerPI,
                registerSI,
                registerTI,
                registerAI;
    
    private int SB,
                DB,
                ST,
                DT,
                SZ;
    
    private boolean registerC,
                    registerMOD;
    
    String registers[] = {"CT", "IC", "SP", "C", "R", "MOD", "PTR", "TI","SI","PI",
                          "INT"}; 

    public RealMachine() {
        
        this.registerR = "0";
        this.registerPTR = 0;
        this.registerIC = 0;
        this.registerSP = 999;
        this.registerINT = 0;
        this.registerCT = 0;
        this.registerPI = 0;
        this.registerSI = 0;
        this.registerTI = 50;
        this.registerAI = 0;
        this.SB = 0;
        this.DB = 0;
        this.ST = 0;
        this.DT = 0;
        this.SZ = 0;
        this.registerC = false;
        this.registerMOD = false;
        
    }   
    public void start()
    {
       OS.realMachine.setRegisterIC(0);
       OS.realMachine.setRegisterSP(99);
       OS.paging.findPTR();
       OS.realMachine.setRegisterMOD(true);
    }
    
    public void sb( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.SB = object_number;
        } else ; // throw exception
    }

    public void db( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.DB = object_number;
        } else ; // throw exception
    }
    
    public void st( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.ST = object_number;
        } else ; // throw exception
    }
    
    public void dt( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.DT = object_number;
        } else ; // throw exception
    }
    
    public void sz( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.SZ = object_number;
        } else ; // throw exception
    }
  
    public void xchgn()
    {
        OS.cd.DataExchange();
    }

    public int getRegisterAI()
    {
        return registerAI;
    }
    
     
    public String getRegisterR() {
        return registerR;
    }

    public int getRegisterPTR() {
        return registerPTR;
    }

    public int getRegisterIC() {
        return registerIC;
    }

    public int getRegisterSP() {
        return registerSP;
    }

    public int getRegisterINT() {
        return registerINT;
    }

    public int getRegisterCT() {
        return registerCT;
    }

    public int getRegisterPI() {
        return registerPI;
    }

    public int getRegisterSI() {
        return registerSI;
    }

    public int getRegisterTI() {
        return registerTI;
    }

    public int getSB() {
        return SB;
    }

    public int getDB() {
        return DB;
    }

    public int getST() {
        return ST;
    }

    public int getDT() {
        return DT;
    }

    public int getSZ() {
        return SZ;
    }

    public boolean isRegisterC() {
        return registerC;
    }

    public boolean isRegisterMOD() {
        return registerMOD;
    }

    public void setRegisterAI(int registerAI)
    {
        this.registerAI = registerAI;
    }
    
    public void setRegisterR(String registerR) {
        this.registerR = registerR;
    }

    public void setRegisterPTR(int registerPTR) {
        this.registerPTR = registerPTR;
    }

    public void setRegisterIC(int registerIC) {
        this.registerIC = registerIC;
    }

    public void setRegisterSP(int registerSP) {
        this.registerSP = registerSP;
    }
    
    public void setRegisterPI(int registerPI) {
        this.registerPI = registerPI;
    }

    public void setRegisterSI(int registerSI) {
        this.registerSI = registerSI;
    }

    public void setRegisterTI(int registerTI) {
        this.registerTI = registerTI;
    }
    
    public void setRegisterC(boolean registerC) {
        this.registerC = registerC;
    }

    public void setRegisterMOD(boolean registerMOD) {
        this.registerMOD = registerMOD;
    }
    
    @Override
    public String toString() {
        return "RealMachine{" + ", registerPTR=" + registerPTR + ", registerIC=" + registerIC + ", registerAI="+registerAI + "registerPI=" + registerPI + ", registerSI=" + registerSI + ", registerTI=" + registerTI +  ", registerMOD=" + registerMOD + '}';
    }
 
}
