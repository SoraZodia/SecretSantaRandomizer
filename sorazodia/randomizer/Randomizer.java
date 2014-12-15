package sorazodia.randomizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import sorazodia.parser.FileParser;

/**
 * Takes, randomize, and output an list of Strings
 * @author SoraZodia
 */
public class Randomizer {
	
	private static Random rand = new Random();
	private static ArrayList<String> list = new ArrayList<>();
	
	/**
	 * Randomize the list from {@link sorazodia.parser.FileParser#getList() FileParser's getList()}
	 * and output it as an file.
	 * @throws IOException
	 */
	public static void listRand() throws IOException{
		list = FileParser.getList();
		BufferedWriter writer = new BufferedWriter(new FileWriter("list.txt"));
		int space = 0;
		int draws;
		while(!list.isEmpty()){
			draws = rand.nextInt(list.size());
			writer.write(list.get(draws));
			writer.newLine();
			space++;
			if(space%2 == 0) writer.newLine();
			list.remove(draws);
		}
		writer.close();
	}
	
}
