
public class CPU {
	private byte PLR[] = new byte[Machine.WORD_SIZE];
	private byte AX[]  = new byte[Machine.WORD_SIZE];
	private byte BX[]  = new byte[Machine.WORD_SIZE];
	private byte IC[]  = {0, 0};  // kelinta komanda vykdoma
	private byte C;             
    private byte SF; // X X X CF ZF X X X  
    private byte MODE;   // 1 - supervizorius, 0 - vartotojas
    private byte CH1;
    private byte CH2;
    private byte CH3;
    private byte IOI;
    private byte PI;
    private byte SI;
    private byte TI = 10;
    private  byte CS [] = {0,0};
    private byte DS [] = {0,0};
    
	CPU(byte MODE, byte PLR[]){
		this.setPLR(PLR);
		this.setMODE(MODE);
	}
	CPU(){
		
	}

	public byte[] getPLR() {
		return PLR;
	}

	public void setPLR(byte pLR[]) {
		PLR = pLR;
	}

	public byte[] getAX() {
		return AX;
	}

	public void setAX(byte aX[]) {
		AX = aX;
	}

	public byte[] getBX() {
		return BX;
	}

	public void setBX(byte bX[]) {
		BX = bX;
	}

	public byte[] getIC() {
		return IC;
	}

	public void setIC(byte iC[]) {
		IC = iC;
	}

	public byte getC() {
		return C;
	}

	public void setC(byte c) {
		C = c;
	}

	public byte getSF() {
		return SF;
	}

	public void setSF(byte sF) {
		SF = sF;
	}

	public byte getMODE() {
		return MODE;
	}

	public void setMODE(byte mODE) {
		MODE = mODE;
	}

	public byte getCH1() {
		return CH1;
	}

	public void setCH1(byte cH1) {
		CH1 = cH1;
	}

	public byte getCH2() {
		return CH2;
	}

	public void setCH2(byte cH2) {
		CH2 = cH2;
	}

	public byte getCH3() {
		return CH3;
	}

	public void setCH3(byte cH3) {
		CH3 = cH3;
	}

	public byte getIOI() {
		return IOI;
	}

	public void setIOI(byte iOI) {
		IOI = iOI;
	}

	public byte getPI() {
		return PI;
	}

	public void setPI(byte pI) {
		PI = pI;
	}

	public byte getSI() {
		return SI;
	}

	public void setSI(byte sI) {
		SI = sI;
	}

	public byte getTI() {
		return TI;
	}

	public void setTI(byte tI) {
		TI = tI;
	}

	public byte[] getCS() {
		return CS;
	}

	public void setCS(byte cS[]) {
		CS = cS;
	}

	public byte[] getDS() {
		return DS;
	}

	public void setDS(byte dS[]) {
		DS = dS;
	}
	public void clearRegisters(){
    	for(int i = 0; i < Machine.WORD_SIZE; i++){
    		AX[i] = 0;
    		BX[i] = 0;
    	}
    	IC[0] = 0;
    	IC[1] = 0;
    	SF = 0;
    	PLR[0] = 0;
        PLR[1] = 15;
        PLR[2] = 6;
    	PLR[3] = 1;
    	TI = 10;
    } 
}
