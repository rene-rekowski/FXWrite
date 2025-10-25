package controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Document;
import model.FileIO;
import view.PageManager;

import java.io.File;
import java.io.IOException;

/**
 * controller document interaction
 * 
 * @author rene-rekowski
 */
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
                FileIO.saveToFile(document, file);
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
                FileIO.loadFromFile(document, file);
                pageManager.refreshView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
