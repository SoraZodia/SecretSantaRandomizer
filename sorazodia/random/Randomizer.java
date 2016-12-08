package sorazodia.random;

import java.util.ArrayList;
import java.util.Random;

import sorazodia.parser.FileIO;

/**
 * Takes, randomize, and output an list of Strings
 * @author SoraZodia
 */
public class Randomizer {
	
	private static Random rand = new Random();
	private static ArrayList<String> list = new ArrayList<>();
	
	/**
	 * Randomize {@link #list} and output the result.
	 */
	public static String getReciever(String name){
		String text = "";
		int draws = 0;
		if(list.isEmpty()) text = "There's no more people to pick";
		if(!list.isEmpty()){
			for(int x = 0; x<1; x++){
				draws = rand.nextInt(list.size());
				text = list.get(draws);
				if(text.equalsIgnoreCase(name) && list.size() != 1) x--;
			}
			list.remove(draws);
		}
		return text;
	}

	/**
	 * Start up Randomizer
	 */
	public static void initRandomizer(){
		for(String str: FileIO.getList())
			if(!list.contains(str))list.add(str);
	}
	
	/**
	 * Nuke and remove all entries in {@link #list}
	 */
	public static void clear(){
		list.clear();
	}
	
	/**
	 * Checks if {@link #list} has any more elements in it
	 */
	public static boolean isListEmpty(){
		return list.isEmpty();
	}
	
}
