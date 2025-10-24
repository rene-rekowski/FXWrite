package view;

import controller.FXWriterController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import model.Document;

public class FXWriterApp extends Application {

    @Override
    public void start(Stage stage) {
        Document document = new Document();
        

        VBox pagesContainer = new VBox(20);
        pagesContainer.getStyleClass().add("pages-container");
        PageManager pageManager = new PageManager(pagesContainer, document);
        pageManager.refreshView();

        FXWriterController controller = new FXWriterController(document, pageManager, stage);
        SampleLoader.init(document, pageManager);
        
        BorderPane centeredPane = new BorderPane(pagesContainer);
        BorderPane.setAlignment(pagesContainer, Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(centeredPane);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        BorderPane root = new BorderPane(scrollPane);


        root.setTop(MenuBarFactory.createMenuBar(
                controller::newDocument,
                controller::saveDocument,
                controller::loadDocument,
                pageManager
        ));

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("FXWriter");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles/fxwriter.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
