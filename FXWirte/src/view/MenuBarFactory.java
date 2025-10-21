package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarFactory {

    public static MenuBar createMenuBar(Runnable onNew, Runnable onSave, Runnable onLoad) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> onNew.run());

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> onSave.run());

        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> onLoad.run());

        fileMenu.getItems().addAll(newItem, saveItem, loadItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }
}
