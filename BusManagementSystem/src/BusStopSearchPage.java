import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BusStopSearchPage {
	
	private static final String HEADING = "Search for a bus stop.";

	private static TextField searchBoxTextField;
	private static ListView<String> searchResults;

	private static VBox page;

	public static VBox createPage() {
		new Stops("inputs/stops.txt");
		new BusStopSearch(Stops.stops);
		
		page = new VBox();
		page.setSpacing(DevSettings.SPACING);
		page.setPadding(DevSettings.INSETS);
		page.setAlignment(Pos.CENTER);

		Label heading = new Label(HEADING);

		HBox searchBar = createSearchBar();
		searchResults = new ListView<String>();
		VBox searchResultContainer = new VBox();
		searchResultContainer.getChildren().add(searchResults);

		page.getChildren().addAll(heading, searchBar, searchResultContainer);
		return page;
	}

	private static HBox createSearchBar() {
		HBox searchBar = new HBox();
		searchBar.setAlignment(Pos.CENTER);
		searchBoxTextField = new TextField();
		Button searchButton = new Button("Search");
		searchButton.setOnAction(e -> searchButtonFunction());
		searchBar.getChildren().addAll(searchBoxTextField, searchButton);
		return searchBar;
	}

	private static void searchButtonFunction() {
		String searchInput = searchBoxTextField.getText();
		ArrayList<Stops.Stop> finds = BusStopSearch.findStops(searchInput.toUpperCase());
		searchResults.getItems().clear();
		int numFinds = finds.size();
		if (numFinds > 0) {
			for (int i = 0; i < numFinds; i++) {
				searchResults.getItems().add(finds.get(i).toString());
			}
		}
		else {
			searchResults.getItems().add(DevSettings.NO_ENTRIES);
		}
	}

	public static VBox getPage() {
		return page;
	}
}
