package GUIWidgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Class used as a status bar at the bottom of the servers GUI.
 * @author Oliver Bowker
 * @version 1.0
 */
public class StatusBar extends HBox {
	
	/**
	 * Constructor for StatusBar class.
	 */
	public StatusBar() {
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(new Label("Status Bar"));
		this.setup();
	}
	
	/**
	 * Styling of StatusBar widget.
	 */
	private void setup() {
		this.getStyleClass().add("status-bar");
	}
	
}
