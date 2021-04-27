import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		// Basic tests for search. @TEMP: Maybe replace with JUnit tests later...
		//busStopSearchTest();
		arrivalTimeSearchTest();
		busStopSearchTest();
	}
	
	private static void busStopSearchTest() {
		new BusStopSearch("inputs/stops.txt");
		
		String stringToFind = "WESTMINSTER HWY AT 11000 BLOCK WB";
		String prefixSearchWord = "WESTM";
		
		System.out.println("List of words with prefix: \"" + prefixSearchWord + "\":");
		ArrayList<String> finds = BusStopSearch.findStops(prefixSearchWord);
		for (String s : finds) {
			System.out.println(s);
		}
		
		boolean wasFound = BusStopSearch.isInDataBase(stringToFind);
		System.out.println("Was \"" + stringToFind + "\" found in the tree: " + wasFound);
	}
	
	private static void arrivalTimeSearchTest() {
		new ArrivalTimeSearch("inputs/stop_times.txt");
		
		//Enter time HH, MM, SS
		int[] arrivalTime = new int[]{5, 25, 0};
		ArrayList<ArrivalTimeSearch.tripDetails> tripsAtArrivalTime = ArrivalTimeSearch.findTripsAtArrivalTime(arrivalTime);
		
		System.out.println("Sample Outputs");
		for (int i = 0; i <= tripsAtArrivalTime.size() - 1; i++) {
			ArrivalTimeSearch.tripDetails details = tripsAtArrivalTime.get(i);
			String output = i + ": ";
			output += ArrivalTimeSearch.getTripID(details) + ",";
			output += ArrivalTimeSearch.getTripArrivalTime(details) + ",";
			output += ArrivalTimeSearch.getTripDepartureTime(details) + ",";
			output += ArrivalTimeSearch.getStopID(details) + ",";
			output += ArrivalTimeSearch.getStopSequence(details) + ",";
			output += ArrivalTimeSearch.getStopHeadsign(details) + ",";
			output += ArrivalTimeSearch.getPickupType(details) + ",";
			output += ArrivalTimeSearch.getDropOffType(details) + ",";
			output += ArrivalTimeSearch.getShapeDistTravelled(details);
			System.out.println(output);
		}
	}
}