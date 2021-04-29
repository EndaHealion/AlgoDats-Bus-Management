
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Stops {
	public static ArrayList<Stop> stops;
	public static HashMap<String, Integer> stopNameIndexMap;
	public static HashMap<Integer, Stop> stopIdIndex;
	public static boolean isValid = false;

	public class Stop {
		public int stopId = 0;
		public int stopCode = 0;
		public String stopName = "";
		public String stopDesc = "";
		public float stopLat = 0.0f;
		public float stopLon = 0.0f;
		public String zoneId = "";
		public String stopUrl = "";
		public int locationType = 0;
		public int parentStation = 0;
		
		public ArrayList<Edge> edges = new ArrayList<Edge>();

		public static final int ID_INDEX = 0;
		public static final int CODE_INDEX = 1;
		public static final int NAME_INDEX = 2;
		public static final int DESC_INDEX = 3;
		public static final int LAT_INDEX = 4;
		public static final int LON_INDEX = 5;
		public static final int ZONE_ID_INDEX = 6;
		public static final int URL_INDEX = 7;
		public static final int LOC_TYPE_INDEX = 8;
		public static final int PAREN_STATION_INDEX = 9;
		public static final int MEMBER_COUNT = 10;

		public Stop(int stopId, int stopCode, String stopName, String stopDesc, float stopLat, float stopLon,
				String zoneId, String stopUrl, int locationType, int parentStation) {
			this.stopId = stopId;
			this.stopCode = stopCode;
			this.stopName = stopName;
			this.stopDesc = stopDesc;
			this.stopLat = stopLat;
			this.stopLon = stopLon;
			this.zoneId = zoneId;
			this.stopUrl = stopUrl;
			this.locationType = locationType;
			this.parentStation = parentStation;
		}

		Stop(String[] csvData) {
			try { stopId   = Integer.parseInt(csvData[ID_INDEX]); } catch (Exception e) { stopId = 0;   }
			try { stopCode = Integer.parseInt(csvData[CODE_INDEX]); } catch (Exception e) { stopCode = 0; }
			
			StringBuilder name = new StringBuilder();
			name.append(csvData[NAME_INDEX]);
			makeStopSearchable(name);
			stopName = name.toString();

			stopDesc = csvData[DESC_INDEX];
			
			try { stopLat = Float.parseFloat(csvData[LAT_INDEX]); } catch (Exception e) { stopLat = 0.0f; }
			try { stopLon = Float.parseFloat(csvData[LON_INDEX]); } catch (Exception e) { stopLon = 0.0f; }
			
			zoneId = csvData[ZONE_ID_INDEX];
			stopUrl = csvData[URL_INDEX];
			
			try { locationType  = Integer.parseInt(csvData[LOC_TYPE_INDEX]); } catch (Exception e) { locationType = 0;  }
			try { parentStation = Integer.parseInt(csvData[PAREN_STATION_INDEX]); } catch (Exception e) { parentStation = 0; }
		}
				
		public String toString() {
			StringBuilder ret = new StringBuilder();
			ret.append(stopId);
			ret.append(", ");
			ret.append(stopCode);
			ret.append(", ");
			ret.append(stopName);
			ret.append(", ");
			ret.append(stopDesc);
			ret.append(", ");
			ret.append(stopLat);
			ret.append(", ");
			ret.append(stopLon);
			ret.append(", ");
			ret.append(zoneId);
			ret.append(", ");
			ret.append(stopUrl);
			ret.append(", ");
			ret.append(locationType);
			ret.append(", ");
			ret.append(parentStation);
			return ret.toString();
		}
		
		@Override
		public Stops.Stop clone() {
			return new Stops.Stop(
					this.stopId,
					this.stopCode,
					this.stopName,
					this.stopDesc,
					this.stopLat,
					this.stopLon,
					this.zoneId,
					this.stopUrl,
					this.locationType,
					this.parentStation);
			
		}
	}

	Stops(String fileLocation) {
		stops = new ArrayList<Stop>();
		stopNameIndexMap = new HashMap<String, Integer>();
		stopIdIndex = new HashMap<Integer, Stop>();
		if (fileLocation == null) {
			return;
		}

		int currentStop = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileLocation));

			// read line to get rid of column descriptors in CSV file...
			String line = reader.readLine();
			if (line == null) {
				System.out.println("[BusStopSearch] WARNING: No data in file: " + fileLocation);
				reader.close();
				return;
			}

			line = reader.readLine();
			while (line != null) {
				// System.out.println(line);

				String[] csvData = line.trim().split(",\\s*");
				if (csvData.length <= Stop.MEMBER_COUNT) {
					Stop s = new Stop(csvData);
					stops.add(s);
					stopNameIndexMap.put(s.stopName, currentStop);
					
					// create index between stop id and the stop object
					stopIdIndex.put(s.stopId, s);
					
					currentStop++;
					line = reader.readLine();
				}
			}

			isValid = true;
			reader.close();
		} catch (IOException e) {
			System.out.println("Could not open file at location: " + fileLocation);
		}
	}
	
	// Manipulate the stop name to make it more searchable...
	// Move wb,nb,sb,eb from the start to the end
	private void makeStopSearchable(StringBuilder stopName) {
		if (stopName == null) {
			return;
		}

		int numPrefixChars = 3;
		if (stopName.length() > numPrefixChars) {
			if (stopName.charAt(1) == 'B' && stopName.charAt(2) == ' ') {
				switch (stopName.charAt(0)) {
				case 'W':
				case 'N':
				case 'S':
				case 'E':
					stopName.delete(0, 3);
					stopName.append(' ');
					stopName.append(stopName.charAt(0));
					stopName.append('B');
					break;

				default:
				}
			}
		}
	}
	
	public static Stops.Stop findStopById(String stopId) {
		int id = Integer.parseInt(stopId);
		return findStopById(id);
	}
	
	public static Stops.Stop findStopById(int stopId) {
		/*
		for (Stops.Stop s : stops) {
			if (s.stopId == stopId){
				return s;
			}
		}
		// didn't find anything
		return null;
		*/
		return stopIdIndex.get(stopId);
	}
}
