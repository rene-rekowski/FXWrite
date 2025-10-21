package view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import model.Page;

public class PageView extends TextArea {

    private final Page page;

    private static final double PAGE_WIDTH = 794;
    private static final double PAGE_HEIGHT = 1123;
    private static final double PADDING = 10;

    public PageView(Page page) {
        this.page = page;

        setWrapText(true);
        setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
        setMaxSize(PAGE_WIDTH, PAGE_HEIGHT);
        setMinSize(PAGE_WIDTH, PAGE_HEIGHT);
        setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: gray;" +
                "-fx-border-width: 1;" +
                "-fx-font-family: 'Courier New';" +
                "-fx-font-size: 14pt;" +
                "-fx-padding: " + PADDING + ";"
        );
        setText(page.getContent());

        textProperty().addListener((obs, oldText, newText) -> page.setContent(newText));

        VBox.setMargin(this, new Insets(10, 0, 10, 0));
    }

    public Page getPage() {
        return page;
    }
}
