package GUIWidgets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import controller.Server.RequestType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.ConnectedClient;

/**
 * Class used to display logs in the GUI.
 * @author Oliver Bowker
 * @version 1.0
 */
public class LogObject extends HBox {

	private Label lblLogInfo;
	private Circle requestStatus;
	
	/**
	 * Constructor for LogObject object.
	 * @param c = Client that the new log will be about.
	 * @param data = Data about the request the client made.
	 */
	public LogObject(ConnectedClient c, int[] data) {
		this.getStyleClass().add("log");
		this.setPadding(new Insets(12, 8, 12, 8));
		
		// Get data about request and time of request.
		RequestType request = RequestType.values()[data[0]];
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		this.lblLogInfo = new Label(request.toString() + " request from " + c.getID() + " at " + sdf.format(cal.getTime()));
		this.lblLogInfo.getStyleClass().add("log-text");
		
		this.requestStatus = new Circle(10);
		if (data[1] == 0) this.requestStatus.setFill(Color.valueOf("#e8101e"));
		else this.requestStatus.setFill(Color.valueOf("#2ac13e"));
		this.requestStatus.setTranslateY(3);
		
		HBox.setMargin(this.requestStatus, new Insets(0, 0, 0, 20));
		
		this.getChildren().addAll(this.lblLogInfo, this.requestStatus);
	}
	
	/**
	 * Overloaded constructor for LogObject class, used for connection and disconnections of clients.
	 * @param message = Message to be displayed in the log.
	 */
	public LogObject(String message) {
		this.getStyleClass().add("log");
		this.setPadding(new Insets(12, 8, 12, 8));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		this.lblLogInfo = new Label(message +  " at " + sdf.format(cal.getTime()));
		this.lblLogInfo.getStyleClass().add("log-text");
		this.getChildren().add(this.lblLogInfo);
	}
	
}
