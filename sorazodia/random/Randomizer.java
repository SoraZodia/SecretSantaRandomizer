package sorazodia.random;

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
	public static String listRand(String name){
		String text = "";
			int draws = 0;
			if(!list.isEmpty()){
				for(int x = 0; x<1; x++){
					draws = rand.nextInt(list.size());
					text = list.get(draws);
					if(text.equalsIgnoreCase(name)){
						if(list.size() == 1)text = "There's no more people to pick";
						else x--;
					}
				}
				list.remove(draws);
			}
			return text;
	}
	
	public static void initRandomizer(){
		for(String str: FileParser.getList())
		list.add(str);
	}
	
	public static boolean isListEmpty(){
		return list.isEmpty();
	}
	
}
