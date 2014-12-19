package sorazodia.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sorazodia.parser.FileParser;
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
	private JScrollPane scroll;
	private GridBagConstraints bag = new GridBagConstraints();
	
	private boolean listChanged = false;
	
	/**
	 * Adds in all the parts needed for the GUI
	 */
	public TextDisplay(){		
		this.setLayout(new GridBagLayout());
		
		this.drawTextArea();
		this.drawPanel();
		this.drawTextField();		
        this.drawButton();

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
	
	private void drawButton(){
	
		clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.setVisible(false);
		clearButton.addActionListener(this);
		bag.gridy = 5;
		bag.gridheight = 2;
		this.add(clearButton, bag);

		redoButton = new JButton("Redo?");
		redoButton.setActionCommand("redo");
		redoButton.setVisible(false);
		redoButton.addActionListener(this);
		bag.gridy = 5;
		bag.gridheight = 2;
		this.add(redoButton, bag);
		
		reloadButton = new JButton("Refresh List");
		reloadButton.setActionCommand("refresh");
		reloadButton.addActionListener(this);
		bag.gridx = 0;
		bag.gridy = 5;
		bag.gridheight = 1;
		this.add(reloadButton, bag);
		
		loadButton = new JButton("Load names.txt");
		loadButton.setActionCommand("load");
		loadButton.addActionListener(this);
		bag.gridx = 0;
		bag.gridy = 4;
		bag.gridheight = 1;
		this.add(loadButton, bag);
	}
	
	/**
	 * Checks for user input
	 */
	@Override
	public void actionPerformed(ActionEvent event){

		switch(event.getActionCommand()){
		
		case "enter":
			
			if(listChanged){
				FileParser.clearList(); // Did this so that the text box changes will also be reflected into the actual ArrayList
				FileParser.addToList(list.getText());
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
			else outputText = Randomizer.listRand(inputText);
			
			output.setText(outputText);
			
			if(!clearButton.isVisible() && !Randomizer.isListEmpty()) clearButton.setVisible(true);
			break;
			
		case "load":
			
			if(redoButton.isVisible()) redoButton.setVisible(false);
			
			try{
				list.setText(FileParser.read("names.txt") + list.getText());
			}
			catch(IOException io){
				output.setText("Unable to find or read names.txt");
				io.printStackTrace();
			}
			break;
			
		case "clear":
			output.setText("");
			break;
			
		case "refresh":
			FileParser.clearList();
			FileParser.addToList(list.getText());
			Randomizer.clear();
			Randomizer.initRandomizer();
			break;
			
		case "redo":
			output.setText("");
			Randomizer.initRandomizer();
			redoButton.setVisible(false);
			break;
			
		}
		
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
