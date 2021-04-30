package userInterface;

import algorithms.BusStopsShortestPath;
import algorithms.Edge;
import algorithms.Stops;

import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BusStopsShortestPathPage {

	private static VBox page;

	private static TextField sourceTextField;
	private static TextField destinationTextField;
	private static ListView<String> searchResults;

	public static VBox createPage() {
		new Stops("inputs/stops.txt");
		page = new VBox();
		page.setSpacing(DevSettings.SPACING);
		page.setPadding(DevSettings.INSETS);
		page.setAlignment(Pos.CENTER);

		HBox searchBar = createSearchBar();
		searchResults = new ListView<String>();
		VBox searchResultContainer = new VBox();
		searchResultContainer.getChildren().add(searchResults);

		page.getChildren().addAll(searchBar, searchResultContainer);
		return page;
	}

	private static HBox createSearchBar() {
		HBox searchBar = new HBox();
		searchBar.setAlignment(Pos.CENTER);
		searchBar.setSpacing(DevSettings.SPACING);
		searchBar.setPadding(DevSettings.INSETS);

		VBox sourceTextBar = new VBox();
		Label sourceText = new Label("Start stop I.D");
		sourceTextField = new TextField();
		sourceTextBar.getChildren().addAll(sourceText, sourceTextField);

		VBox destinationTextBar = new VBox();
		Label destinationText = new Label("Destination stop I.D");
		destinationTextField = new TextField();
		destinationTextBar.getChildren().addAll(destinationText, destinationTextField);

		Button searchButton = new Button("Search");
		searchButton.setOnAction(e -> searchButtonFunction());

		searchBar.getChildren().addAll(sourceTextBar, destinationTextBar, searchButton);
		return searchBar;
	}

	private static void searchButtonFunction() {
		try {
			BusStopsShortestPath.buildGraph("inputs/stop_times.txt", "inputs/transfers.txt");
		} catch (IOException e) {
			System.err.println("Could not find specified files. Exiting");
			return;
		}

		Stops.Stop source = Stops.findStopById(sourceTextField.getText());
		Stops.Stop destination = Stops.findStopById(destinationTextField.getText());

		Edge[] path = BusStopsShortestPath.shortestPath(source, destination);
		// if the path is of length 0, there isn't a solution

		searchResults.getItems().clear();

		if (path.length > 0) {
			searchResults.getItems().add("Cost of journey: " + BusStopsShortestPath.sumCost(path));
			searchResults.getItems().add("I.D of each stop on path: ");
			int index = 1;
			for (Edge e : path) {
				System.out.print(e.currentStop.stopId + "->");
				searchResults.getItems().add("" + index + ": " + Integer.toString(e.currentStop.stopId));
				index++;
			}
		} else {
			searchResults.getItems().add(DevSettings.NO_ENTRIES);
		}
	}

	public static VBox getPage() {
		return page;
	}
}
