
public class Main {
	public static void main(String[] args) {
		System.out.println("Hi :)");
		
		
		
		// Basic tests for search. @TEMP: Maybe replace with JUnit tests later...
		BusStopSearch search = new BusStopSearch("inputs/stops.txt");
		String stringToFind = "WB WESTMINSTER HWY AT 11000 BLOCK";
		boolean wasFound = search.isInDataBase(stringToFind);
		System.out.println(wasFound);
	}

}
