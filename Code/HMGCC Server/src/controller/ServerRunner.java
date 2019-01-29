package controller;

import java.io.IOException;
import java.sql.SQLException;

import GUIWidgets.LogContainer;
import GUIWidgets.StatusBar;
import GUIWidgets.WindowMenuBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Class used to start both the server and GUI.
 * @author Oliver Bowker
 * @version 1.0
 */
public class ServerRunner extends Application {
	
	public Server server;
	
	private Stage window;
	private Scene scene;
	private BorderPane layout;
	private WindowMenuBar menuBar;
	private LogContainer logContainer;
	private StatusBar statusBar;
	
	/**
	 * Main method of program.
	 * @param args = Command line arguments.
	 * @throws SQLException - Thrown due to database access.
	 * @throws IOException - Socket exceptions.
	 */
	public static void main(String[] args) throws SQLException, IOException {
		launch(args);
	}

	
	/**
	 * Function to start the GUI, also the Server itself is started.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		this.window.setHeight(700);
		this.window.setWidth(1100);
		this.window.setResizable(false);
		
		this.menuBar = new WindowMenuBar();
		this.logContainer = new LogContainer();
		this.statusBar = new StatusBar();
		this.layout = new BorderPane();
		this.layout.setTop(this.menuBar);
		this.layout.setCenter(this.logContainer);
		this.layout.setBottom(this.statusBar);
		
		this.server = new Server(this.logContainer);
		this.server.startServer();
		
		this.scene = new Scene(this.layout);
		this.scene.getStylesheets().add("file:styles.css");
		this.window.setScene(this.scene);
		this.window.show();
	}
	
}
