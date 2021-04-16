import java.io.*;
//import java.util.ArrayList;
import java.util.*;

public class ArrivalTimeSearch {
	
	//Invalid constants
	private static final int INVALID_INT = -1;
	private static final String INVALID_STRING = null;
	private static final int[] INVALID_INT_ARRAY = null;
	private static final ArrayList<tripDetails> INVALID_TRIPS = null;
	
	//Time constants
	private static final int MAX_HOURS = 23;
	private static final int MAX_MINUTES = 59;
	private static final int MAX_SECONDS = 59;
	private static final int MIN_TIME = 0;
	private static final int INDEX_HOURS = 0;
	private static final int INDEX_MINUTES = 1;
	private static final int INDEX_SECONDS = 2;
	
	private static ArrayList<tripDetails> trips = INVALID_TRIPS;
	private static boolean validFile = true;
	
	public class tripDetails {
		private int trip_id;
		private int[] arrival_time;
		private int[] departure_time;
		private int stop_id;
		private int stop_sequence;
		private String stop_headsign;
		private int pickup_type;
		private int drop_off_type;
		private double shape_dist_traveled;

		tripDetails() {
			trip_id = INVALID_INT;
			arrival_time = INVALID_INT_ARRAY;
			departure_time = INVALID_INT_ARRAY;
			stop_id = INVALID_INT;
			stop_sequence = INVALID_INT;
			stop_headsign = INVALID_STRING;
			pickup_type = INVALID_INT;
			drop_off_type = INVALID_INT;
			shape_dist_traveled = INVALID_INT;
		}
	}
	
	ArrivalTimeSearch(String fileLocation) {
		trips = extractStopTimesFromFile(fileLocation);
		if (!validFile) {
			System.out.println("Invalid File");
		}
		else {
			sortsTripsByArrivalTimeAndTripID();
		}
	}
	
	private ArrayList<tripDetails> extractStopTimesFromFile(String fileLocation) {
		if (fileLocation != null) {
			File inputFile = new File(fileLocation);
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
				trips = new ArrayList<tripDetails>();
				bufferedReader.readLine();
				while (bufferedReader.ready())
				{
					boolean validTimes = true;
					String[] currentTrip = bufferedReader.readLine().trim().split(",\\s*");
					tripDetails currentTripDetails = new tripDetails();
					currentTripDetails.trip_id = Integer.parseInt(currentTrip[0]);
					
					//Extract Arrival Time
					String[] arrivalTime = currentTrip[1].split(":");
					int arrivalTimeHours = Integer.parseInt(arrivalTime[INDEX_HOURS]);
					int arrivalTimeMinutes = Integer.parseInt(arrivalTime[INDEX_MINUTES]);
					int arrivalTimeSeconds = Integer.parseInt(arrivalTime[INDEX_SECONDS]);
					if ((arrivalTimeHours >= MIN_TIME && arrivalTimeHours <= MAX_HOURS) &&
						(arrivalTimeMinutes >= MIN_TIME && arrivalTimeMinutes <= MAX_MINUTES) &&
						(arrivalTimeSeconds >= MIN_TIME && arrivalTimeSeconds <= MAX_SECONDS)) {
						currentTripDetails.arrival_time = new int[]{arrivalTimeHours, arrivalTimeMinutes, arrivalTimeSeconds};
					}
					else validTimes = false;
					
					//Extract Departure Time
					String[] departureTime = currentTrip[2].split(":");
					int departureTimeHours = Integer.parseInt(departureTime[INDEX_HOURS]);
					int departureTimeMinutes = Integer.parseInt(departureTime[INDEX_MINUTES]);
					int departureTimeSeconds = Integer.parseInt(departureTime[INDEX_SECONDS]);
					if ((departureTimeHours >= MIN_TIME && departureTimeHours <= MAX_HOURS) &&
						(departureTimeMinutes >= MIN_TIME && departureTimeMinutes <= MAX_MINUTES) &&
						(departureTimeSeconds >= MIN_TIME && departureTimeSeconds <= MAX_SECONDS)) {
						currentTripDetails.departure_time = new int[]{departureTimeHours, departureTimeMinutes, departureTimeSeconds};
					}
					else validTimes = false;
					
					currentTripDetails.stop_id = Integer.parseInt(currentTrip[3]);
					currentTripDetails.stop_sequence = Integer.parseInt(currentTrip[4]);
					currentTripDetails.stop_headsign = currentTrip[5];
					currentTripDetails.pickup_type = Integer.parseInt(currentTrip[6]);
					currentTripDetails.drop_off_type = Integer.parseInt(currentTrip[7]);
					currentTripDetails.shape_dist_traveled = (currentTrip.length == 9) ? Double.parseDouble(currentTrip[8]) : 0;
					if (validTimes) {
						trips.add(currentTripDetails);
					}
				}
				bufferedReader.close();
				return trips;
			}
			catch (IOException ie) {
				validFile = false;
			}
			catch (OutOfMemoryError E) {
				System.out.println("Out of Memory");
				validFile = false;
			}
		}
		else {
			validFile = false;
		}
		return INVALID_TRIPS;
	}

	private static void sortsTripsByArrivalTimeAndTripID() {
		Collections.sort(trips, new TripsComparator());
	}
	
	public static class TripsComparator implements Comparator<tripDetails> {
	    public int compare(tripDetails trip1, tripDetails trip2) {
	    	//Sort by time
	    	int trip1TimeInSeconds = (trip1.arrival_time[INDEX_HOURS] * 3600) + (trip1.arrival_time[INDEX_MINUTES] * 60) + trip1.arrival_time[INDEX_SECONDS];
	    	int trip2TimeInSeconds = (trip2.arrival_time[INDEX_HOURS] * 3600) + (trip2.arrival_time[INDEX_MINUTES] * 60) + trip2.arrival_time[INDEX_SECONDS];
	        int compareValue = trip1TimeInSeconds - trip2TimeInSeconds;
	        //Sort by trip_id
	        if (compareValue == 0) {
	            return trip1.trip_id - trip2.trip_id;
	        }
	        return compareValue;
	    }
	}
	
	public static ArrayList<tripDetails> findTripsAtArrivalTime(int[] arrivalTime) {
		ArrayList<tripDetails> tripsAtSpecifiedTime = new ArrayList<tripDetails>();
		for (tripDetails details : trips) {
			if (details.arrival_time[INDEX_HOURS] == arrivalTime[INDEX_HOURS] &&
				details.arrival_time[INDEX_MINUTES] == arrivalTime[INDEX_MINUTES] &&
				details.arrival_time[INDEX_SECONDS] == arrivalTime[INDEX_SECONDS]) {
					tripsAtSpecifiedTime.add(details);
			}
		}
		return tripsAtSpecifiedTime;
	}
	
	//Gets
	public static int getTripID(tripDetails details) {
		return details.trip_id;
	}
	
	public static String getTripArrivalTime(tripDetails details) {
		return String.format("%02d", details.arrival_time[INDEX_HOURS]) + ":" + String.format("%02d", details.arrival_time[INDEX_MINUTES]) + ":" 
				+ String.format("%02d", details.arrival_time[INDEX_SECONDS]);
	}
	
	public static String getTripDepartureTime(tripDetails details) {
		return String.format("%02d", details.departure_time[INDEX_HOURS]) + ":" + String.format("%02d", details.departure_time[INDEX_MINUTES]) + ":" 
				+ String.format("%02d", details.departure_time[INDEX_SECONDS]);
	}
	
	public static int getStopID(tripDetails details) {
		return details.stop_id;
	}
	
	public static int getStopSequence(tripDetails details) {
		return details.stop_sequence;
	}
	
	public static String getStopHeadsign(tripDetails details) {
		return details.stop_headsign;
	}
	
	public static int getPickupType(tripDetails details) {
		return details.pickup_type;
	}
	
	public static int getDropOffType(tripDetails details) {
		return details.drop_off_type;
	}
	
	public static double getShapeDistTravelled(tripDetails details) {
		return details.shape_dist_traveled;
	}
}