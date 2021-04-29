import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	
	public static ArrayList<Stops.Stop> q = Stops.stops;

	public static void buildGraph(String stopTimesLocation, String transfersLocation) throws IOException {
		// add additional copies of a stop for each trip that visits it.
		// for an explanation, see: https://www.javaer101.com/en/article/100599942.html

		BufferedReader stopTimes = new BufferedReader(new FileReader(stopTimesLocation));
		stopTimes.readLine(); // move past headers
		
		String line = stopTimes.readLine();
		String[] splitLine = line.split(",");

		
		String currentStopId = splitLine[ST_STOP_ID_INDEX];
		
		Stops.Stop prevStop = null;
		Stops.Stop currentStop = Stops.findStopById(currentStopId);
		int prevTripId = -1;
		int currentTripId = Integer.parseInt(splitLine[ST_TRIP_ID_INDEX]);
		
		double cost = 1.0; // staying on the same bus, so the 'cost' is always 1
		
		
		while ((line = stopTimes.readLine()) != null) {
			prevStop = currentStop;
			prevTripId = currentTripId;
			
			splitLine = line.split(",");
			
			currentTripId = Integer.parseInt(splitLine[ST_TRIP_ID_INDEX]);
			currentStopId = splitLine[ST_STOP_ID_INDEX];
			currentStop = Stops.findStopById(currentStopId);
			
			// if we're staying on the same bus to the next stop, create an edge between them
			if (prevTripId == currentTripId) {
				Edge edge = new Edge(currentTripId, prevStop, currentStop, cost);
				prevStop.edges.add(edge);
			}
		}
		stopTimes.close();
		
		
		// for every line in the transfers file, find the two stop objects, then create an edge between them
		BufferedReader transfers = new BufferedReader(new FileReader(transfersLocation));
		transfers.readLine(); // move past headers
		
		while ((line = transfers.readLine()) != null) { 
			splitLine = line.split(",");
			int fromId = Integer.parseInt(splitLine[T_FROM_STOP_ID_INDEX]);
			int toId = Integer.parseInt(splitLine[T_TO_STOP_ID_INDEX]);
			int transferType = Integer.parseInt(splitLine[T_TRANSFER_TYPE_INDEX]);
			
			Stops.Stop from = Stops.findStopById(fromId);
			Stops.Stop to = Stops.findStopById(toId);
			
			if (transferType == 0) {
				cost = 2.0;
			} else if (transferType == 2) {
				cost = Double.parseDouble(splitLine[T_MIN_TRANSFER_TIME_INDEX]) / 100;
			}
			
			from.edges.add(new Edge(0, from, to, cost));
		}
		
		transfers.close();
	}

	// returns the path of the shortest route between two stops
	public static Edge[] shortestPath(Stops.Stop source, Stops.Stop destination) {
		
		
		HashMap<Stops.Stop, Double> dist = new HashMap<Stops.Stop, Double>();
		HashMap<Stops.Stop, Edge> prev = new HashMap<Stops.Stop, Edge>();
		HashMap<Stops.Stop, Boolean> visited = new HashMap<Stops.Stop, Boolean>();
		
		for (Stops.Stop v : q) {
			prev.put(v, null);
			if (v.stopId != source.stopId) {
				dist.put(v, Double.POSITIVE_INFINITY);
			} else {
				dist.put(v, 0.0);
			}
		}
		
		
		while (q.size() - visited.size() != 0) {
			System.out.println("" + (q.size() - visited.size()) + " vertices left to check");
			// find the vertex with the minimum cost in dist
			Stops.Stop u = null;
			double minDist = Double.POSITIVE_INFINITY;
			for (Stops.Stop s : q) {
				if (!visited.containsKey(s) && dist.get(s) <= minDist) {
					u = s;
					minDist = dist.get(s);
				}
			}
			
			visited.put(u, true);
			
			for (Edge e : u.edges) {
				if (!visited.containsKey(e.nextStop)) {
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
		Edge e = prev.get(destination);
		
		ArrayList<Edge> path = new ArrayList<Edge>();
		while (e != null) {
			path.add(e);
			e = prev.get(e.currentStop);
		}
		
		// the solution list is now in reverse order, so reverse it before returning as an array
		Edge[] result = new Edge[path.size()];
		for (int i = path.size() - 1; i >= 0; i--) {
			result[path.size() - i - 1] = path.get(i);
		}
		return result;
	}

	public static double sumCost(Edge[] path) {
		double totalCost = 0.0;
		for (Edge e : path) {
			totalCost += e.cost;
		}
		return totalCost;
	}
}