package controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Document;
import view.PageManager;

import java.io.File;
import java.io.IOException;

public class FXWriterController {

    private final Document document;
    private final PageManager pageManager;
    private final Stage stage;

    public FXWriterController(Document document, PageManager pageManager, Stage stage) {
        this.document = document;
        this.pageManager = pageManager;
        this.stage = stage;
    }

    public void newDocument() {
        document.clear();
        pageManager.refreshView();
    }

    public void saveDocument() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Document");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showSaveDialog(stage);
        if (file != null) {
            try {
                document.saveToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDocument() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Document");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            try {
                document.loadFromFile(file);
                pageManager.refreshView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
