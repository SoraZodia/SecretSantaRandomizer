package sorazodia.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sorazodia.random.Randomizer;

/**
 * Displays a text box which the user can inputs their name
 * and see who they will be giving a gift to
 * @author SoraZodia
 */
@SuppressWarnings("serial")
public class TextBox extends JPanel implements ActionListener{
	
	private JTextField inputLine = new JTextField(10);
	private JLabel input = new JLabel();
	private JLabel output = new JLabel();
	private JButton clearButton = new JButton("Clear");
	private JButton redoButton = new JButton("Redo?");
	
	/**
	 * Adds in all the parts needed for the GUI
	 */
	public TextBox(){		
		this.setLayout(new FlowLayout());
		inputLine.addActionListener(this);
		
		clearButton.setActionCommand("clear");
		clearButton.setVisible(false);
		clearButton.addActionListener(this);
		
		redoButton.setActionCommand("redo");
		redoButton.setVisible(false);
		redoButton.addActionListener(this);
		
		input.setText("Enter Name:");
		
		this.add(input);
		this.add(inputLine);
		this.add(output);
		this.add(clearButton);
		this.add(redoButton);
		this.setPreferredSize(new Dimension(500, 50));
	}
	
	/**
	 * Checks for user input
	 */
	public void actionPerformed(ActionEvent event){
		String inputText = inputLine.getText();
		String outputText;
		
		inputLine.selectAll();
		if(inputText.equals(""))outputText = "Please Enter An Name";
		else outputText = Randomizer.listRand(inputText);
        output.setText(outputText);
		
		if(!clearButton.isVisible() && !Randomizer.isListEmpty())clearButton.setVisible(true);
		if(Randomizer.isListEmpty()){
			clearButton.setVisible(false);
			redoButton.setVisible(true);
			output.setText("There's no more people to pick");
		}
		if(event.getActionCommand().equals("clear"))output.setText("");
		if(event.getActionCommand().equals("redo")){
			output.setText("");
			Randomizer.initRandomizer();
			redoButton.setVisible(false);
		}
	}

	/**
	 * Starts up the GUI
	 */
	public static void drawGUI(){
		JFrame frame = new JFrame("Santa List Randomizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TextBox());
        
        frame.pack();
        frame.setVisible(true);	
	}
	
}
