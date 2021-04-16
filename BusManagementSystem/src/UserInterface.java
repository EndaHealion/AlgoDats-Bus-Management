import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Application {

	public static final int SIDE_BUTTON_WIDTH = 250;
	public static final int[] SCREEN_DIMENSIONS = { 1280, 720 };
	public static final int SIDE_BUTTON_HEIGHT = SCREEN_DIMENSIONS[1] / 5;
	public static final String SOFTWARE_NAME = "Vaccine Portal For Users";

	// Creating the window and BorderPane( BorderPane is a layout on the screen).
	Stage window;
	BorderPane borderPane;

	// Creating our different sub screens
	VBox infoPage;
	VBox questionPage;
	VBox appointmentPage;
	StackPane registerPage;
	VBox learnMorePage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle(SOFTWARE_NAME);

		// Initialising BorderPane and adding the left menu buttons
		borderPane = new BorderPane();
		VBox leftMenu = createLeftMenu();
		borderPane.setLeft(leftMenu);

		// Making the border pane the scene and setting screen dimensions
		Scene scene = new Scene(borderPane, SCREEN_DIMENSIONS[0], SCREEN_DIMENSIONS[1]);

		// Setting the screen centre to the info screen (this will be the default screen
		// on start up
		borderPane.setCenter(infoPage);

		// Showing the scene
		window.setScene(scene);
		window.show();

	}

	// -------------------------------------------------------------------------------------

	// Assigning and Creation Function

	// This function creates buttons and adds them to the left menu
	public VBox createLeftMenu() {
		VBox leftMenu = new VBox(0);
		Button infoButton = new Button("Information");
		infoButton.setOnAction(e -> infoButtonFunction());
		infoButton.setMinWidth(SIDE_BUTTON_WIDTH);
		infoButton.setMinHeight(SIDE_BUTTON_HEIGHT);

		Button registerButton = new Button("Register");
		registerButton.setOnAction(e -> registerButtonFunction());
		registerButton.setMinWidth(SIDE_BUTTON_WIDTH);
		registerButton.setMinHeight(SIDE_BUTTON_HEIGHT);

		Button questionButton = new Button("COVID Questionnaire");
		questionButton.setOnAction(e -> questionButtonFunction());
		questionButton.setMinWidth(SIDE_BUTTON_WIDTH);
		questionButton.setMinHeight(SIDE_BUTTON_HEIGHT);

		Button appointmentButton = new Button("Book an appointment");
		appointmentButton.setOnAction(e -> appointmentButtonFunction());
		appointmentButton.setMinWidth(SIDE_BUTTON_WIDTH);
		appointmentButton.setMinHeight(SIDE_BUTTON_HEIGHT);
		
		Button learnMoreButton = new Button("Learn More");
		learnMoreButton.setOnAction(e -> learnMoreButtonFunction());
		learnMoreButton.setMinWidth(SIDE_BUTTON_WIDTH);
		learnMoreButton.setMinHeight(SIDE_BUTTON_HEIGHT);

		leftMenu.getChildren().addAll(infoButton, registerButton, questionButton, appointmentButton, learnMoreButton);
		return leftMenu;
	}

	// -------------------------------------------------------------------------------------

	// Button Functions

	// This function will run when the sign up button is pressed

	// This function will run when the info button is pressed
	public void infoButtonFunction() {
		borderPane.setCenter(infoPage);
		System.out.println("Information Button Pressed");
	}

	// This function will run when the register button is pressed
	public void registerButtonFunction() {
		borderPane.setCenter(registerPage);
		System.out.println("Register Button Pressed");
	}

	// This function will run when the question button is pressed
	public void questionButtonFunction() {
		borderPane.setCenter(questionPage);
		System.out.println("Question Button Pressed");
	}

	// This function will run when the appointment button is pressed
	public void appointmentButtonFunction() {
		borderPane.setCenter(appointmentPage);
		System.out.println("Appointment Button Pressed");
	}
	
	// This function will run when the learn more button is pressed
		public void learnMoreButtonFunction() {
			borderPane.setCenter(learnMorePage);
			System.out.println("Learn More Button Pressed");
		}
}