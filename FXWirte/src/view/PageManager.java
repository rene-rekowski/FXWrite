package view;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import model.Document;
import model.Page;

//TODO: sauber durchgehen

public class PageManager {

    private final VBox pagesContainer;
    private final Document document;

    // Grenzen für den Seiteninhalt
    private static final int MAX_CHARS_PER_PAGE = 80; // maximale Zeichen pro Seite
    private static final int MAX_LINES_PER_PAGE = 38;   // optionale Begrenzung
    private static final int MERGE_THRESHOLD = MAX_CHARS_PER_PAGE/2;    // wenn weniger als diese Zeichen, kann zusammengeführt werden

    public PageManager(VBox container, Document document) {
        this.pagesContainer = container;
        this.document = document;
    }

    /** Erstellt eine neue Seite im Model und fügt sie als PageView hinzu. */
    public PageView addNewPage() {
        Page newPage = document.addPage();
        PageView view = new PageView(newPage);
        attachTextListener(view, newPage);
        pagesContainer.getChildren().add(view);
        return view;
    }

    /** Baut die UI basierend auf dem Document neu auf. */
    public void refreshView() {
        pagesContainer.getChildren().clear();
        for (Page page : document.getPages()) {
            PageView view = new PageView(page);
            view.setText(page.getContent());
            attachTextListener(view, page);
            pagesContainer.getChildren().add(view);
        }
    }

    /** Fügt Listener hinzu, um Änderungen im Text zu überwachen. */
    private void attachTextListener(PageView view, Page model) {
        view.textProperty().addListener((obs, oldText, newText) -> {
            model.setContent(newText);
            Platform.runLater(() -> {
                handleOverflow(view, model);
                handleMerge(view, model);
            });
        });
    }

    /** Prüft, ob der Text einer Seite zu lang ist, und verschiebt ggf. den Rest auf eine neue. */
    private void handleOverflow(PageView pageView, Page pageModel) {
        String text = pageView.getText();
        int lineCount = text.split("\n").length;

        if (text.length() > MAX_CHARS_PER_PAGE || lineCount > MAX_LINES_PER_PAGE) {
            int splitIndex = findSplitPoint(text);

            String currentText = text.substring(0, splitIndex);
            String overflowText = text.substring(splitIndex).trim();

            // Aktuelle Seite kürzen
            pageView.setText(currentText);
            pageModel.setContent(currentText);

            // Neue Seite mit Resttext hinzufügen
            Page nextPage = new Page(document.getPages().size() + 1);
            nextPage.setContent(overflowText);
            document.getPages().add(nextPage);

            PageView nextPageView = new PageView(nextPage);
            nextPageView.setText(overflowText);
            attachTextListener(nextPageView, nextPage);

            // Neue Seite direkt nach der aktuellen einfügen
            int currentIndex = pagesContainer.getChildren().indexOf(pageView);
            pagesContainer.getChildren().add(currentIndex + 1, nextPageView);

            nextPageView.requestFocus();
            nextPageView.positionCaret(overflowText.length());
        }
    }

    /** Prüft, ob die aktuelle Seite leer oder unterfüllt ist und ggf. mit der vorherigen Seite zusammengeführt werden kann. */
    private void handleMerge(PageView pageView, Page pageModel) {
        int index = pagesContainer.getChildren().indexOf(pageView);

        // Falls es eine vorherige Seite gibt
        if (index > 0) {
            PageView previousView = (PageView) pagesContainer.getChildren().get(index - 1);
            Page previousPage = document.getPages().get(index - 1);

            String currentText = pageView.getText();
            String previousText = previousView.getText();

            // Bedingung: Diese Seite ist leer oder sehr kurz, vorherige Seite hat noch Platz
            if ((currentText.isBlank() || currentText.length() < MERGE_THRESHOLD)
                    && previousText.length() < MAX_CHARS_PER_PAGE - MERGE_THRESHOLD) {

                // Texte zusammenführen
                String merged = previousText + "\n" + currentText;
                previousPage.setContent(merged);
                previousView.setText(merged);

                // Diese Seite entfernen
                pagesContainer.getChildren().remove(pageView);
                document.getPages().remove(pageModel);

                // Fokus auf vorherige Seite setzen
                previousView.requestFocus();
                previousView.positionCaret(previousView.getText().length());
            }
        }

        // Wenn die letzte Seite komplett leer ist (z. B. nach dem Löschen)
        if (pageView.getText().isBlank() && pagesContainer.getChildren().size() > 1) {
            pagesContainer.getChildren().remove(pageView);
            document.getPages().remove(pageModel);
        }
    }

    /** Findet einen sinnvollen Trennpunkt für den Seitenumbruch (Leerzeichen oder Zeilenende). */
    private int findSplitPoint(String text) {
        int limit = Math.min(text.length(), MAX_CHARS_PER_PAGE);
        int lastSpace = text.lastIndexOf(' ', limit);
        int lastNewline = text.lastIndexOf('\n', limit);

        int split = Math.max(lastSpace, lastNewline);
        if (split == -1) split = limit; // kein Leerzeichen gefunden
        return split;
    }

    public Document getDocument() {
        return document;
    }
}
