public class CPU {

    private String registerR;
    private int registerPTR, registerIC, registerSP, registerINT, registerCT,
                registerPI, registerSI;
    private boolean registerC, registerMOD;
    
    CPU(boolean registerMod, int registerPTR, int registerIC ){
        this.registerR = "";
        this.registerC = false;
        this.registerCT = 0;
        this.registerINT = 0;
        this.registerMOD = registerMod;
        this.registerPTR = registerPTR;
        this.registerSP = 0;
        this.registerIC = registerIC;
    } 

    public String getRegisterR()
    {
        return registerR;
    }
    
    public void setRegisterR( String registerR ){
        this.registerR = registerR;
    }
    
    public int getRegisterPTR() {
        return registerPTR;
    }

    public void setRegisterPTR(int registerPTR) {
        this.registerPTR = registerPTR;
    }

    public int getRegisterIC() {
        return registerIC;
    }

    public void setRegisterIC(int registerIC) {
        this.registerIC = registerIC;
    }

    public int getRegisterSP() {
        return registerSP;
    }

    public void setRegisterSP(int registerSP) {
        this.registerSP = registerSP;
    }

    public int getRegisterINT() {
        return registerINT;
    }

    public void setRegisterINT(int registerINT) {
        this.registerINT = registerINT;
    }

    public int getRegisterCT() {
        return registerCT;
    }

    public void setRegisterCT(int registerCT) {
        this.registerCT = registerCT;
    }

    public int getRegisterPI() {
        return registerPI;
    }

    public void setRegisterPI(int registerPI) {
        this.registerPI = registerPI;
    }

    public int getRegisterSI() {
        return registerSI;
    }

    public void setRegisterSI(int registerSI) {
        this.registerSI = registerSI;
    }

    public boolean isRegisterC() {
        return registerC;
    }

    public void setRegisterC(boolean registerC) {
        this.registerC = registerC;
    }

    public boolean isRegisterMOD() {
        return registerMOD;
    }

    public void setRegisterMOD(boolean registerMOD) {
        this.registerMOD = registerMOD;
    }
               
}
