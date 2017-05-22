import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;



/**
 * 
 * @author Aliona ir Eimantas
 *  
 *  
 */
public class Machine implements Runnable{
	private static CPU cpu;
	public final static int WORD_SIZE = 4;
	public final static int BLOCK_SIZE = 16;
	public final static int USER_BLOCKS = 48;
	private final static int BLOCKS = 64;
	public final static long MAX_VALUE = 0xFFFFFFFFL;
	public static int[] pagingTablesNum = new int[3 * BLOCK_SIZE];
	public static String step = "0";
	private static int dataBlocksNum = 0;
	
	public static byte memory[] = new byte[BLOCKS * BLOCK_SIZE * WORD_SIZE];
	private VM vm;
	
	public static String[] programsNames = new String[100];;
	public static String fileSystem;
	/*
	public final static byte PLR[] = new byte[WORD_SIZE];
	public static final byte AX[]  = new byte[WORD_SIZE];
	public static final byte BX[]  = new byte[WORD_SIZE];
	public static final byte IC[]  = {0, 0};  // kelinta komanda vykdoma
	public static byte C;             
    public static byte SF; // X X X CF ZF X X X  
    private byte MODE;   // 1 - supervizorius, 0 - vartotojas
    private byte CH1;
    private byte CH2;
    private byte CH3;
    private byte IOI;
    public static byte PI;
    public static byte SI;
    public static byte TI = 10;
    public static byte CS [] = {0,0};
    public static byte DS [] = {0,0};
    */
    public int channelNumber;
    public byte channelDeviceBuffer[] = new byte[64];
    public int X, Y;
    public static int programsNum = 0;
    public static Loader loader;
    public static int programNameNum = 0;
    static boolean isJump = false;
    static int jumpToRow = 0;
    static int jumpToColumn = 0;
    
    
    Machine(CPU cpu){
    	this.cpu = cpu;
    }
    
    private void loader(String programName, int vmCounter) throws Exception{
    	///Irasome psl. lenteles skaicius.
    	byte PLR[] = cpu.getPLR();
    	for(int i = 0; i < BLOCK_SIZE; i++){
    		memory[BLOCK_SIZE * WORD_SIZE * (PLR[2] * 10 + PLR[3]) + i * WORD_SIZE] = (byte) pagingTablesNum[((vmCounter - 1) * BLOCK_SIZE) + i];  ///negerai jauciu 
    		System.out.println("Paging table num " + pagingTablesNum[i]);
    	}
    	String command = " ";
    	BufferedReader inputStream = new BufferedReader(new FileReader(fileSystem));
    	int dat[] = new int[2];
    	int code[] = new int[2];
    	code[0] = 0;
    	code[1] = 0;
    	
    	while(!(command = inputStream.readLine()).equals("#" + programName)){} // failu sistemoje randame programa
    
    	expect("$WOW",inputStream);
    	//expect(".NAM ", inputStream);
    	expect(".DAT ", inputStream);
    	
    	//System.out.println("DataBlocksNum" + dataBlocksNum);
    	dat[0] = BLOCK_SIZE - dataBlocksNum;
    	//System.out.println("DAT[0] = " + dat[0]);
    	dat[1] = 0;
    	int dataSegment = dat[0];
    	byte DS[] = cpu.getDS();
    	byte CS[] = cpu.getCS();
    	DS[0] = (byte) dat[0];
		DS[1] = (byte) dat[1];
		CS[0] = (byte) code[0];
    	CS[1] = (byte) code[1];
    	cpu.setCS(CS);
    	cpu.setDS(DS);
    	int counter = 0; 
    	while(!(command = inputStream.readLine()).equals("$WRT")){ //duomenu segmenta irasome i atminti
    		writeToMemory(command, dat);
    		dat = nextAddr(dat);
    	}
    	while(!(command = inputStream.readLine()).equals("$END")){  //kodo segmenta irasome i atminti
    		writeToMemory(command, code);
    		//System.out.println("DATA SEGMENT" + dataSegment);
    		//System.out.println("CODe" + code[0]);
    		if(code[0] == dataSegment){
    			throw new Exception("Virtual machine has no more space");
    		}else{
    			code = nextAddr(code);	
    		}
    	}
    	// END
    	inputStream.close();
    }
    public void writeToMemory(String command, int[] memCoord){
    	int address = realAddress(memCoord[0], memCoord[1]);
    	//System.out.println("REAL ADDRESS " + address);
    	for(int i = 0; i < WORD_SIZE; i++){
    		memory[address + i] = (byte) command.charAt(i); 
    	}
    }
    public static int realAddress(int x, int y) {
    	byte PLR[] = cpu.getPLR();
		int pagingTableAddr = (((int) PLR[2]) * 10 + (int) PLR[3]) * BLOCK_SIZE * WORD_SIZE;
		//System.out.println("PAGING TABLE ADDRESS " + pagingTableAddr);
	    int pagingRandomNumber = memory[pagingTableAddr + x * 4]; 
	    //System.out.println("PAGING RANDOM NUMBER " + pagingRandomNumber);
	    return pagingRandomNumber * BLOCK_SIZE * WORD_SIZE + y * WORD_SIZE;  
	}
    public int[] nextAddr(int[] code) throws Exception{
    	int x = code[0];
    	int y = code[1];
    	y++;
    	if(y > 15){
    		x++;
    		y = 0;
    	}
    	if(x > 15){
    		throw new Exception("Virtual memory has no more space");
    	}
    	code[0] = x;
    	code[1] = y;
    	return code;
    }
    public void commandInterpreter() throws Exception{
    	String command = "";
    	byte IC[] = cpu.getIC();
    	int address = realAddress(IC[0], IC[1]);   // IC[0] - x, IC[1] - y (atv nei koord)
    	// Paimame komanda is atminties
    	for(int i = 0; i < WORD_SIZE; i++){
    		command += (char)memory[address + i];
    	}
    	String commandStart = command;
    	switch(commandStart.substring(0, 2)){  // paimame pirmus 2 komandos simbolius
    		// ATMINTIES KOMANDOS
    		case "LA":
    			if(command.equals("LAFB")){
    				commandLAFB();
    				break;
    			}else{
	    			commandLA(Character.getNumericValue(command.charAt(2)), 
	    					  Character.getNumericValue(command.charAt(3)));
	    			break;
    			}
    		case "LB":
    			if(command.equals("LBFA")){
    				commandLBFA();
    				break;
    			}else{
	    			commandLB(Character.getNumericValue(command.charAt(2)), 
	  					  	  Character.getNumericValue(command.charAt(3)));
	    			break;
    			}
    		case "SA":
    			commandSA(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "SB":
    			commandSB(Character.getNumericValue(command.charAt(2)), 
  					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "CO":
    			if(command.equals("COPA")){
    				commandCOPA();
    				break;
    			}else{ 
    				if(command.equals("COPB")){
    					commandCOPB();
    					break;
    				}
    			}
    		// ARITMETINES KOMANDOS
    		case "AA":
    						//  !!!!! nepadaryta su perpildymu
    			commandAA(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "AB":
				//  !!!!! nepadaryta su perpildymu
    			commandAB(Character.getNumericValue(command.charAt(2)), 
    					  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "BA":
				//  !!!!! nepadaryta su perpildymu
    			commandBA(Character.getNumericValue(command.charAt(2)), 
    					  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "BB":
				//  !!!!! nepadaryta su perpildymu
    			commandBB(Character.getNumericValue(command.charAt(2)), 
    					  Character.getNumericValue(command.charAt(3)));
    			break;
    		// LOGINES KOMANDOS
    		case "CA":
    			commandCA(Character.getNumericValue(command.charAt(2)), 
  					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "CB":
    			commandCA(Character.getNumericValue(command.charAt(2)), 
  					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		// VALDYMO PERDAVIMO KOMANDOS
    		case "JP":
    			commandJP(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "JA": // >  CF = 0 and ZF = 0
    			commandJA(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "JG": // >= CF = 0
    			commandJG(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "JB": // < CF = 1
    			commandJB(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "JL": // <= CF = 1 or ZF = 1
    			commandJL(Character.getNumericValue(command.charAt(2)), 
				  	  Character.getNumericValue(command.charAt(3)));
				break;
    		case "JN": // ZF = 0
    			commandJN(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    	    //ISVEDIMO IR IVEDIMO KOMANDOS
    		case "OP":
    			commandOP(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		case "IP":
    			commandIP(Character.getNumericValue(command.charAt(2)), 
					  	  Character.getNumericValue(command.charAt(3)));
    			break;
    		// PABAIGA
    		case "HA":
    			if(commandStart.substring(2, 4).equals("LT")){
    				commandHALT();
    			}else{
    				JOptionPane.showMessageDialog(null, "Unknown command", "Error", JOptionPane.ERROR_MESSAGE);
    				throw new Exception("Unknown command");
    			}
    			break;
    		default:
    			JOptionPane.showMessageDialog(null, "Unknown command", "Error", JOptionPane.ERROR_MESSAGE);
    			throw new Exception("Unknown command");
    	}
    }
    
    // KOMANDOS
    // ATMINTIES KOMANDOS
    public void commandLA(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	byte AX[] = cpu.getAX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		AX[i] = memory[address + i];
    	}
    	cpu.setAX(AX);
    }
    public void commandLAFB(){
    	incIC();
    	clearC();
    	cpu.setAX(cpu.getBX());
    }
    public void commandLB(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	byte BX[] = cpu.getBX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		BX[i] = memory[address + i];
    	}
    	cpu.setBX(BX);
    }
    public void commandLBFA(){
    	incIC();
    	clearC();
    	cpu.setBX(cpu.getAX());
    }
    public void commandSA(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	byte AX[] = cpu.getAX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		memory[address + i] = AX[i];
    	}
    }
    public void commandSB(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	byte BX[] = cpu.getBX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		memory[address + i] = BX[i];
    	}
    }
    public void commandCOPA(){
    	incIC();
    	clearC();
    	cpu.setAX(cpu.getBX());
    }
    public void commandCOPB(){
    	incIC();
    	clearC();
    	cpu.setBX(cpu.getAX());
    }
    // ARITMETINES KOMANDOS
    public void commandAA(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	String num1 = "";
    	String num2 = "";
    	long temp1 = 0;
    	long temp2 = 0;
    	byte AX [] = cpu.getAX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		num1 += (String) Integer.toHexString(memory[address + i]).toUpperCase();
    		num2 += (String) Integer.toHexString(unsignedToBytes(AX[i])).toUpperCase();
    	}
    	//System.out.println("NUM1 " + num1);
    	//System.out.println("NUM2 " + num2);
    	temp1 = Long.parseLong(num1, 16);
    	temp2 = Long.parseLong(num2, 16);
    	//System.out.println("Temp " + temp1);
    	//System.out.println("SUMA " + (temp1 + temp2));
    	
    	long sum = temp1 + temp2;
    	if(sum > MAX_VALUE){
    		setCF();
    		sum -= (MAX_VALUE + 1);
    		//System.out.println("PERPILDYMAS " + sum);
    	}
    	String hexSum = Long.toHexString(sum).toUpperCase();
    	
    	
    	///////////////////////////////////////////////////////////////////////////
    	if(hexSum.length() == 8){
    		for(int i = 0; i < WORD_SIZE; i++){
    			String hex = hexSum.substring(i * 2, (i * 2) + 2);
    			System.out.println(Integer.parseInt(hex, 16));
    			AX[i] = (byte) Integer.parseInt(hex, 16);
    		}
    	}
    	cpu.setAX(AX);
    }
    public void commandAB(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	String num1 = "";
    	String num2 = "";
    	long temp1 = 0;
    	long temp2 = 0;
    	byte BX[] = cpu.getBX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		num1 += (String) Integer.toHexString(memory[address + i]).toUpperCase();
    		num2 += (String) Integer.toHexString(unsignedToBytes(BX[i])).toUpperCase();
    	}
    	//System.out.println("NUM1 " + num1);
    	//System.out.println("NUM2 " + num2);
    	temp1 = Long.parseLong(num1, 16);
    	temp2 = Long.parseLong(num2, 16);
    	//System.out.println("Temp " + temp1);
    	//System.out.println("SUMA " + (temp1 + temp2));
    	
    	long sum = temp1 + temp2;
    	if(sum > MAX_VALUE){
    		setCF();
    		sum -= (MAX_VALUE + 1);
    		//System.out.println("PERPILDYMAS " + sum);
    	}
    	String hexSum = Long.toHexString(sum).toUpperCase();
    	
    	
    	///////////////////////////////////////////////////////////////////////////
    	if(hexSum.length() == 8){
    		for(int i = 0; i < WORD_SIZE; i++){
    			String hex = hexSum.substring(i * 2, (i * 2) + 2);
    			System.out.println(Integer.parseInt(hex, 16));
    			BX[i] = (byte) Integer.parseInt(hex, 16);
    		}
    	}
    	cpu.setBX(BX);
    }
    public void commandBA(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	String num1 = "";
    	String num2 = "";
    	long temp1 = 0;
    	long temp2 = 0;
    	byte AX[] = cpu.getAX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		num1 += (String) Integer.toHexString(memory[address + i]).toUpperCase();
    		num2 += (String) Integer.toHexString(unsignedToBytes(AX[i])).toUpperCase();
    	}
    	//System.out.println("NUM1 " + num1);
    	//System.out.println("NUM2 " + num2);
    	temp1 = Long.parseLong(num1, 16);
    	temp2 = Long.parseLong(num2, 16);
    	//System.out.println("Temp " + temp1);
    	//System.out.println("Temp " + temp2);
    	
    	//System.out.println("SUMA " + (temp1 + temp2));
    	
    	long sum = temp2 - temp1;
    	if(temp2 < temp1){
    		setCF();
    		sum = temp1 - temp2;
    		sum = MAX_VALUE - sum + 1;
    		System.out.println("Perpildymas");
    	}
    	String hexSum = Long.toHexString(sum).toUpperCase();
  
    	while(hexSum.length() != 8){
    		hexSum = "0" + hexSum; 
    	}
        //if(hexSum.length() == 8){
    	for(int i = 0; i < WORD_SIZE; i++){
    		String hex = hexSum.substring(i * 2, (i * 2) + 2);
       		System.out.println(Integer.parseInt(hex, 16));
   			AX[i] = (byte) Integer.parseInt(hex, 16);
    	}
    	cpu.setAX(AX);
    }
    public void commandBB(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	String num1 = "";
    	String num2 = "";
    	long temp1 = 0;
    	long temp2 = 0;
    	byte BX[] = cpu.getBX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		num1 += (String) Integer.toHexString(memory[address + i]).toUpperCase();
    		num2 += (String) Integer.toHexString(unsignedToBytes(BX[i])).toUpperCase();
    	}
    	//System.out.println("NUM1 " + num1);
    	//System.out.println("NUM2 " + num2);
    	temp1 = Long.parseLong(num1, 16);
    	temp2 = Long.parseLong(num2, 16);
    	//System.out.println("Temp " + temp1);
    	//System.out.println("Temp " + temp2);
    	
    	//System.out.println("SUMA " + (temp1 + temp2));
    	
    	long sum = temp2 - temp1;
    	if(temp2 < temp1){
    		setCF();
    		sum = temp1 - temp2;
    		sum = MAX_VALUE - sum + 1;
    		System.out.println("Perpildymas");
    	}
    	String hexSum = Long.toHexString(sum).toUpperCase();
    	
    	while(hexSum.length() != 8){
    		hexSum = "0" + hexSum; 
    	}
        //if(hexSum.length() == 8){
    	for(int i = 0; i < WORD_SIZE; i++){
    		String hex = hexSum.substring(i * 2, (i * 2) + 2);
       		System.out.println(Integer.parseInt(hex, 16));
   			BX[i] = (byte) Integer.parseInt(hex, 16);
    	}
    	cpu.setBX(BX);
    }
    // LOGINES KOMANDOS
    public void commandCA(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	int correct = 0;
    	byte AX[] = cpu.getAX();
    	System.out.println("CPU GETA AX " + cpu.getAX()[0]);
    	int counter = 0;
    	for(int j = 0; j < WORD_SIZE; j++){
    		if(AX[j] == 0){
    			counter++;
    		}
    	}
    	if(counter == 3){
    		for(int i = 0; i < WORD_SIZE; i++){
        		if(AX[i] == memory[address + i] - 48){ ///!!!!!!!!!!!!!!
        			correct++;
        		}
        	}
    	}else{
    		for(int i = 0; i < WORD_SIZE; i++){
        		if(AX[i] == memory[address + i]){ ///!!!!!!!!!!!!!!
        			correct++;
        		}
        	}
    	}
    	
    	if (correct == WORD_SIZE){
    		setC();
    		setZF();
    	}else{
    		clearZF();
    		clearC();
    	}
    }
    public void commandCB(int x, int y){
    	incIC();
    	clearC();
    	int address = realAddress(x,y);
    	int correct = 0;
    	byte BX[] = cpu.getBX();
    	for(int i = 0; i < WORD_SIZE; i++){
    		if(BX[i] == memory[address + i]){
    			correct++;
    		}
    	}
    	if (correct == WORD_SIZE){
    		setC();
    		setZF();
    	}else{
    		clearZF();
    		clearC();
    	}
    }
    // VALDYMO PERDAVIMO
    public void commandJP(int x, int y){
    	byte IC[] = cpu.getIC();
    	IC[0] = (byte)x;
    	IC[1] = (byte)y;
    	cpu.setIC(IC);
    	isJump = true;
    	jumpToRow = x;
    	jumpToColumn = y;
    }
    public void commandJA(int x, int y){
    	byte IC[] = cpu.getIC();
    	if(getBit(4) == 0 && getBit(3) == 0){ //>
    		IC[0] = (byte)x;
        	IC[1] = (byte)y;
        	cpu.setIC(IC);
        	isJump = true;
        	jumpToRow = x;
        	jumpToColumn = y;
    	}else{
    		incIC();
    	}
    }
    public void commandJG(int x, int y){ //>=
    	byte IC[] = cpu.getIC();
    	if(getBit(4) == 0){
    		IC[0] = (byte)x;
        	IC[1] = (byte)y;
        	cpu.setIC(IC);
        	isJump = true;
        	jumpToRow = x;
        	jumpToColumn = y;
    	}else{
    		incIC();
    	}
    }
    public void commandJB(int x, int y){ //< 
    	byte IC[] = cpu.getIC();
    	if(getBit(4) == 1){
    		IC[0] = (byte)x;
        	IC[1] = (byte)y;
        	cpu.setIC(IC);
        	isJump = true;
        	jumpToRow = x;
        	jumpToColumn = y;
    	}else{
    		incIC();
    	}
    }
    public void commandJL(int x, int y){ // <=
    	byte IC[] = cpu.getIC();
    	if(getBit(4) == 1 || getBit(3) == 1){
    		IC[0] = (byte)x;
        	IC[1] = (byte)y;
        	cpu.setIC(IC);
        	isJump = true;
        	jumpToRow = x;
        	jumpToColumn = y;
    	}else{
    		incIC();
    	}
    }
    public void commandJN(int x, int y){ // <=
    	byte IC[] = cpu.getIC();
    	if(getBit(3) == 0){
    		IC[0] = (byte)x;
        	IC[1] = (byte)y;
        	cpu.setIC(IC);
        	isJump = true;
        	jumpToRow = x;
        	jumpToColumn = y;
    	}else{
    		incIC();
    	}
    }
    
    //ISVEDIMO IR IVEDIMO KOMANDOS
    public void commandOP(int x, int y){
    	incIC();
    	byte SI = 2;
    	cpu.setSI(SI);
    	int address = realAddress(x,y);
    	X = x;
    	Y = y;
    }
    public void commandIP(int x, int y){
    	incIC();
    	byte SI = 1; 
    	cpu.setSI(SI);
    	int address = realAddress(x,y);
    	X = x;
    	Y = y;
    }
    // PABAIGA
    public void commandHALT(){
    	byte SI = 3;
    	cpu.setSI(SI);
    }
    public void incIC(){
    	byte IC[] = cpu.getIC();
    	IC[1]++;
    	if(IC[1] > 15){
    		IC[0]++;
    		IC[1] = 0;
    	}
    	cpu.setIC(IC);
    }
    // PAGING TABLE
    public void setPagingTable(){
		for(int i = 0; i < pagingTablesNum.length; i++){
			pagingTablesNum[i] = i;
		}
		int counter = 0;
		Random randomGenerator = new Random(System.currentTimeMillis());
		for(int i = 0; i < pagingTablesNum.length; i++){
			int random = randomGenerator.nextInt(pagingTablesNum.length - counter++);
			swap(i, random + i);
		}
	}
	public void swap(int from, int to){
		int temp = pagingTablesNum[from];
		pagingTablesNum[from] = pagingTablesNum[to];
		pagingTablesNum[to] = temp;
	}
	public void setZF(){
		byte SF = cpu.getSF();
		SF = (byte) (SF | (1 << 3));
		cpu.setSF(SF);
	}
	public void clearZF(){
		byte SF = cpu.getSF();
		SF = (byte) (SF & ~(1 << 3));
	}
	public void clearC(){
		byte C = cpu.getC();
		C = 0;
		cpu.setC(C);
	}
	public void setC(){
		byte C = cpu.getC();
		C = 1;
		cpu.setC(C);
	}
	public void setCF(){
		byte SF = cpu.getSF();
		SF = (byte) (SF | (1 << 4));
		cpu.setSF(SF);
	}
	public int getBit(int pos){
		byte SF = cpu.getSF();
		return(SF >> pos) & 1;
	}
	// INTERRUPTS
	public void checkInterrupt() throws Exception{
		
		if(cpu.getTI() == 0){
			System.out.println("Program has exceeded its time limit");
			JOptionPane.showMessageDialog(null, "Program has exceeded its time limit", "Information", JOptionPane.INFORMATION_MESSAGE);
			byte MODE = 1;
			cpu.setMODE(MODE);
			RM.supervisorMode();
			restartTimer();
			MODE = 0;
			cpu.setMODE(MODE);
			RM.userMode();
		}
		if(cpu.getPI() != 0){
			switch(cpu.getPI()){
			case 1:
				System.out.println("Program interrupt. Incorrect command");
				JOptionPane.showMessageDialog(null, "Incorrect command", "Program interrupt", JOptionPane.ERROR_MESSAGE);
				byte MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				stopProgram();
				break;
			case 2:
				System.out.println("Program interrupt. Negative result");
				JOptionPane.showMessageDialog(null, "Negative result", "Program interrupt", JOptionPane.ERROR_MESSAGE);
				MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				stopProgram();
				break;
			case 3:
				System.out.println("Program interrupt. Division by zero");
				JOptionPane.showMessageDialog(null, "Division by zero", "Program interrupt", JOptionPane.ERROR_MESSAGE);
				MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				stopProgram();
				break;
			case 4:
				System.out.println("Program interrupt. Program overflow");
				JOptionPane.showMessageDialog(null, "Program overflow", "Program interrupt", JOptionPane.ERROR_MESSAGE);
				MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				stopProgram();
				break;
			}
		}
		if(cpu.getSI() != 0){
			switch(cpu.getSI()){
			case 1:
				System.out.println("Program interrupt. Data input");
				//JOptionPane.showMessageDialog(null, "Data input", "Program interrupt", JOptionPane.INFORMATION_MESSAGE);
				byte MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				channelNumber = 1;
				MODE = 0;
				cpu.setMODE(MODE);
				RM.userMode();
				byte SI = 0;
				cpu.setSI(SI);
				break;
			case 2:
				System.out.println("Program interrupt. Data output");
				//JOptionPane.showMessageDialog(null, "Data output", "Program interrupt", JOptionPane.INFORMATION_MESSAGE);
				MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				channelNumber = 2;
				MODE = 0;
				cpu.setMODE(MODE);
				RM.userMode();
				SI = 0;
				cpu.setSI(SI);
				break;
			case 3:
				System.out.println("Program interrupt. Command halt");
				JOptionPane.showMessageDialog(null, "Command halt", "Program interrupt", JOptionPane.INFORMATION_MESSAGE);
				cpu.setMODE(MODE = 1);
				RM.supervisorMode();
				stopProgram();
				break;
			}
		}
		if(cpu.getIOI()!= 0){
			switch(cpu.getIOI()){
			case 1:
				System.out.println("Channel 1 done");
				//JOptionPane.showMessageDialog(null, "Channel 1 done", "Information", JOptionPane.INFORMATION_MESSAGE);
				byte MODE = 1;
				cpu.setMODE(MODE);
				RM.supervisorMode();
				byte IOI = 0;
				cpu.setIOI(IOI);
				cpu.setMODE(MODE = 0);
				RM.userMode();
				break;
			case 2:
				System.out.println("Channel 2 done");
				//JOptionPane.showMessageDialog(null, "Channel 2 done", "Information", JOptionPane.INFORMATION_MESSAGE);
				cpu.setMODE(MODE = 1);
				RM.supervisorMode();
				cpu.setIOI(IOI = 0);
				cpu.setMODE(MODE = 0);
				RM.userMode();
				break;
			case 3:
				System.out.println("Channel 3 done");
				//JOptionPane.showMessageDialog(null, "Channel 3 done", "Information", JOptionPane.INFORMATION_MESSAGE);
				cpu.setMODE(MODE = 1);
				RM.supervisorMode();
				cpu.setIOI(IOI = 0);
				cpu.setMODE(MODE = 0);
				RM.userMode();
				break;
			}
		}
	}
	public void restartTimer(){
		if(cpu.getMODE() == 1){
			cpu.setTI((byte) 10);
			System.out.println("Supervisor => Timer restarted succesfully");
		}
	}
	public void startIO() throws IOException{
		if(channelNumber == 1){
			channelNumber = 0;
			cpu.setCH1((byte) 1);
			System.out.println("INPUT DATA ");
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//String input = br.readLine();
			//String input = JOptionPane.showInputDialog("Input");
			JOptionPane.showMessageDialog(null, "Áveskite duomenis á ávedimo langà","Data input", JOptionPane.INFORMATION_MESSAGE);
			String input = "";
			while(!VM.enterInputText){
				System.out.print("");
			}
			input = VM.textPane.getText();
			System.out.println("DATA " + input);
			int length = input.length();
			if(length > 64){
				length = 64;
			}
			for(int i = 0; i < length; i++){
				channelDeviceBuffer[i] = (byte) input.charAt(i);
			}
			// PABAIGOS SIMBOLIS
			if(length < 64){
				channelDeviceBuffer[length] = (byte) '$';     // 36
			}
			int startPos = X * BLOCK_SIZE + Y;  ///F
			outerloopCH1:
			for(int i = 0; i < BLOCK_SIZE; i++){
				int x = (startPos + i) / 16;
				int y = (startPos + i) % 16;
				int address = realAddress(x, y);
				for(int j = 0; j < WORD_SIZE; j++){
					if(channelDeviceBuffer[i * WORD_SIZE + j] == '$'){
						break outerloopCH1;
					}
					memory[address + j] = channelDeviceBuffer[i * WORD_SIZE + j];
				}
			}
		    cpu.setCH1((byte) 0);
		    byte IOI = cpu.getIOI();
			IOI += 1;
			cpu.setIOI(IOI);
			printMemory();
		}
		if(channelNumber == 2){
			channelNumber = 0;
			cpu.setCH2((byte) 1);
			int startPos = X * BLOCK_SIZE + Y;  // !!!!!!!!!!!!!!!!!!!
			outerLoopCH2:
			for(int i = 0; i < BLOCK_SIZE; i++){
				int x = (startPos + i) / 16;
				int y = (startPos + i) % 16;
				int address = realAddress(x, y);
				for(int j = 0; j < WORD_SIZE; j++){
					channelDeviceBuffer[i * WORD_SIZE + j] = memory[address + j];
					if(memory[address + j] == '$'){
						break outerLoopCH2;
					}
				}
			}
			for(int i = 0; i < 64; i++){
				if(channelDeviceBuffer[i] == '$'){
					System.out.println();
					break;
				}
				System.out.write(channelDeviceBuffer[i]);
			}
			String output = "";
			for(int i = 0; i < channelDeviceBuffer.length; i++){
				output += String.valueOf(Character.toChars(channelDeviceBuffer[i]));
			}
			System.out.println(output);
			VM.textPane_1.setText(output);
			//JOptionPane.showMessageDialog(null, output ,"Data output", JOptionPane.INFORMATION_MESSAGE);
			
			cpu.setCH2((byte) 0);
			byte IOI = cpu.getIOI();
			IOI += 2;
			cpu.setIOI(IOI);
			//printMemory();
		}
		if(channelNumber == 3){
			channelNumber = 0;
			cpu.setCH3((byte) 1);
			cpu.setCH3((byte) 0);
			byte IOI = cpu.getIOI();
			IOI += 4;
			cpu.setIOI(IOI);
		}
	}
	public void stopProgram() throws Exception{
		throw new Exception("Stop program");
		//System.exit(0);
	}
    public static void expect(String expectCommand, BufferedReader inputStream) throws Exception{
    	String command = inputStream.readLine();
    	if(command.startsWith((expectCommand))){
    		if(expectCommand == ".DAT "){
    			if(command.length() == 6){
    				dataBlocksNum = Integer.parseInt(command.substring(5)); 
    			}else if(command.length() > 6){
    				dataBlocksNum = Integer.parseInt(command.substring(5, command.length())); 
    			}
    		}
    	}else{
    		JOptionPane.showMessageDialog(null,"Invalid command " + command + " expected " + expectCommand, "Error", JOptionPane.ERROR_MESSAGE);
    		Machine.programsNum--;
    		loader.setCheckCommands(false);
    		throw new Exception("Invalid command " + command + " expected " + expectCommand);
    	}
    }
    public void printMemory(){
   /* 	for(int i = 0; i < BLOCKS * BLOCK_SIZE * WORD_SIZE; i++){
    		System.out.println(i + "| " + (char)memory[i]);
		}
	*/
    	RM.printMemory();
    	vm.printMemory();
    	
    	
    }
    public void printRegisters(){
    	/*
    	System.out.println("AX = " + (Integer.toHexString(unsignedToBytes(AX[0])).toUpperCase()) + " " + 
    		Integer.toHexString(unsignedToBytes(AX[1])).toUpperCase() + " " + Integer.toHexString(unsignedToBytes(AX[2])).toUpperCase()
    		+ " " + Integer.toHexString(unsignedToBytes(AX[3])).toUpperCase());
    	System.out.println("BX = " + (Integer.toHexString(BX[0]).toUpperCase()) + " " + 
        	Integer.toHexString(BX[1]).toUpperCase() +" " + Integer.toHexString(BX[2]).toUpperCase()
        	+ " " + Integer.toHexString(BX[3]).toUpperCase());
     	System.out.println("IC = " + (Integer.toHexString(IC[0]).toUpperCase()) + " " + 
        Integer.toHexString(IC[1]).toUpperCase());
    	System.out.println("C  = " + C);
    */
    	RM.printRegisters(cpu);
    	VM.printRegisters(cpu);
    }
    public static int unsignedToBytes(byte b){
    	return b & 0XFF;
    }
    public void clearMemory(){
    	byte memory[] = new byte[BLOCKS * BLOCK_SIZE * WORD_SIZE];
    	Machine.memory = memory;
    }
     ////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!gali but kad reikia
    /*
    public void clearRegistersVm(){
    	for(int i = 0; i < WORD_SIZE; i++){
    		AX[i] = 0;
    		BX[i] = 0;
    	}
    	IC[0] = 0;
    	IC[1] = 0;
    	SF = 0;
    	TI = 10;
    }
    */
    
    public CPU getCPU(){
    	return cpu;
    }
    
    public void run(){
    	try {
			RM.supervisorMode();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	/*
    	if(Machine.PLR[3] == 4){
    		programsNum = 0;
        */
    	//Machine machine = new Machine();
        loader = new Loader(fileSystem); // gauname programu masyvo vardus
        try{
        	 programsNames = loader.getProgramsNames();
        }catch(Exception e){
        	System.out.println(e.toString());
        }
    	if(programsNum == 0){
    		cpu.clearRegisters();
    		clearMemory();
    		setPagingTable();
    		//machine.printMemory();
    		//machine.printRegisters();
    	}
    	for(int i = 0; i < programNameNum; i++){
    		//clearRegistersVm();
    		/*
    		if(Machine.PLR[3] == 4){
        		programsNum = 0;
        		machine.clearRegisters();
        		machine.clearMemory();
        		machine.setPagingTable();
    	    }*/
    		int counter = 0;
    		boolean run = true;
    		RM.currentProgram(programsNames[i]);
    		programsNum++;
    		//pradedame vykdyti programa
    		while(run){
    			try{
    				startIO();
    				//machine.printRegisters();
    				if(counter == 0){
    					cpu.setMODE((byte) 1);
    					VM.setVmColumn(0);
						VM.setVmRow(0);
    					loader.checkCommands(programsNames[i]); //patikriname ar korektiska programa
    					loader(programsNames[i], programsNum);
    					vm = new VM(this);
    			    	vm.vm();
    			    	printMemory();
    			    	vm.setModeWait();
        			}
    				RM.userMode();
    				printRegisters();
    				if(vm.getMode() != 2){
    					while(vm.getMode() == 0){
    						System.out.print("");
        			    }
    					if(vm.getMode() != 2){
    						vm.setModeWait();
    					}
    				}
		    		printMemory();
			   		commandInterpreter();
			   		byte TI = cpu.getTI();
			   		TI -= 1;
			   		cpu.setTI(TI);
			   		checkInterrupt();
			   		counter++;
    			}catch(Exception e){
    				if(loader.checkCommands()){
    					byte PLR[] = cpu.getPLR();
    					PLR[3]++;
    					cpu.setPLR(PLR);
    				}else{
    					loader.setCheckCommands(true);
    				}
		    		//machine.plr3++;
    				cpu.setSI((byte) 0);
    				byte IC[] = cpu.getIC();
					IC[0] = 0;
					IC[1] = 0;
					cpu.setTI((byte) 10);
					if(vm.frmVm != null){
						vm.frmVm.dispose();
					}
					run = false;
		    		break;
    			}
    		}
    	}
    }
}