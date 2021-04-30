package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		Button welcomeButton = createButton(WELCOME, e -> welcomeButtonFunction());
		Button shortestPathButton = createButton(ROUTE_FINDER, e -> shortestPathButtonFunction());
		Button busStopSearchButton = createButton(STOP_SEARCH, e -> busStopSearchButtonFunction());
		Button arrivalTimeSearchButton = createButton(ARRIVAL_TIME_SEARCH, e -> arrivalTimeSearchButtonFunction());
		buttonPanel.getChildren().addAll(welcomeButton, shortestPathButton, busStopSearchButton, arrivalTimeSearchButton);
		return buttonPanel;
	}
	
	private static Button createButton(String buttonText, EventHandler<ActionEvent> buttonEvent) {
		Button button = new Button(buttonText);
		button.setOnAction(buttonEvent);
		button.setMinWidth(BUTTON_WIDTH);
		button.setMinHeight(BUTTON_HEIGHT);
		return button;
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
