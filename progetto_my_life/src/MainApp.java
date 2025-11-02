
import main.control.Control;
public class MainApp{

	public static void main(String[] args) {
		final Control controller = Control.getControlInstance();
		controller.startGame();
	}
}