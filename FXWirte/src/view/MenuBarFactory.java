package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import service.CoderService;

/**
 * Initialized the menu-bar
 * 
 * @author rene-rekowski
 * @version 1.0
 */
public class MenuBarFactory {

	public static MenuBar createMenuBar(Runnable onNew, Runnable onSave, Runnable onLoad, PageManager pageManager) {
		MenuBar menuBar = new MenuBar();

		// File-Menu
		Menu fileMenu = new Menu("File");

		MenuItem newItem = new MenuItem("New");
		newItem.setOnAction(e -> onNew.run());

		MenuItem saveItem = new MenuItem("Save");
		saveItem.setOnAction(e -> onSave.run());

		MenuItem loadItem = new MenuItem("Load");
		loadItem.setOnAction(e -> onLoad.run());

		// Coder-Menu
		Menu coderMenu = new Menu("Encode/Decode");
		// Caesar
		MenuItem caesarEncode = new MenuItem("Caesar Encode (+3)");
		caesarEncode.setOnAction(e -> CoderService.encodeCaesar(pageManager, 3));
		MenuItem caesarDecode = new MenuItem("Caesar Decode (-3)");
		caesarDecode.setOnAction(e -> CoderService.decodeCaesar(pageManager, 3));
		// LZW 
		MenuItem lzwEncode = new MenuItem("LZW Encode");
		lzwEncode.setOnAction(e -> CoderService.encodeLZW(pageManager));
		MenuItem lzwDecode = new MenuItem("LZW Decode");
		;
		lzwDecode.setOnAction(e -> CoderService.decodeLZW(pageManager));

		// Sample-Menu
		Menu sampleMenu = new Menu("Sample");
		MenuItem zeitmachineItem = new MenuItem("Zeitmachine");
		zeitmachineItem.setOnAction(e -> SampleLoader.loadSampleFromResource("/Zeitmaschine-kapitel-1.txt"));

		coderMenu.getItems().addAll(caesarEncode, caesarDecode, lzwEncode, lzwDecode);
		sampleMenu.getItems().addAll(zeitmachineItem);
		fileMenu.getItems().addAll(newItem, saveItem, loadItem);
		menuBar.getMenus().addAll(fileMenu, coderMenu, sampleMenu);

		return menuBar;
	}
}
