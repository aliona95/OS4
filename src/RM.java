package os;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.ScrollPane;
import java.awt.Canvas;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
// norint pakeisti failu sistemos failo pavadinima:
// ctrl+f fileSystem = "failas.txt";
public class RM {
	static JFrame frmMm;
	public static JTable table_1;
	public static String fileSystem;
	public static TextField textAX;
	public static TextField textBX;
	public static TextField textPLR;
	public static TextField textIC;
	public static TextField textC;
	public static TextField textPI;
	public static TextField textSI;
	public static TextField textTI;
	public static TextField textDS;
	public static TextField textCS;
	public static JRadioButton userButton;
	public static JRadioButton supervisorButton;
	public static int programsNum = 0;
	public static boolean exit = true;
	public static JPanel panel;
		
	static JRadioButton[] fontButtons = new JRadioButton[3];
	static ButtonGroup fontGroup = new ButtonGroup();
	private static JTable table;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RM window = new RM();
					window.frmMm.setVisible(true);
					supervisorMode();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RM() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMm = new JFrame();
		frmMm.setTitle("RM");
		frmMm.getContentPane().setBackground(new Color(248, 248, 255));
		frmMm.getContentPane().setLayout(null);
		
		Label label = new Label("Nustatymai ");
		label.setBounds(10, 10, 72, 22);
		label.setForeground(new Color(0, 128, 128));
		frmMm.getContentPane().add(label);
		
		Label label_1 = new Label("Re\u017Eimas");
		label_1.setBounds(20, 38, 62, 22);
		frmMm.getContentPane().add(label_1);
		
		userButton = new JRadioButton("Vartotojas");
		userButton.setBounds(30, 65, 109, 23);
		userButton.setBackground(new Color(248, 248, 255));
		frmMm.getContentPane().add(userButton);
		
		supervisorButton = new JRadioButton("Supervizorius");
		supervisorButton.setBounds(30, 93, 109, 23);
		supervisorButton.setBackground(new Color(248, 248, 255));
		frmMm.getContentPane().add(supervisorButton);
		
		
		
		Label label_2 = new Label("Registrai");
		label_2.setBounds(157, 10, 62, 22);
		label_2.setForeground(new Color(0, 128, 128));
		frmMm.getContentPane().add(label_2);
		
		Label label_3 = new Label("PLR");
		label_3.setBounds(144, 38, 35, 22);
		frmMm.getContentPane().add(label_3);
		
		textPLR = new TextField();
		textPLR.setBounds(185, 38, 73, 22);
		textPLR.setText("0000");
		textPLR.setEditable(false);
		textPLR.setEnabled(false);
		textPLR.setBackground(Color.LIGHT_GRAY);
		textPLR.setForeground(Color.BLACK);
		frmMm.getContentPane().add(textPLR);
		
		Label label_5 = new Label("IC");
		label_5.setBounds(151, 94, 28, 22);
		frmMm.getContentPane().add(label_5);
		
		textIC = new TextField();
		textIC.setBounds(185, 94, 47, 22);
		textIC.setText("0000");
		textIC.setEditable(false);
		textIC.setEnabled(false);
		textIC.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textIC);
		
		Label label_6 = new Label("PI");
		label_6.setBounds(264, 38, 15, 22);
		frmMm.getContentPane().add(label_6);
		
		textPI = new TextField();
		textPI.setBounds(298, 38, 47, 22);
		textPI.setText("0000");
		textPI.setEnabled(false);
		textPI.setEditable(false);
		textPI.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textPI);
		
		Label label_7 = new Label("SI");
		label_7.setBounds(264, 66, 15, 22);
		frmMm.getContentPane().add(label_7);
		
		textSI = new TextField();
		textSI.setBounds(298, 66, 47, 22);
		textSI.setText("0000");
		textSI.setEnabled(false);
		textSI.setEditable(false);
		textSI.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textSI);
		
		Label label_8 = new Label("TI");
		label_8.setBounds(264, 94, 15, 22);
		frmMm.getContentPane().add(label_8);
		
		textTI = new TextField();
		textTI.setBounds(298, 94, 47, 22);
		textTI.setText("0000");
		textTI.setEnabled(false);
		textTI.setEditable(false);
		textTI.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textTI);
		
		Label label_9 = new Label("C");
		label_9.setBounds(154, 66, 22, 22);
		frmMm.getContentPane().add(label_9);
		
		textC = new TextField();
		textC.setBounds(185, 66, 47, 22);
		textC.setText("FALSE");
		textC.setEnabled(false);
		textC.setEditable(false);
		textC.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textC);
		
		JButton btnPradti = new JButton("Prad\u0117ti");
		btnPradti.setBounds(712, 644, 89, 23);
		btnPradti.setForeground(new Color(255, 255, 255));
		btnPradti.setBackground(new Color(0, 128, 128));
		frmMm.getContentPane().add(btnPradti);
		
			
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 123, 664, 295);
		scrollPane.setBackground(Color.ORANGE);
		frmMm.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setRowSelectionAllowed(false);
		table_1.setBackground(Color.ORANGE);
		table_1.setModel(new DefaultTableModel(
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
				{new Integer(10).toHexString(10).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(11).toHexString(11).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(12).toHexString(12).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(13).toHexString(13).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(14).toHexString(14).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(15).toHexString(15).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(16).toHexString(16).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(17).toHexString(17).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(18).toHexString(18).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(19).toHexString(19).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(20).toHexString(20).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(21).toHexString(21).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(22).toHexString(22).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(23).toHexString(23).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(24).toHexString(24).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(25).toHexString(25).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(26).toHexString(26).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(27).toHexString(27).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(28).toHexString(28).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(29).toHexString(29).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(30).toHexString(30).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(31).toHexString(31).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(32).toHexString(32).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(33).toHexString(33).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(34).toHexString(34).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(35).toHexString(35).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(36).toHexString(36).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(37).toHexString(37).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(38).toHexString(38).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(39).toHexString(39).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(40).toHexString(40).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(41).toHexString(41).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(42).toHexString(42).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(43).toHexString(43).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(44).toHexString(44).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(45).toHexString(45).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(46).toHexString(46).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(47).toHexString(47).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(48).toHexString(48).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(49).toHexString(49).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(50).toHexString(50).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(51).toHexString(51).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(52).toHexString(52).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(53).toHexString(53).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(54).toHexString(54).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(55).toHexString(55).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(56).toHexString(56).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(57).toHexString(57).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(58).toHexString(58).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(59).toHexString(59).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(60).toHexString(60).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(61).toHexString(61).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(62).toHexString(62).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{new Integer(63).toHexString(63).toUpperCase(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				},
			new String[] {
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
			}
		));
		scrollPane.setViewportView(table_1);
		
		
		///Pildymas nuliais
		/*
		for(int row = 1; row < 65; row++){ 
    		for(int column = 1; column < 17; column++){
    			table_1.setValueAt("0000", row, column);
    		}
    	}
		*/
		JLabel lblkeltiIrVykdomi = new JLabel("\u012Ekeltos ir \r\nvykdomos");
		lblkeltiIrVykdomi.setForeground(new Color(0, 128, 128));
		lblkeltiIrVykdomi.setBounds(684, 121, 117, 42);
		frmMm.getContentPane().add(lblkeltiIrVykdomi);
		
		JLabel lblFailai = new JLabel("u\u017Eduotys:");
		lblFailai.setForeground(new Color(0, 128, 128));
		lblFailai.setBounds(684, 155, 136, 14);
		frmMm.getContentPane().add(lblFailai);
		
	    panel = new JPanel();
	    panel.setBounds(684, 188, 117, 230);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		frmMm.getContentPane().add(panel);
		
		textAX = new TextField();
		textAX.setBounds(383, 38, 88, 22);
		textAX.setText("0000");
		textAX.setEnabled(false);
		textAX.setEditable(false);
		textAX.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textAX);
		
		textBX = new TextField();
		textBX.setBounds(383, 66, 88, 22);
		textBX.setText("0000");
		textBX.setEnabled(false);
		textBX.setEditable(false);
		textBX.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textBX);
		
		Label label_11 = new Label("AX");
		label_11.setBounds(351, 38, 22, 22);
		frmMm.getContentPane().add(label_11);
		
		Label label_12 = new Label("BX");
		label_12.setBounds(351, 66, 22, 22);
		frmMm.getContentPane().add(label_12);
		
	    textDS = new TextField();
	    textDS.setBounds(505, 66, 88, 22);
	    textDS.setText("0000");
	    textDS.setEnabled(false);
	    textDS.setEditable(false);
	    textDS.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textDS);
		
		Label label_4 = new Label("DS");
		label_4.setBounds(477, 66, 22, 22);
		frmMm.getContentPane().add(label_4);
		
		textCS = new TextField();
		textCS.setBounds(505, 38, 88, 22);
		textCS.setText("0000");
		textCS.setEnabled(false);
		textCS.setEditable(false);
		textCS.setBackground(Color.LIGHT_GRAY);
		frmMm.getContentPane().add(textCS);
		
		Label label_13 = new Label("CS");
		label_13.setBounds(477, 38, 22, 22);
		frmMm.getContentPane().add(label_13);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setEnabled(false);
		scrollPane_1.setBounds(10, 455, 400, 212);
		frmMm.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.setEnabled(false);
		table.setBackground(new Color(255, 255, 153));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", ""},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Procesas", "Prioritetas", "B\u016Bsena"
			}
		));
		scrollPane_1.setViewportView(table);
		//RM.setProcessTable(0, "EIMANTAS", 100, "MIEGA");
		
		JLabel lblProcesSraas = new JLabel("Procesu sarasas");
		lblProcesSraas.setForeground(new Color(0, 128, 128));
		lblProcesSraas.setBounds(97, 429, 112, 14);
		frmMm.getContentPane().add(lblProcesSraas);
		
		JLabel lblProgramosvedimas = new JLabel("Programos \u012Fvedimas");
		lblProgramosvedimas.setForeground(new Color(0, 128, 128));
		lblProgramosvedimas.setBounds(477, 429, 197, 14);
		frmMm.getContentPane().add(lblProgramosvedimas);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(new Color(255, 255, 153));
		textPane.setBounds(477, 455, 324, 118);
		frmMm.getContentPane().add(textPane);
		
		
		
		
		btnPradti.addActionListener( new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		    	//Uzsetinam AI ir isaukiame pertraukima, kad pradetume vykdyti programa
		    	//System.out.println("Mygtukas pradeti");
		    	String uzduotiesPav = textPane.getText();
		    	OS.realMachine.setUzduotiesPav(uzduotiesPav);
		        OS.realMachine.setRegisterAI(1);
		    }
		});
		System.out.println(panel.getComponentCount());
		
		//frmMm.setBounds(100, 100, 827, 717);
		frmMm.setBounds(0, 0, 827, 717);
		frmMm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
/*	public static void printMemory(){
		String memory = "";
		int counter = 0;
		for(int row = 1; row < 65; row++){ 
			for(int column = 1; column < 17; column++){
				for(int i = 0; i < 4; i++){
					if(row >= 62){
						memory = "";
						memory += Machine.memory[(row - 1) * 16 * 4 + 0 + (column - 1) * 4];
						System.out.println("PUSLAPIAVIMO ADRESAS " + ((row - 1) * 16 * 4 + 0 + (column - 1) * 4));
					}else{
						memory += (char) Machine.memory[(row - 1) * 16 * 4 + i + (column - 1) * 4];
						System.out.println("ADRESAS " + ((row - 1) * 16 * 4 + i + (column - 1) * 4));
					}
				}
				table_1.setValueAt(memory, row, column);
				memory = "";
			}
		}
	}*/
	public static void printRegisters(CPU cpu){
		/*
		textAX.setText(Integer.toHexString(Machine.unsignedToBytes(cpu.getAX()[0])).toUpperCase() + "|" +
				Integer.toHexString(Machine.unsignedToBytes(cpu.getAX()[1])).toUpperCase() + "|" +
				Integer.toHexString(Machine.unsignedToBytes(cpu.getAX()[2])).toUpperCase() + "|" +
				Integer.toHexString(Machine.unsignedToBytes(cpu.getAX()[3])).toUpperCase());
			
			textBX.setText(Integer.toHexString(Machine.unsignedToBytes(cpu.getBX()[0])).toUpperCase() + "|" +
					Integer.toHexString(Machine.unsignedToBytes(cpu.getBX()[1])).toUpperCase() + "|" +
					Integer.toHexString(Machine.unsignedToBytes(cpu.getBX()[2])).toUpperCase() + "|" +
					Integer.toHexString(Machine.unsignedToBytes(cpu.getBX()[3])).toUpperCase());
			
			textPLR.setText(Integer.toHexString(Machine.unsignedToBytes(cpu.getPLR()[0])).toUpperCase() + 
					Integer.toHexString(Machine.unsignedToBytes(cpu.getPLR()[1])).toUpperCase() + 
					Integer.toHexString(cpu.getPLR()[2]*10 + cpu.getPLR()[3]).toUpperCase());
			textIC.setText((Integer.toHexString(cpu.getIC()[0]).toUpperCase()) + "|" + 
		        	Integer.toHexString(cpu.getIC()[1]).toUpperCase());
			
			if(cpu.getC() == 0){
				textC.setText("FALSE");
			}else{
				textC.setText("TRUE");
			}
			textPI.setText(Integer.toString(cpu.getPI()));
			textSI.setText(Integer.toString(cpu.getSI()));
			textTI.setText(Integer.toString(cpu.getTI()));
			
		    //skaiciuojame realioje atmintyje kodo ir duomenu segmentu adresus
			byte CS[] = cpu.getCS();
			byte DS[] = cpu.getDS();
			int segment = Machine.realAddress(CS[0], CS[1]) / Machine.BLOCK_SIZE / Machine.WORD_SIZE;
			textCS.setText(Integer.toHexString(segment).toUpperCase() + "|0");
			segment = Machine.realAddress(DS[0], CS[1]) / Machine.BLOCK_SIZE / Machine.WORD_SIZE ;
	textDS.setText(Integer.toHexString(segment).toUpperCase() + "|0");
	*/
	}
	public static void setProcessTable(int row, String procName, int procPrior, String procState){
		table.setValueAt(procName, row, 0);
		table.setValueAt(procPrior, row, 1);
		table.setValueAt(procState, row, 2);
	}
	public static void userMode() throws InterruptedException{
		RM.supervisorButton.setSelected(false);
		RM.userButton.setSelected(true);
	}
	public static void supervisorMode() throws InterruptedException{
		RM.supervisorButton.setSelected(true);
		RM.userButton.setSelected(false);
	}
	
	public static void currentProgram(String programName){
		fontButtons[programsNum] = new JRadioButton(programName);
		fontGroup.add(fontButtons[programsNum]);
	    panel.add(fontButtons[programsNum]);
	   // fontButtons[programsNum].setSelected(true);
    	frmMm.validate();
	    frmMm.getContentPane().repaint();  
		RM.fontButtons[programsNum].setSelected(true);
		programsNum++;
	}
}