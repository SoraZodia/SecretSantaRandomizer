package sorazodia.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sorazodia.gui.TextDisplay;

/**
 * Main control line, I like to call it "SYSTEM" (Imaginary cookie to you if you get the reference)
 * @author SoraZodia
 */
public class ListSystemContorl {

	/**
	 * Unifies the classes together to randomize and output the entries found in input.txt
	 * @param args
	 */
	public static void main(String args[]){
		System.out.println("[ListSystemContorl] Drawing Gui");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		TextDisplay.drawGUI();
		System.out.println("[ListSystemContorl] Finished");
	}

}
