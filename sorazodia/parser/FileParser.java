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
	 * @return A String of all of the entries in {@link #list}
	 */
	public static String read(String fileName) throws IOException{
		parser = new BufferedReader(new FileReader(fileName));
		String line;
		
		while((line = parser.readLine()) != null){
			addToList(line);
		}
		
		StringBuilder fusion = new StringBuilder();
		for(String str: list){
			fusion.append(str + "\n");
		}
		
		parser.close();
		return fusion.toString();
	}
	
	/**
	 * Removes any extra white spaces found in the string
	 * and add it to {@link #dummyList} for later use in {@link #parseString()}
	 * @param The original string
	 */
	public static void addToList(String str){
		dummyList.clear();
		String s = str.trim().replaceAll("\\s+", " ");
		if(!s.matches("\\s+") && !s.isEmpty() && !dummyList.contains(s)) dummyList.add(s);
		parseString();
	}
	
	/**
	 * Nuke and vaporize all entries in {@link #list}
	 */
	public static void clearList(){
		list.clear();
	}
	
	/**
	 * Separate the text in {@link #dummyList} into separate tokens and store them into
	 * {@link #list} 
	 */
	private static void parseString(){
		for(String s: dummyList){
			String[] split = s.trim().split("\\s");
			for(String x: split){
				list.add(x);
			}
		}
	}
	
	/**
	 *Allows other classes to use {@link #list}
	 */
	public static ArrayList<String> getList(){
		return list;
	}

}
