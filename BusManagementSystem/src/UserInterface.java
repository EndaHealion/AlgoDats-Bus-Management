import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Application {
	
	public static final int[] SCREEN_DIMENSIONS = { 1280, 720 };
	public static final String PROGRAM_NAME = "Bus Management System";

	Stage window;
	BorderPane screenLayout;
	VBox testPage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle(PROGRAM_NAME);
		screenLayout = new BorderPane();
		Scene scene = new Scene(screenLayout, SCREEN_DIMENSIONS[0], SCREEN_DIMENSIONS[1]);
		ArrivalTimeSearchPage.createPage();
		screenLayout.setCenter(ArrivalTimeSearchPage.getPage());
		window.setScene(scene);
		window.show();
	}
}