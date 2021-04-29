
class Edge {
		int tripId;
		Stops.Stop currentStop;
		Stops.Stop nextStop;
		double cost;
		// LocalTime arrivalTime;
		// LocalTime departureTime; // not sure we need both of these

		/*
		Edge(int tripId, Stops.Stop currentStop, Stops.Stop nextStop, double cost, LocalTime arrivalTime, LocalTime departureTime) {
			this.tripId = tripId;
			this.currentStop = currentStop;
			this.nextStop = nextStop;
			this.cost = cost;
			this.arrivalTime = arrivalTime;
			this.departureTime = departureTime;
		}
		*/
		
		Edge(int tripId, Stops.Stop currentStop, Stops.Stop nextStop, double cost) {
			this.tripId = tripId;
			this.currentStop = currentStop;
			this.nextStop = nextStop;
			this.cost = cost;
		}
}