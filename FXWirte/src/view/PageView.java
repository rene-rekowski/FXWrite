package view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import model.Page;

/**
 * View of the page 
 * 
 * @author rene-rekowski
 * @version 1.0
 */
public class PageView extends TextArea {

    private final Page page;

    private static final double PAGE_WIDTH = 794;
    private static final double PAGE_HEIGHT = 1123;
    private static final double PADDING = 10;

    public PageView(Page page) {
        this.page = page;
        
        setWrapText(true);
        setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
        
        setText(page.getContent());

        textProperty().addListener((obs, oldText, newText) -> page.setContent(newText));
        
        getStyleClass().add("page-view");  
        setText(page.getContent());
        VBox.setMargin(this, new Insets(10, 0, 10, 0));
    }
    
    // getter and setter
    public Page getPage() {
        return page;
    }
}
