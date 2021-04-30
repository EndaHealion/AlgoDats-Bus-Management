import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WelcomePage {
	private static final String HEADING = "Algorithms & Data Structures Group Project";
	private static final String SUBHEADING = "By Cillian Fogarty, Enda Healion, Cian Mawhinney & Cian O'Gorman.";
	private static final String TEXT = "\nShortest Path Between Stops Implementation by Cian Mawhinney.\n"
			+ "\nBus Stop Ternary Search(TST) Implementation by Enda Healion.\n"
			+ "\nSearch By Time of Arrival Implementation by Cillian Fogarty.\n"
			+ "\nJavaFX11 User Interface Design and Implementation by Cian O'Gorman.";
	
	private static VBox page;
	
	public static VBox createPage() {
		page = new VBox();
		page.setSpacing(DevSettings.SPACING);
		page.setPadding(DevSettings.INSETS);
		VBox pageText = createText();

		

		
		page.getChildren().addAll(pageText);
		return page;
	}

	private static VBox createText() {
		VBox textBox = new VBox();
		
		Text heading = new Text(HEADING);
		heading.setFont(Font.font(null, FontWeight.BOLD, DevSettings.FONT_LARGE));
		
		Text subheading = new Text(SUBHEADING);
		subheading.setFont(Font.font(null, FontWeight.LIGHT, DevSettings.FONT_SMALL));
		
		Text text = new Text(TEXT);
		text.setFont(Font.font(null, FontWeight.NORMAL, DevSettings.FONT_MEDIUM));
		text.setWrappingWidth(DevSettings.WRAPPING_WIDTH);
		
		textBox.getChildren().addAll(heading,subheading,text);
		return textBox;
	}
	
	public static VBox getPage() {
		return page;
	}
}
