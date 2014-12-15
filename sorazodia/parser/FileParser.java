package sorazodia.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that scans the file for tokens and input them into an ArrayList 
 * @author SoraZodia
 */
public class FileParser {
	private static BufferedReader parser;
	private static ArrayList<String> dummyList = new ArrayList<>();
	private static ArrayList<String> list = new ArrayList<>();
	
	/**
	 * Scans the file and add it to an ArrayList
	 * @param Name of the file to use
	 * @throws IOException
	 */
	public static void read(String fileName) throws IOException{
		parser = new BufferedReader(new FileReader(fileName));
		String line;
		
		while((line = parser.readLine()) != null){
			addToList(line);
		}
		
		splitString();
		parser.close();
	}
	
	/**
	 * Removes any extra white spaces found in the string
	 * and add it to {@link #dummyList} for later use in {@link #splitString()}
	 * @param The original string
	 */
	public static void addToList(String str){
		String s = str.trim().replaceAll("\\s+", " ");
		dummyList.add(s);
	}
	
	/**
	 * In case there's more than one token found in an single line,
	 * splitString() will store them as separate entries in {@link #list}
	 */
	private static void splitString(){
		for(String s: dummyList){
			String[] split = s.split("\\s");
			for(String x: split){
				list.add(x);
			}
		}
	}
	
	/**
	 * Allows other classes to use {@link #list}. Intended for
	 * {@link sorazodia.randomizer.Randomizer Randomizer}
	 * @return {@link #list}
	 */
	public static ArrayList<String> getList(){
		return list;
	}

}
