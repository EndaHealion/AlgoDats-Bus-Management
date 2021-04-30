import javafx.geometry.Insets;

public class DevSettings {
	
	private static final boolean PRINT_DEV_INFO_TO_CONSOLE = true;
	
	public static final int PADDING = 20;
	public static final int SPACING = 15;
	public static final Insets INSETS = new Insets(PADDING, PADDING, PADDING, PADDING);
	public static final int WRAPPING_WIDTH = 700;
	
	public static final int FONT_LARGE = 24;
	public static final int FONT_MEDIUM = 18;
	public static final int FONT_SMALL = 14;
	
	public static final String NO_ENTRIES = "No results match your query. Please try again.";

	public static void printToConsole(String buttonLabel) {
		if(PRINT_DEV_INFO_TO_CONSOLE) {
			System.out.println("\"" + buttonLabel + "\" Button Pressed\n");
		}	
	}
}
