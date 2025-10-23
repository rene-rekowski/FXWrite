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
        pagesContainer.setStyle("-fx-padding: 20;");
        pagesContainer.setAlignment(Pos.TOP_CENTER);

        PageManager pageManager = new PageManager(pagesContainer, document);
        pageManager.refreshView();

        FXWriterController controller = new FXWriterController(document, pageManager, stage);
        SampleLoader.init(document, pageManager);
        
        ScrollPane scrollPane = new ScrollPane(pagesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: gray;");

        BorderPane root = new BorderPane(scrollPane);
        root.setStyle("-fx-background-color: gray;");

        root.setTop(MenuBarFactory.createMenuBar(
                controller::newDocument,
                controller::saveDocument,
                controller::loadDocument
        ));

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("FXWriter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
