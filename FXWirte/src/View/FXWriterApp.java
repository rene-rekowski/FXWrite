package View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FXWriterApp extends Application {

    private VBox pagesContainer;
    private final double pageWidth = 794;   // DIN-A4 Breite in px bei 96 DPI
    private final double pageHeight = 1123; // DIN-A4 Höhe in px bei 96 DPI
    private final double padding = 10;

    private final int charsPerLine = 80;   // Max Zeichen pro Zeile
    private final int linesPerPage = 3;   // Max Zeilen pro Seite

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: gray;");

        // VBox Container für Seiten
        pagesContainer = new VBox(20); // Abstand zwischen Seiten
        pagesContainer.setStyle("-fx-padding: 20;");
        pagesContainer.setAlignment(Pos.TOP_CENTER); // Seiten zentriert

        // Erste Seite hinzufügen
        addNewPage();

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(pagesContainer);
        scrollPane.setFitToWidth(true); 
        scrollPane.setFitToHeight(false);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: gray;");

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("FXWriter - Mehrseitig");
        stage.setScene(scene);
        stage.show();
    }

    private void addNewPage() {
        TextArea page = new TextArea();
        page.setWrapText(true);

        // Feste Seitengröße
        page.setPrefSize(pageWidth, pageHeight);
        page.setMaxSize(pageWidth, pageHeight);
        page.setMinSize(pageWidth, pageHeight);

        // Styling
        page.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: gray;" +
                "-fx-border-width: 1;" +
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 14pt;" +
                "-fx-padding: " + padding + ";"
        );

        // Abstand zwischen Seiten
        VBox.setMargin(page, new Insets(10, 0, 10, 0));

        // Listener für Textänderungen
        page.textProperty().addListener((obs, oldText, newText) -> {
            Platform.runLater(() -> {
                String[] lines = page.getText().split("\n", -1);
                int totalLines = 0;

                // Berechne alle Zeilen inkl. Umbrüche
                for (String line : lines) {
                    int wrappedLines = (int) Math.ceil((double) line.length() / charsPerLine);
                    totalLines += Math.max(wrappedLines, 1);
                }

                if (totalLines > linesPerPage) {
                    // Text, der auf nächste Seite muss
                    StringBuilder overflowText = new StringBuilder();
                    int lineCounter = 0;

                    for (String line : lines) {
                        int wrappedLines = (int) Math.ceil((double) line.length() / charsPerLine);
                        if (lineCounter + wrappedLines <= linesPerPage) {
                            lineCounter += wrappedLines;
                        } else {
                            int remainingLines = linesPerPage - lineCounter;
                            int startIndex = remainingLines * charsPerLine;
                            if (startIndex < line.length()) {
                                overflowText.append(line.substring(startIndex)).append("\n");
                            }
                            // Den Rest aller nachfolgenden Zeilen hinzufügen
                            int currentIndex = java.util.Arrays.asList(lines).indexOf(line);
                            for (int i = currentIndex + 1; i < lines.length; i++) {
                                overflowText.append(lines[i]).append("\n");
                            }
                            break;
                        }
                    }

                    // Neue Seite erstellen mit Resttext
                    addNewPage();
                    TextArea nextPage = (TextArea) pagesContainer.getChildren()
                            .get(pagesContainer.getChildren().size() - 1);
                    nextPage.setText(overflowText.toString());
                    nextPage.requestFocus();
                    nextPage.positionCaret(overflowText.length());
                }
            });
        });

        pagesContainer.getChildren().add(page);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
