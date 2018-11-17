package sorazodia.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sorazodia.gui.TextDisplay;

/**
 * Main control line, I like to call it "SYSTEM" (Imaginary cookie to you if you get the reference)
 * Man, it was funny at the time ^
 * @author SoraZodia
 */
public class ListSystemContorl {
	private static String email;
	private static String pass;

	/**
	 * Unifies the classes together to randomize and output the entries found in input.txt
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("info"));
		email = reader.readLine();
		pass = reader.readLine();
		reader.close();
		System.out.println("[ListSystemContorl] Drawing Gui");
		TextDisplay.drawGUI();
		System.out.println("[ListSystemContorl] Finished");
	}
	
	public static String getEmail() {
		return email;
	}
	
	public static String getPass() {
		return pass;
	}

}
