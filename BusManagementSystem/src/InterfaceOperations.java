
public class InterfaceOperations {
	
	private static final boolean PRINT_DEV_INFO_TO_CONSOLE = true;

	public static void printToConsole(String buttonLabel) {
		if(PRINT_DEV_INFO_TO_CONSOLE) {
			System.out.println("\"" + buttonLabel + "\" Button Pressed\n");
		}	
	}
}
