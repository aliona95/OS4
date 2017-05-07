import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Canvas;
import java.awt.Button;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.Label;
import java.awt.TextField;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;

public class VM extends Thread{
	Machine machine;
    public static int mode = 0; // 0 - laukiama kol paspausime rezima, 1 - zingsninis rezimas, 2 - nuolatinis.
	public static JFrame frmVm;
	public static JTable table;
	private int dataBloksNum = 0;
	public static TextField textAX;
	public static TextField textBX;
	public static TextField textIC;
	public static TextField textC;
	public static TextField textSF;
	public static TextField textDS;
	public static TextField textCS;
	private TextField textField;
	private Label label;
	private TextField textField_1;
	private Label label_1;
	public static int vmTableRow = 0;
	public static int vmTableColumn = 0;
	/**
	 * Launch the application.
	 */
	public void vm() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VM window = new VM(machine);
					window.frmVm.setVisible(true);
					frmVm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frmVm.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					    	/*
					    	Machine.PLR[3]++;
					    	Machine.IC[0] = 0;
					    	Machine.IC[1] = 0;
					    	*/
					    	try {
								RM.supervisorMode();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }
					});
					//window.checkCommands(); //pildome nuliais
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VM(Machine machine) {
		this.machine = machine;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVm = new JFrame();
		frmVm.setTitle("VM");
		frmVm.getContentPane().setBackground(new Color(248, 248, 255));
		frmVm.getContentPane().setLayout(null);
		
		Label label_2 = new Label("Registrai");
		label_2.setForeground(Color.BLUE);
		label_2.setBounds(28, 30, 62, 22);
		frmVm.getContentPane().add(label_2);
		
		Label label_3 = new Label("AX");
		label_3.setBounds(26, 66, 28, 22);
		frmVm.getContentPane().add(label_3);
		
		textAX = new TextField();
		textAX.setText("0000");
		textAX.setEditable(false);
		textAX.setEnabled(false);
		textAX.setBackground(Color.LIGHT_GRAY);
		textAX.setForeground(Color.BLACK);
		textAX.setBounds(60, 66, 72, 22);
		frmVm.getContentPane().add(textAX);
		
		Label label_4 = new Label("BX");
		label_4.setBounds(26, 107, 28, 22);
		frmVm.getContentPane().add(label_4);
		
		textBX = new TextField();
		textBX.setText("0000");
		textBX.setEnabled(false);
		textBX.setEditable(false);
		textBX.setBackground(Color.LIGHT_GRAY);
		textBX.setBounds(60, 107, 72, 22);
		frmVm.getContentPane().add(textBX);
		
		Label label_6 = new Label("IC");
		label_6.setBounds(152, 66, 28, 22);
		frmVm.getContentPane().add(label_6);
		
		textIC = new TextField();
		textIC.setText("0000");
		textIC.setEnabled(false);
		textIC.setEditable(false);
		textIC.setBackground(Color.LIGHT_GRAY);
		textIC.setBounds(183, 66, 47, 22);
		frmVm.getContentPane().add(textIC);
		
		Label label_7 = new Label("C");
		label_7.setBounds(152, 107, 22, 22);
		frmVm.getContentPane().add(label_7);
		
		textC = new TextField();
		textC.setText("FALSE");
		textC.setEnabled(false);
		textC.setEditable(false);
		textC.setBackground(Color.LIGHT_GRAY);
		textC.setBounds(183, 107, 47, 22);
		frmVm.getContentPane().add(textC);
		
		Label label_9 = new Label("SF");
		label_9.setBounds(249, 66, 22, 22);
		frmVm.getContentPane().add(label_9);
		
		textSF = new TextField();
		textSF.setText("0000");
		textSF.setEnabled(false);
		textSF.setEditable(false);
		textSF.setBackground(Color.LIGHT_GRAY);
		textSF.setBounds(277, 66, 72, 22);
		frmVm.getContentPane().add(textSF);
		
		table = new JTable();
		table.setBorder(UIManager.getBorder("ComboBox.border"));
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setToolTipText("");
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7), new Integer(8), new Integer(9), "A", "B", "C", "D", "E", "F"},
				{new Integer(0), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(1), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(2), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(3), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(4), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(5), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(6), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(7), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(8), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(9), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"A", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"B", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"C", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"D", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"E", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{"F", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
					"-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
			}
		));
		
		// !!!!! UZSETINTI VISA LENTELE NULIAIS
		table.setBackground(new Color(255, 215, 0));
		table.setBounds(30, 177, 636, 272);
		
		/////////////////////////////////////////////////////////////////////
		/*VM.MyCellRenderer mcr = new VM.MyCellRenderer();
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
        	table.getColumnModel().getColumn(columnIndex).setCellRenderer(mcr); 
        }
		*/
		
		frmVm.getContentPane().add(table);
		
		textDS = new TextField();
		textDS.setText("0000");
		textDS.setEnabled(false);
		textDS.setEditable(false);
		textDS.setBackground(Color.LIGHT_GRAY);
		textDS.setBounds(277, 107, 72, 22);
		frmVm.getContentPane().add(textDS);
		
		label = new Label("DS");
		label.setBounds(249, 107, 22, 22);
		frmVm.getContentPane().add(label);
		
		textCS = new TextField();
		textCS.setText("0000");
		textCS.setEnabled(false);
		textCS.setEditable(false);
		textCS.setBackground(Color.LIGHT_GRAY);
		textCS.setBounds(398, 66, 72, 22);
		frmVm.getContentPane().add(textCS);
		
		label_1 = new Label("CS");
		label_1.setBounds(370, 66, 22, 22);
		frmVm.getContentPane().add(label_1);
		
		JButton btnNuolatinis = new JButton("Nuolatinis");
		btnNuolatinis.setBackground(Color.LIGHT_GRAY);
		btnNuolatinis.setToolTipText("");
		btnNuolatinis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("PASPAUSTA Nuolatinis");
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PERPIESTI
				mode = 2;
			}
		});
		btnNuolatinis.setBounds(523, 107, 100, 22);
		frmVm.getContentPane().add(btnNuolatinis);
		
		JButton btningsninis = new JButton("\u017Dingsninis");
		btningsninis.setBackground(Color.LIGHT_GRAY);
		btningsninis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("PASPAUSTA Zingsninis");
				mode = 1;
				VM.MyCellRenderer mcr = new VM.MyCellRenderer();
		        //for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
				if(vmTableColumn == 16){
					vmTableColumn = 0;
					vmTableRow++;
				}
				vmTableColumn++;	
				table.getColumnModel().getColumn(VM.getVmColumn()).setCellRenderer(mcr); 
		        	
		        	//}
				frmVm.validate();
			}
		});
		btningsninis.setBounds(523, 66, 100, 23);
		frmVm.getContentPane().add(btningsninis);
		
		Label label_5 = new Label("Re\u017Eimai");
		label_5.setForeground(Color.BLUE);
		label_5.setBounds(523, 30, 62, 22);
		frmVm.getContentPane().add(label_5);
		frmVm.setBounds(100, 100, 703, 510);
		frmVm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/*
	public void checkCommands() throws Exception{	
    	// visa uzpildom nuliais
    	for(int row = 1; row < 17; row++){ 
    		for(int column = 1; column < 17; column++){
    			table.setValueAt("0000", row, column);
    		}
    	}
	}
	*/
	// setters & getters
	public static void setVmRow(int row){
		vmTableRow = row;
	}
	public static void setVmColumn(int column){
		vmTableColumn = column;
	}
	public static int getVmRow(){
		return vmTableRow;
	}
	public static int getVmColumn(){
		return vmTableColumn;
	}
	public JTable getVmTable(){
		return this.table;
	}
	public void printMemory(){
		String memory = "";
		for(int row = 1; row < 17; row++){ 
    		for(int column = 1; column < 17; column++){
    			int address = machine.realAddress(row - 1, column - 1);
    			System.out.println("GAUNAMOS VM ADDRESAI X" + (row - 1) +" Y " + (column - 1));
    			//System.out.println("VM MASINA-------- "+ address);
    			for(int i = 0; i < Machine.WORD_SIZE; i++){
    				memory += (char)Machine.memory[address + i];
        		}
    			System.out.println("memory " + memory);
    			if(!memory.trim().isEmpty()){
    				//System.out.println("DUOMENYS " + memory + memory.length()) ;
    			}
    			System.out.println("memory " +memory);
    			table.setValueAt(memory, row, column);
    			memory = "";
    		}
    	}
	}
	public static void printRegisters(CPU cpu){
		/*
		textAX.setText(Integer.toHexString(Machine.unsignedToBytes(Machine.AX[0])).toUpperCase() + 
			Integer.toHexString(Machine.unsignedToBytes(Machine.AX[1])).toUpperCase() + 
			Integer.toHexString(Machine.unsignedToBytes(Machine.AX[2])).toUpperCase() + 
			Integer.toHexString(Machine.unsignedToBytes(Machine.AX[3])).toUpperCase());
		textBX.setText(Integer.toHexString(Machine.unsignedToBytes(Machine.BX[0])).toUpperCase() + 
				Integer.toHexString(Machine.unsignedToBytes(Machine.BX[1])).toUpperCase() + 
				Integer.toHexString(Machine.unsignedToBytes(Machine.BX[2])).toUpperCase() + 
				Integer.toHexString(Machine.unsignedToBytes(Machine.BX[3])).toUpperCase());
		textSF.setText(Integer.toHexString(Machine.unsignedToBytes(Machine.SF)).toUpperCase());
		textIC.setText((Integer.toHexString(Machine.IC[0]).toUpperCase()) + " " + 
	        	Integer.toHexString(Machine.IC[1]).toUpperCase());
	    textDS.setText((Integer.toHexString(Machine.DS[0]).toUpperCase()) + 
	        	Integer.toHexString(Machine.DS[1]).toUpperCase());
	    textCS.setText((Integer.toHexString(Machine.CS[0]).toUpperCase()) + 
	        	Integer.toHexString(Machine.CS[1]).toUpperCase());
		if(Machine.C == 0){
			textC.setText("FALSE");
		}else{
			textC.setText("TRUE");
		}
		*/
	}
	public int getMode(){
		return mode;
	}
	public void setModeWait(){
		mode = 0;
	}
	//////
	
	public static class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
        	
        	java.awt.Component  cellComponent = super.getTableCellRendererComponent(table, value, 
        		isSelected, hasFocus, row, column);
        	if(Machine.isJump){
        		VM.setVmColumn(Machine.jumpToColumn);
        		VM.setVmRow(Machine.jumpToRow);
        		setBackground((row == VM.getVmRow() + 1) && (column == VM.getVmColumn() + 1) ? Color.lightGray : new Color(255, 215, 0));
        		System.out.println("JUMPAS " + Machine.jumpToColumn + " " + Machine.jumpToRow);
        		Machine.isJump = false;
        	}else{
        		setBackground((row == VM.getVmRow() + 1) && (column == VM.getVmColumn()) ? Color.lightGray : new Color(255, 215, 0));
        	}
        	System.out.println("VM ROW = " + VM.getVmRow());
        	System.out.println("VM COLUMN = " + VM.getVmColumn());
        	return cellComponent;
        }
	}    
	
}
