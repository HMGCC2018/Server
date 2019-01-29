package GUIWidgets;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.ConnectedClient;

/**
 * Class used to hold all LogObjects in the GUI of the server.
 * @author Oliver Bowker
 * @version 1.0
 */
public class LogContainer extends ScrollPane {

	private VBox layout;
	
	/**
	 * Constructor for LogContainer object, sets up basic styling of widget.
	 */
	public LogContainer() {
		this.layout = new VBox();
		this.setContent(this.layout);
		this.setFitToHeight(true);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}
	
	/**
	 * Function used to update/add a new LogObject to the screen.
	 * @param c = Client that the new LogObject refers to.
	 * @param data = Data about the request the client made.
	 */
	public void update(ConnectedClient c, int[] data) {
		this.layout.getChildren().add(new LogObject(c, data));
	}
	
	/**
	 * Function used to update/add a new LogObject to the screen.
	 * @param message = Message to be displayed in LogObject.
	 */
	public void update(String message) {
		this.layout.getChildren().add(new LogObject(message));
	}
	
}
