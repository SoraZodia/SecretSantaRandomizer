package sorazodia.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sorazodia.parser.FileIO;
import sorazodia.random.Randomizer;

/**
 * Displays a text box which the user can inputs their name
 * and see who they will be giving a gift to
 * @author SoraZodia
 */
@SuppressWarnings("serial")
public class TextDisplay extends JPanel implements ActionListener, DocumentListener{
	
	private JTextField inputLine;
	private JTextArea list;
	private JLabel inputName;
	private JLabel inputList;
	private JLabel output;
	private JButton clearButton;
	private JButton redoButton;
	private JButton reloadButton;
	private JButton loadButton;
	private JButton printButton;
	private JScrollPane scroll;
	private GridBagConstraints bag = new GridBagConstraints();
	private JFileChooser fc = new JFileChooser();
	
	private boolean listChanged = false;
	
	/**
	 * Adds in all the parts needed for the GUI
	 */
	public TextDisplay(){		
		this.setLayout(new GridBagLayout());
		
		this.drawTextArea();
		this.drawPanel();
		this.drawTextField();		
        this.drawButtonArea();

		this.setPreferredSize(new Dimension(600, 550));
	}
	
	private void drawPanel(){
		inputName = new JLabel();
		inputName.setText("Giver's Name");	
		bag.gridx = 2;
		bag.gridy = 0;
		bag.gridheight = 1;
		this.add(inputName, bag);
		
		inputList = new JLabel();
		inputList.setText("Recipients List");	
		bag.gridx = 1;
		bag.gridheight = 1;
		this.add(inputList, bag);
		
		output = new JLabel();
		bag.gridy = 3;
		bag.gridx = 2;
		bag.gridheight = 2;
		this.add(output, bag);
	}
	
	private void drawTextField(){
		inputLine = new JTextField(10);
		bag.gridx = 2;
		bag.gridy = 2;
		bag.gridheight = 1;
		inputLine.setActionCommand("enter");
		inputLine.addActionListener(this);		
		this.add(inputLine, bag);
	}
	
	private void drawTextArea(){
		list = new JTextArea(25, 20);
		scroll = new JScrollPane(list);
		bag.gridx = 1;
		bag.gridy = 1;
		bag.gridheight = 25;
		list.setLineWrap(true);
		list.getDocument().addDocumentListener(this);
		this.add(scroll, bag);
	}
	
	private void drawButtonArea(){
	
		clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.setVisible(false);
		clearButton.addActionListener(this);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.gridy = 6;
		bag.gridheight = 1;
		this.add(clearButton, bag);

		redoButton = new JButton("Redo?");
		redoButton.setActionCommand("redo");
		redoButton.setVisible(false);
		redoButton.addActionListener(this);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.gridy = 6;
		bag.gridheight = 1;
		this.add(redoButton, bag);
		
		reloadButton = new JButton("Refresh List");
		reloadButton.setActionCommand("refresh");
		reloadButton.addActionListener(this);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.gridx = 2;
		bag.gridy = 8;
		bag.gridheight = 1;
		this.add(reloadButton, bag);
		
		loadButton = new JButton("Load File");
		loadButton.setActionCommand("load");
		loadButton.addActionListener(this);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.gridx = 2;
		bag.gridy = 7;
		bag.gridheight = 1;
		this.add(loadButton, bag);
		
		printButton = new JButton("Print Result");
		printButton.setActionCommand("print");
		printButton.addActionListener(this);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.gridx = 2;
		bag.gridy = 9;
		bag.gridheight = 1;
		this.add(printButton, bag);
	}
	
	/**
	 * Checks for user input
	 */
	@Override
	public void actionPerformed(ActionEvent event){

		int userVal = 0;
		
		switch(event.getActionCommand()){
		
		case "enter":
			
			if(listChanged){
				FileIO.clearList(); // Did this so that the text box changes will also be reflected into the actual ArrayList
				FileIO.addToList(list.getText());
				Randomizer.clear();
				Randomizer.initRandomizer();
    			listChanged = false;
			}
			
			String inputText = inputLine.getText();
			String outputText;

			inputLine.selectAll();
			
			if(Randomizer.isListEmpty()){
				clearButton.setVisible(false);
				redoButton.setVisible(true);
			}

			if(!Randomizer.isListEmpty() && (inputText.matches("\\s+") || inputText.isEmpty())) outputText = "Please Enter An Name";
			else outputText = Randomizer.getReciever(inputText).split("[|]")[0];
			
			output.setText(outputText);
			
			if(!clearButton.isVisible() && !Randomizer.isListEmpty()) clearButton.setVisible(true);
			break;
			
		case "load":
			
			if(redoButton.isVisible()) redoButton.setVisible(false);
			
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			userVal = fc.showDialog(this, "Load File");
			
			try{
				if (userVal == JFileChooser.APPROVE_OPTION)
					list.setText(FileIO.read(fc.getSelectedFile().getAbsolutePath()) + list.getText());
			}
			catch(IOException io){
				output.setText("Unable to read file");
				io.printStackTrace();
			}
			break;
			
		case "clear":
			output.setText("");
			break;
			
		case "refresh":
			randomize();
			break;
			
		case "redo":
			output.setText("");
			Randomizer.initRandomizer();
			redoButton.setVisible(false);
			break;
			
		case "print":
			output.setText("Sending messages...");
			randomize();
			
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			userVal = fc.showDialog(this, "Save to Folder");
			
			if (userVal == JFileChooser.APPROVE_OPTION)
			{
				File file = new File(fc.getSelectedFile().getAbsolutePath() + "\\list.txt");
				int success = FileIO.write(file);
				
				if (success == 1)
				{
					output.setText("Success!");
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
					output.setText("Error with the folder choosen");
				
			}
			
			break;
			
		}
		
	}
	
	private void randomize() {
		FileIO.clearList();
		FileIO.addToList(list.getText());
		Randomizer.clear();
		Randomizer.initRandomizer();
	}
	
	/**
	 * Starts up the GUI
	 */
	public static void drawGUI(){
		JFrame frame = new JFrame("Secret Santa Randomizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new TextDisplay());
        
        frame.pack();
        frame.setVisible(true);	
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		listChanged = true;
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		listChanged = true;
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		listChanged = true;
	}
	
}
