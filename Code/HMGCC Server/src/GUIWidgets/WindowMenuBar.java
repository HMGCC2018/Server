package GUIWidgets;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * The purpose of this class is to keep the ServerRunner/Main class clean and easy to read.
 * The other purpose is to create the MenuBar object for the top of the GUI.
 * @author Oliver Bowker
 * @version 1.0
 */
public class WindowMenuBar extends MenuBar {

	private Menu menuFile;
	private MenuItem menuItemRestartServer;
	private MenuItem menuItemCloseAndShutdown;
	private MenuItem menuItemExit;
	
	private Menu menuEdit;
	
	private Menu menuAbout;
	private MenuItem menuItemCreatedBy;
	
	/**
	 * Constructor for WindowMenuBar.
	 */
	public WindowMenuBar() {
		this.menuFile = new Menu("File");
		this.menuItemRestartServer = new MenuItem("Restart Server");
		this.menuItemCloseAndShutdown = new MenuItem("Exit and Shutdown");
		this.menuItemExit = new MenuItem("Exit");
		
		this.menuEdit = new Menu("Edit");
		
		this.menuAbout = new Menu("About");
		this.menuItemCreatedBy = new MenuItem("Created By - HMGCC & Bowker Productions");
		this.setup();
	}
	
	/**
	 * Function used to add all the menus to the correct parent objects.
	 */
	private void setup() {
		this.menuFile.getItems().addAll(this.menuItemRestartServer, 
										new SeparatorMenuItem(), 
										this.menuItemCloseAndShutdown, 
										this.menuItemExit);
		this.menuAbout.getItems().addAll(this.menuItemCreatedBy);
		this.getMenus().addAll(this.menuFile, this.menuEdit, this.menuAbout);
	}
	
}
