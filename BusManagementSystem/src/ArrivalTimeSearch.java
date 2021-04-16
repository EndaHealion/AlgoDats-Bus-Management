import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArrivalTimeSearch
{
	private static ArrayList<tripDetails> trips = null;
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
			trip_id = -1;
			arrival_time = null;
			departure_time = null;
			stop_id = -1;
			stop_sequence = -1;
			stop_headsign = null;
			pickup_type = -1;
			drop_off_type = -1;
			shape_dist_traveled = -1;
		}
	}
	
	ArrivalTimeSearch(String fileLocation) {
		trips = extractStopTimesFromFile(fileLocation);
		if (!validFile) {
			System.out.println("Invalid File");
		}
		System.out.println("Valid Lines " + trips.size());
		System.out.println("Example Output");
		tripDetailsToString(trips.get(1));
		tripDetailsToString(trips.get(1000));
		tripDetailsToString(trips.get(trips.size() - 1));
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
					int arrivalTimeHours = Integer.parseInt(arrivalTime[0]);
					int arrivalTimeMinutes = Integer.parseInt(arrivalTime[1]);
					int arrivalTimeSeconds = Integer.parseInt(arrivalTime[2]);
					if ((arrivalTimeHours >= 0 && arrivalTimeHours <= 23) &&
						(arrivalTimeMinutes >= 0 && arrivalTimeMinutes <= 59) &&
						(arrivalTimeSeconds >= 0 && arrivalTimeSeconds <= 59)) {
						currentTripDetails.arrival_time = new int[]{arrivalTimeHours, arrivalTimeMinutes, arrivalTimeSeconds};
					}
					else validTimes = false;
					
					//Extract Departure Time
					String[] departureTime = currentTrip[2].split(":");
					int departureTimeHours = Integer.parseInt(departureTime[0]);
					int departureTimeMinutes = Integer.parseInt(departureTime[1]);
					int departureTimeSeconds = Integer.parseInt(departureTime[2]);
					if ((departureTimeHours >= 0 && departureTimeHours <= 23) &&
						(departureTimeMinutes >= 0 && departureTimeMinutes <= 59) &&
						(departureTimeSeconds >= 0 && departureTimeSeconds <= 59)) {
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
		return null;
	}
	
	private static void tripDetailsToString(tripDetails details) {
		String output = "";
		output += details.trip_id + ",";
		output += details.arrival_time[0] + ":" + details.arrival_time[1] + ":" + details.arrival_time[2] + ",";
		output += details.departure_time[0] + ":" + details.departure_time[1] + ":" + details.departure_time[2] + ",";
		output += details.stop_id + ",";
		output += details.stop_sequence + ",";
		output += details.stop_headsign + ",";
		output += details.pickup_type + ",";
		output += details.drop_off_type + ",";
		output += details.shape_dist_traveled;
		System.out.println(output);
	}
}