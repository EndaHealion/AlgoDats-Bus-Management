import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserInterface extends Application {
	
	public static final String PROGRAM_NAME = "Bus Management System";

	Stage window;
	public static BorderPane screenLayout;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle(PROGRAM_NAME);
		screenLayout = new BorderPane();
		Scene scene = new Scene(screenLayout, ScreenDimensions.SCREEN_WIDTH, ScreenDimensions.SCREEN_HEIGHT);
		DisplayPage.createPages();
		screenLayout.setLeft(DisplayPage.buttonPanel);
		screenLayout.setCenter(WelcomePage.getPage());
		window.setScene(scene);
		window.show();
	}
}