package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXWriterApp extends Application {

    @Override
    public void start(Stage stage) {
        // Root-Layout
        BorderPane root = new BorderPane();

        // TextArea – Hauptbereich
        TextArea textArea = new TextArea();
        root.setCenter(textArea);

        // Menüleiste
        MenuBar menuBar = new MenuBar();

        // Menü "Datei"
        Menu fileMenu = new Menu("Datei");
        MenuItem newItem = new MenuItem("Neu");
        MenuItem openItem = new MenuItem("Öffnen");
        MenuItem saveItem = new MenuItem("Speichern");
        MenuItem exitItem = new MenuItem("Beenden");

        // Aktionen 
        newItem.setOnAction(e -> textArea.clear());
        exitItem.setOnAction(e -> stage.close());

        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem(), exitItem);

        // Menü "Bearbeiten"
        Menu editMenu = new Menu("Bearbeiten");
        MenuItem copyItem = new MenuItem("Kopieren");
        MenuItem pasteItem = new MenuItem("Einfügen");
        MenuItem cutItem = new MenuItem("Ausschneiden");
        editMenu.getItems().addAll(copyItem, pasteItem, cutItem);

        // Menüleiste hinzufügen
        menuBar.getMenus().addAll(fileMenu, editMenu);
        root.setTop(menuBar);

        // Szene erstellen
        Scene scene = new Scene(root, 800, 600);

        // Stage konfigurier
        stage.setTitle("FXWriter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
