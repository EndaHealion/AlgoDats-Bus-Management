package algorithms;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args) {
		// Basic tests for search. @TEMP: Maybe replace with JUnit tests later...
		//busStopSearchTest();
		//arrivalTimeSearchTest();
		busStopsShortestPathTest();
		
		// NOTE(Enda): new Stops(fileLocation) must be called before new BusStopSearch(Stops)
	}
	
	private static void busStopSearchTest() {
		new Stops("inputs/stops.txt");
		new BusStopSearch(Stops.stops);
		
		String stringToFind = "WESTMINSTER HWY AT 11000 BLOCK WB";
		String prefixSearchWord = "WESTMI";
		
		System.out.println("List of stops with prefix: \"" + prefixSearchWord + "\":");
		ArrayList<Stops.Stop> finds = BusStopSearch.findStops(prefixSearchWord);
		int numFinds = finds.size();
		for (int i=0; i<numFinds; i++) {
			System.out.println(finds.get(i).toString());
		}
		
		boolean wasFound = BusStopSearch.isInDataBase(stringToFind);
		System.out.println("is \"" + stringToFind + "\" in the tree: " + wasFound);
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
	
	private static void busStopsShortestPathTest() {
		new Stops("inputs/stops.txt");
		try {
			BusStopsShortestPath.buildGraph("inputs/stop_times.txt", "inputs/transfers.txt");
		} catch (IOException e) {
			System.err.println("Could not find files specified. Exiting");
			return;
		}
		Stops.Stop source = Stops.findStopById("1270");
		Stops.Stop destination = Stops.findStopById("5474");
		
		Edge[] path = BusStopsShortestPath.shortestPath(source, destination);
		// if the path is of length 0, there isn't a solution
		
		for (Edge e : path) {
			System.out.print(e.currentStop.stopId + "->");
		}
		System.out.print(path[path.length - 1].nextStop.stopId);	
		System.out.println(); // lazy newline
		
		System.out.println("Cost of journey: " + BusStopsShortestPath.sumCost(path));
		
	}
}