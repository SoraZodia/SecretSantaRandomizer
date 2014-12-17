package sorazodia.main;

import java.io.IOException;

import sorazodia.gui.TextBox;
import sorazodia.parser.FileParser;
import sorazodia.random.Randomizer;

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
			try {
				System.out.println("[ListSystemContorl] Drawing Gui");
				FileParser.read("input.txt");
				Randomizer.initRandomizer();
				TextBox.drawGUI();
			}catch(IOException e){
				System.out.println("[ListSystemContorl] Something Went Wrong, Check Your File Name.");
				System.out.println("[ListSystemContorl] Make Sure It's Named input.txt");
				System.out.println("[ListSystemContorl] If That's Not The Case, Check The Crash Report");
				e.printStackTrace();
			}
		System.out.println("[ListSystemContorl] Finished");
	}

}
