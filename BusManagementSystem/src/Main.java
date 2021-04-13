import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hi :)");
		
		
		
		// Basic tests for search. @TEMP: Maybe replace with JUnit tests later...
		BusStopSearch search = new BusStopSearch("inputs/stops.txt");
		String stringToFind = "WESTMINSTER HWY AT 11000 BLOCK WB";
		String prefixSearchWord = "WESTM";
		System.out.println("List of words with prefix: \"" + prefixSearchWord + "\":");
		ArrayList<String> finds = search.findStops(prefixSearchWord);
		for (String s : finds) {
			System.out.println(s);
		}
		boolean wasFound = search.isInDataBase(stringToFind);
		System.out.println("Was \"" + stringToFind + "\" found in the tree: " + wasFound);
	}

}
