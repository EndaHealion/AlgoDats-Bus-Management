import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DisplayPage {
	
	private static final int BUTTON_AMOUNT = 4;
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = ScreenDimensions.SCREEN_HEIGHT / BUTTON_AMOUNT;
	
	private static final String WELCOME = "Welcome";
	private static final String ROUTE_FINDER = "Route Finder";
	private static final String STOP_SEARCH = "Bus Stop Search";
	private static final String ARRIVAL_TIME_SEARCH = "Arrival Time Search";
	
	public static VBox displayPage;
	public static VBox buttonPanel;
	
	public static void createPages(){
		buttonPanel = createButtonPanel();
		WelcomePage.createPage();
		BusStopsShortestPathPage.createPage();
		BusStopSearchPage.createPage();
		ArrivalTimeSearchPage.createPage();
		displayPage = WelcomePage.getPage();
	}
	
	public static VBox createButtonPanel() {
		VBox buttonPanel = new VBox();
		
		Button welcomeButton = new Button(WELCOME);
		welcomeButton.setOnAction(e -> welcomeButtonFunction());
		welcomeButton.setMinWidth(BUTTON_WIDTH);
		welcomeButton.setMinHeight(BUTTON_HEIGHT);
		
		Button shortestPathButton = new Button(ROUTE_FINDER);
		shortestPathButton.setOnAction(e -> shortestPathButtonFunction());
		shortestPathButton.setMinWidth(BUTTON_WIDTH);
		shortestPathButton.setMinHeight(BUTTON_HEIGHT);
		
		Button busStopSearchButton = new Button(STOP_SEARCH);
		busStopSearchButton.setOnAction(e -> busStopSearchButtonFunction());
		busStopSearchButton.setMinWidth(BUTTON_WIDTH);
		busStopSearchButton.setMinHeight(BUTTON_HEIGHT);
		
		Button arrivalTimeSearchButton = new Button(ARRIVAL_TIME_SEARCH);
		arrivalTimeSearchButton.setOnAction(e -> arrivalTimeSearchButtonFunction());
		arrivalTimeSearchButton.setMinWidth(BUTTON_WIDTH);
		arrivalTimeSearchButton.setMinHeight(BUTTON_HEIGHT);
		
		buttonPanel.getChildren().addAll(welcomeButton, shortestPathButton, busStopSearchButton, arrivalTimeSearchButton);
		return buttonPanel;
	}
	
	private static void welcomeButtonFunction() {
		DevSettings.printToConsole(WELCOME);
		UserInterface.screenLayout.setCenter(WelcomePage.getPage());
	}
	
	private static void shortestPathButtonFunction() {
		DevSettings.printToConsole(ROUTE_FINDER);
		UserInterface.screenLayout.setCenter(BusStopsShortestPathPage.getPage());
	}
	
	private static void busStopSearchButtonFunction() {
		DevSettings.printToConsole(STOP_SEARCH);
		UserInterface.screenLayout.setCenter(BusStopSearchPage.getPage());
	}

	private static void arrivalTimeSearchButtonFunction() {
		DevSettings.printToConsole(ARRIVAL_TIME_SEARCH);
		UserInterface.screenLayout.setCenter(ArrivalTimeSearchPage.getPage());
	}
}
