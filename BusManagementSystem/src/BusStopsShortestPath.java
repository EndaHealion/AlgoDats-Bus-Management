import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BusStopsShortestPath {
	
	// trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
	public static final int ST_TRIP_ID_INDEX 				= 0;
	public static final int ST_ARRIVAL_TIME_INDEX	 		= 1;
	public static final int ST_DEPARTURE_TIME_INDEX 		= 2;
	public static final int ST_STOP_ID_INDEX 				= 3;
	public static final int ST_STOP_SEQUENCE_INDEX 			= 4;
	public static final int ST_STOP_HEADSIGN_INDEX 			= 5;
	public static final int ST_PICKUP_TYPE_INDEX 			= 6;
	public static final int ST_DROP_OFF_TYPE_INDEX 			= 7;
	public static final int ST_SHAPE_DIST_TRAVELED_INDEX 	= 8;
	
	// from_stop_id,to_stop_id,transfer_type,min_transfer_time
	public static final int T_FROM_STOP_ID_INDEX 		= 0;
	public static final int T_TO_STOP_ID_INDEX 			= 1;
	public static final int T_TRANSFER_TYPE_INDEX 		= 2;
	public static final int T_MIN_TRANSFER_TIME_INDEX 	= 3;
	
	
	public static ArrayList<Stops.Stop> q = new ArrayList<Stops.Stop>();

	public static void buildGraph() throws IOException {
		// TODO: add additional copies of a stop for each trip that visits it.
		// https://www.javaer101.com/en/article/100599942.html

		BufferedReader stopTimes = new BufferedReader(new FileReader("inputs/stop_times.txt"));
		stopTimes.readLine(); // move past headers
		
		
		// assuming there is at least one journey with two stops
		int tripId;
		int prevTripId = -1;
		Stops.Stop prevStop = null;
		Stops.Stop currentStop = null;
		String line = stopTimes.readLine();
		do {
			
			String[] splitLine = line.split(",");
			tripId = Integer.parseInt(splitLine[ST_TRIP_ID_INDEX]);
			double cost = 1;
			
			String currentStopId = splitLine[ST_STOP_ID_INDEX];
			currentStop = Stops.findStopById(currentStopId).clone();
			
			// if we're staying on the same bus to the next stop, create an edge between them
			if (prevTripId == tripId) {
				Edge edge = new Edge(tripId, prevStop, currentStop, cost);
				prevStop.edges.add(edge);
			}
			
			q.add(currentStop);
			
			prevStop = currentStop;
			prevTripId = tripId;
			line = stopTimes.readLine();
		} while (line != null);
		
		// TODO: for every line in the transfers file, find the two stop objects, then create an edge between them
	}

	// returns the path of the shortest route between two stops
	public static Edge[] shortestPath(Stops.Stop source, Stops.Stop destination) {
		
		
		HashMap<Stops.Stop, Double> dist = new HashMap<Stops.Stop, Double>();
		HashMap<Stops.Stop, Edge> prev = new HashMap<Stops.Stop, Edge>();
		
		for (Stops.Stop v : Stops.stops) {
			dist.put(v, Double.POSITIVE_INFINITY);
			prev.put(v, null);
		}
		dist.put(source, 0.0);
		
		while (q.size() != 0) {
			// find the vertex with the minimum cost in dist
			Stops.Stop u = q.get(0);
			for (Stops.Stop s : q) {
				if (dist.get(s) < dist.get(u)) {
					u = s;
				}
			}
			q.remove(u);
			System.out.println("got here!");
			for (Edge e : u.edges) {
				if (q.contains(e.currentStop)) {
					Stops.Stop v = e.nextStop;
					double alt = dist.get(u) + e.cost;
					if (alt < dist.get(v)) {
						dist.put(v, alt);
						prev.put(v, e);
					}
				}
			}
		}
		
		// walk through the shortest path from destination to source to build up the solution as a list
		ArrayList<Edge> path = new ArrayList<Edge>();
		Edge e = prev.get(destination);
		while (e != null) { // (!e.currentStop.equals(source)) {
			System.out.println(e);
			System.out.println(e.currentStop);
			path.add(e);
			e = prev.get(e.currentStop);
		}
		path.add(e); // add the first edge (ie. the one connected to the source)
		
		// the solution list is now in reverse order, so reverse it before returning as an array
		Collections.sort(path, Collections.reverseOrder());
		return path.toArray(new Edge[path.size()]);
	}

	public static double sumCost(Edge[] path) {
		double totalCost = 0.0;
		for (Edge e : path) {
			totalCost += e.cost;
		}
		return totalCost;
	}
}