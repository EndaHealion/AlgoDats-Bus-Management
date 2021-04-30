import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ArrivalTimeSearchPage {
	private static final String BUTTON = "Press Me!";
	private static final String TEXT = "Hello World!";
	
	private static VBox page;
	
	public static VBox createPage() {
		page = new VBox();
		Button button = new Button(BUTTON);
		button.setOnAction(e -> buttonFunction(page));
		page.getChildren().addAll(button);
		return page;
	}

	private static void buttonFunction(VBox page) {
		DevSettings.printToConsole(BUTTON);
		Label text = new Label(TEXT);
		page.getChildren().addAll(text);
	}
	
	public static VBox getPage() {
		return page;
	}
}
