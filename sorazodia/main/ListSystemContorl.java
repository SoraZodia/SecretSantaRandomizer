package sorazodia.main;

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
		TextDisplay.drawGUI();
		System.out.println("[ListSystemContorl] Finished");
	}

}
