package userInterface;

import algorithms.ArrivalTimeSearch;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ArrivalTimeSearchPage {
	
	private static final String HEADING = "Search for routes by arrival time.";

	private static final String HOUR = "Hour";
	private static final String MINUTE = "Minute";
	private static final String SECOND = "Second";

	private static VBox page;

	private static ComboBox<String> hourSelect;
	private static ComboBox<String> minuteSelect;
	private static ComboBox<String> secondSelect;

	private static ListView<String> searchResults;

	public static VBox createPage() {
		new ArrivalTimeSearch("inputs/stop_times.txt");
		
		page = new VBox();
		page.setSpacing(DevSettings.SPACING);
		page.setPadding(DevSettings.INSETS);
		page.setAlignment(Pos.CENTER);
		
		Label heading = new Label(HEADING);

		HBox timePicker = createTimePicker();
		searchResults = new ListView<String>();
		VBox searchResultContainer = new VBox();
		searchResultContainer.getChildren().add(searchResults);

		page.getChildren().addAll(heading, timePicker, searchResultContainer);
		return page;
	}

	private static HBox createTimePicker() {
		hourSelect = new ComboBox<String>();
		hourSelect.setEditable(true);
		hourSelect.getItems().add(HOUR);
		hourSelect.setValue(HOUR);

		final int hourLimit = 23;
		for (int i = 0; i <= hourLimit; i++) {
			hourSelect.getItems().add(Integer.toString(i));
		}

		minuteSelect = new ComboBox<String>();
		minuteSelect.setEditable(true);
		minuteSelect.getItems().add(MINUTE);
		minuteSelect.setValue(MINUTE);

		secondSelect = new ComboBox<String>();
		secondSelect.setEditable(true);
		secondSelect.getItems().add(SECOND);
		secondSelect.setValue(SECOND);

		final int minuteSecondLimit = 59;
		for (int i = 0; i <= minuteSecondLimit; i++) {
			minuteSelect.getItems().add(Integer.toString(i));
			secondSelect.getItems().add(Integer.toString(i));
		}

		Button searchButton = new Button("Search");
		searchButton.setOnAction(e -> searchButtonFunction());

		HBox timePicker = new HBox();
		timePicker.getChildren().addAll(hourSelect, minuteSelect, secondSelect, searchButton);
		timePicker.setAlignment(Pos.CENTER);
		timePicker.setSpacing(DevSettings.SPACING);
		return timePicker;
	}

	private static void searchButtonFunction() {
		int[] time = getTimePickerValues();
		ArrayList<ArrivalTimeSearch.tripDetails> tripsAtArrivalTime = ArrivalTimeSearch.findTripsAtArrivalTime(time);
		searchResults.getItems().clear();
		int numberOfEntries = tripsAtArrivalTime.size();
		if (numberOfEntries > 0) {
			for (int i = 0; i < numberOfEntries; i++) {
				ArrivalTimeSearch.tripDetails details = tripsAtArrivalTime.get(i);
				String output = (i + 1) + ": ";
				output += ArrivalTimeSearch.getTripID(details) + ", ";
				output += ArrivalTimeSearch.getTripArrivalTime(details) + ", ";
				output += ArrivalTimeSearch.getTripDepartureTime(details) + ", ";
				output += ArrivalTimeSearch.getStopID(details) + ", ";
				output += ArrivalTimeSearch.getStopSequence(details) + ", ";
				output += ArrivalTimeSearch.getStopHeadsign(details) + ", ";
				output += ArrivalTimeSearch.getPickupType(details) + ", ";
				output += ArrivalTimeSearch.getDropOffType(details) + ", ";
				output += ArrivalTimeSearch.getShapeDistTravelled(details);
				searchResults.getItems().add(output);
			}
		} else {
			searchResults.getItems().add(DevSettings.NO_ENTRIES);
		}
	}

	private static int[] getTimePickerValues() {
		int[] time = new int[3];

		try {
			time[0] = Integer.parseInt(hourSelect.getValue());
		} catch (Exception e) {
			time[0] = 0;
		}
		try {
			time[1] = Integer.parseInt(minuteSelect.getValue());
		} catch (Exception e) {
			time[1] = 0;
		}
		try {
			time[2] = Integer.parseInt(secondSelect.getValue());
		} catch (Exception e) {
			time[2] = 0;
		}
		return time;
	}

	public static VBox getPage() {
		return page;
	}
}
