package sorazodia.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sorazodia.random.Randomizer;

/**
 * Class that scans the file for tokens and input them into an ArrayList
 * 
 * @author SoraZodia
 */
public class FileIO {
	private static BufferedReader parser;
	private static ArrayList<String> dummyList = new ArrayList<>();
	private static ArrayList<String> list = new ArrayList<>();
	private static BufferedWriter writer;

	public static int write(File file)
	{
		int val = 1;
		StringBuilder santaList = new StringBuilder();
		
		Randomizer.initRandomizer();
		
		for (String giver: list) {
			String senderInfo[] = giver.split("[|]");
			String recieverInfo[] = Randomizer.getReciever(giver).split("[|]");
			String carrier = senderInfo.length > 2 ? senderInfo[2] : "tmobile";
			System.out.println(carrier);
			if (senderInfo.length > 1) {
				Randomizer.sendEmail(senderInfo[1], recieverInfo[0], carrier);
			}
			else {
				santaList.append(senderInfo[0] + " > " + recieverInfo[0] + System.lineSeparator());
			}
			
		}
		
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(santaList.toString());
			writer.close();
		}
		catch (IOException io)
		{
			io.printStackTrace();
			val = 0;
		}
		
		return val;
	}

	/**
	 * Scans the file and add it to an ArrayList
	 * 
	 * @param Name
	 *            of the file to use
	 * @throws IOException
	 * @return A String of all of the entries in {@link #list}
	 */
	public static String read(String fileName) throws IOException {
		parser = new BufferedReader(new FileReader(fileName));
		String line;

		while ((line = parser.readLine()) != null) {
			addToList(line);
		}

		StringBuilder fusion = new StringBuilder();
		for (String str : list) {
			fusion.append(str + "\n");
		}

		parser.close();
		return fusion.toString();
	}

	/**
	 * Removes any extra white spaces found in the string and add it to
	 * {@link #dummyList} for later use in {@link #parseString()}
	 * 
	 * @param The
	 *            original string
	 */
	public static void addToList(String str) {
		dummyList.clear();
		String s = str.trim().replaceAll("\\s+", " ");
		if (!s.matches("\\s+") && !s.isEmpty() && !dummyList.contains(s))
			dummyList.add(s);
		parseString();
	}

	/**
	 * Nuke and vaporize all entries in {@link #list}
	 */
	public static void clearList() {
		list.clear();
	}

	/**
	 * Separate the text in {@link #dummyList} into separate tokens and store
	 * them into {@link #list}
	 */
	private static void parseString() {
		for (String s : dummyList) {
			String[] split = s.trim().split("\\s");
			for (String x : split) {
				list.add(x);
			}
		}
	}

	/**
	 * Allows other classes to use {@link #list}
	 */
	public static ArrayList<String> getList() {
		return list;
	}

}
