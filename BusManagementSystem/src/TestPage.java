import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TestPage {
	
	private static final String BUTTON_LABEL = "Press Me!";
	private static final String TEST_TEXT = "Hello World!";
	
	public static VBox createTestPage() {
		VBox testPage = new VBox();
		Button testButton = new Button(BUTTON_LABEL);
		testButton.setOnAction(e -> testButtonFunction(testPage));
		testPage.getChildren().addAll(testButton);
		return testPage;
	}

	private static void testButtonFunction(VBox testPage) {
		InterfaceOperations.printToConsole(BUTTON_LABEL);
		Label testText = new Label(TEST_TEXT);
		testPage.getChildren().addAll(testText);
	}
}
