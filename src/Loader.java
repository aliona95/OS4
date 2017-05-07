import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Loader {
	private final int programNameLength = 10; //nurodomas programos vardo ilgis 10 baitu
	private String fileSystem;
	private boolean checkComands = true;
	Loader(String fileSystem){
		this.fileSystem = fileSystem;
	}
	
	public void checkCommands(String progamName) throws Exception{
		BufferedReader inputStream = new BufferedReader(new FileReader(fileSystem));
		String command = "";
		
	    while(command != null){ //ieskome programos failu sistemoje
	    	command = inputStream.readLine();
	    	if(command == null){
	    		JOptionPane.showMessageDialog(null,"Programa " + progamName + " failu sistemoje neegzistuoja ", "Error", JOptionPane.ERROR_MESSAGE);
	    		Machine.programsNum--;
	    		checkComands = false;
	    		throw new Exception("Programa " + progamName + " failu sistemoje neegzistuoja ");
	    	}
	    	if(command.equals('#' + progamName)){
	    		if(command.length() > programNameLength){
	    			JOptionPane.showMessageDialog(null,"Programos vardas turi buti ne ilgesnis nei 10b", "Error", JOptionPane.ERROR_MESSAGE);
	    			Machine.programsNum--;
	    			checkComands = false;
	    			throw new Exception("Programos vardas turi buti ne ilgesnis nei 10b");
	    		}
	    		break;
			}
	    }
	    Machine.expect("$WOW",inputStream);
	    Machine.expect(".DAT ", inputStream);	
	    int line = 3; //skaiciuosime programos eilutes
	    command = "";
	    while(command != null){ //skaitome duomenu segmenta
	    	command = inputStream.readLine();
	    	line++;
	    	if(command == null){
	    		JOptionPane.showMessageDialog(null,"Nerasta duomenu segmento pabaiga", "Error", JOptionPane.ERROR_MESSAGE);
	    		Machine.programsNum--;
	    		checkComands = false;
	    		throw new Exception("Nerasta duomenu segmento pabaiga");
	    	}
	    	if(command.equals("$WRT")){ //tikriname duomenu segmento pabaiga
	    		break;
			}
	    	checkDataCommandLength(command, line);
	    }
	    
	    command = "";
	    while(command != null){ //tikriname kodo segmenta
	    	command = inputStream.readLine();
	    	line++;
	    	if(command == null){
	    		JOptionPane.showMessageDialog(null,"Nerasta kodo segmento pabaiga", "Error", JOptionPane.ERROR_MESSAGE);
	    		Machine.programsNum--;
	    		checkComands = false;
	    		throw new Exception("Nerasta kodo segmento pabaiga");
	    	}
	    	if(command.equals("$END")){ //suradome kodo segmento pabaiga
	    		break;
			}
	    	checkCodeCommandLength(command, line);
	    	checkCommand(command, line);
	    }
    	inputStream.close();
	}	
	
    public void checkDataCommandLength(String command, int line) throws Exception{
    	if(command.length() > Machine.WORD_SIZE){
    		JOptionPane.showMessageDialog(null,"Duomenu eilute neuzima daugiau nei " + Machine.WORD_SIZE + ". Klaida " + line + "-oje eiluteje.   ", "Error", JOptionPane.ERROR_MESSAGE);
    		Machine.programsNum--;
    		checkComands = false;
    		throw new Exception("Duomenu eilute neuzima daugiau nei " + Machine.WORD_SIZE + ". Klaida " + line + "-oje eiluteje.   ");
    	}
    }
    public void checkCodeCommandLength(String command, int line) throws Exception{
    	if(command.length() != Machine.WORD_SIZE){
    		JOptionPane.showMessageDialog(null,"Komandos dydis turi buti " + Machine.WORD_SIZE + ". Klaida " + line + "-oje eiluteje.   ", "Error", JOptionPane.ERROR_MESSAGE);
    		Machine.programsNum--;
    		checkComands = false;
    		throw new Exception("Komandos dydis turi buti " + Machine.WORD_SIZE + ". Klaida " + line + "-oje eiluteje.   ");
    	}
    }
	public void checkCommand(String command, int line) throws Exception{
	   String commandStart = command;   
	   switch(commandStart.substring(0, 2)){
	   // ATMINTIES KOMANDOS
		case "LA":
			break;
		case "LB":
   			break;
		case "SA":
			break;
		case "SB":
			break;
		case "CO":
			break;
		// ARITMETINES KOMANDOS
		case "AA":
			break;
		case "AB":
			break;
		case "BA":
			break;
		case "BB":
			break;
		// LOGINES KOMANDOS
		case "CA":
			break;
		case "CB":
			break;
		// VALDYMO PERDAVIMO KOMANDOS
		case "JP":
			break;
		case "JA":	
			break;
		case "JL":
			break;
		case "JB":
			break;
		case "JG":
			break;
		case "JN":
			break;
	    //ISVEDIMO IR IVEDIMO KOMANDOS
		case "OP":
			break;
		case "IP":
			break;
		// PABAIGA
		case "HA":
			if(!commandStart.substring(2, 4).equals("LT")){
				JOptionPane.showMessageDialog(null, "Nezinoma komanda " + command + " " + line + "-oje eiluteje.   ", "Error", JOptionPane.ERROR_MESSAGE);
				Machine.programsNum--;
				checkComands = false;
				throw new Exception("Neatpazinta komanda " + command + " " + line + "-oje eiluteje.   ");
			}
			break;
		default:
			JOptionPane.showMessageDialog(null, "Nezinoma komanda " + command + " " + line + "-oje eiluteje.   ", "Error", JOptionPane.ERROR_MESSAGE);
			Machine.programsNum--;
			checkComands = false;
			throw new Exception("Neatpazinta komanda " + command + " " + line + "-oje eiluteje.   ");
	   }
	}
	public boolean checkCommands(){
		return checkComands;
	}
	public void setCheckCommands(boolean checkComands){
		this.checkComands = checkComands;
	}
	public String[] getProgramsNames() throws IOException{
		String[] programsNames = new String[100];
		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(fileSystem));
			String command = "";
			int n = 0;
			Machine.programsNames =  programsNames;
		    while (inputStream != null) {
		    	command = inputStream.readLine();
		    	if(command.startsWith("#")){
		    		Machine.programsNames[n] = command.substring(1);
		    		n++;
		    		Machine.programNameNum = n;
		    	}
			}  
		} catch (FileNotFoundException e) {
			System.out.println("Nerasta failu sistema");
			//e.printStackTrace();
		}
		return programsNames;
	}
}