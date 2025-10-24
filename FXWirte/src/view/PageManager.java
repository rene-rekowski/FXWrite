package view;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import model.Document;
import model.Page;

/**
 * Managed all inactions with the pages or the documents
 * 
 * @author rene-rekowkski
 * @version 1.0
 */
public class PageManager {
	private final VBox pagesContainer;
	private final Document document;

	private static final int MAX_CHARS_PER_PAGE = 2600;
	private static final int MAX_LINES_PER_PAGE = 30;
	private static final int MERGE_THRESHOLD = MAX_CHARS_PER_PAGE / 2;

	public PageManager(VBox container, Document document) {
		this.pagesContainer = container;
		this.document = document;
	}

	public PageView addNewPage() {
		Page newPage = document.addPage();
		PageView view = new PageView(newPage);
		attachTextListener(view, newPage);
		pagesContainer.getChildren().add(view);
		return view;
	}

	/**
	 * Refreshes the page view to reflect the current document state. rebuild all
	 * page view from the documents page.
	 */
	public void refreshView() {
		pagesContainer.getChildren().clear();
		for (Page page : document.getPages()) {
			PageView view = new PageView(page);
			view.setText(page.getContent());
			attachTextListener(view, page);
			pagesContainer.getChildren().add(view);
		}
		if (document.getPages().isEmpty()) {
			addNewPage();
		} else {
			distributeText();
		}
	}

	/**
	 * when ever the text change -> update pageModel redistibutes and merge pages
	 * 
	 * @param view
	 * @param model
	 */
	private void attachTextListener(PageView view, Page model) {
		view.textProperty().addListener((obs, oldText, newText) -> {
			model.setContent(newText);
			Platform.runLater(() -> {
				int oldCaretPosition = view.getCaretPosition();
				int pageIndex = pagesContainer.getChildren().indexOf(view);

				distributeText();
				handleMerge();

				if (pageIndex < pagesContainer.getChildren().size()) {
					PageView newPageView = (PageView) pagesContainer.getChildren().get(pageIndex);
					newPageView.requestFocus();
					int newCaretPosition = Math.min(oldCaretPosition, newPageView.getText().length());
					newPageView.positionCaret(newCaretPosition);
				}
			});
		});
	}

	/**
	 * Distributes the entire document text across pages. collect all page content,
	 * and merge it into a sinle string and spilts that into pages
	 */
	private void distributeText() {
		// 🔹 Alle Texte zusammenführen – aber ohne zusätzliches \n am Ende jeder Seite
		StringBuilder fullText = new StringBuilder();
		for (Page page : document.getPages()) {
			String content = page.getContent();
			if (!content.isEmpty()) {
				// Entferne nur, wenn letztes Zeichen KEIN Leerzeichen ist, bevor du eins einfügst
				if (fullText.length() > 0 && !fullText.toString().endsWith(" ") && !content.startsWith(" ")) {
					fullText.append(" "); // Trennt Seiten mit Leerzeichen, aber nicht doppelt
				}
				fullText.append(content);
			}
		}

		document.clear();
		pagesContainer.getChildren().clear();

		String textToDistribute = fullText.toString();

		if (textToDistribute.trim().isEmpty()) {
			addNewPage();
			return;
		}

		while (!textToDistribute.isEmpty()) {
			int splitIndex = findSplitPoint(textToDistribute);
			String pageText = textToDistribute.substring(0, splitIndex);
			String nextText = textToDistribute.substring(splitIndex).replaceAll("^[\\s\\n]+", "");
			textToDistribute = nextText;

			Page newPage = document.addPage();
			newPage.setContent(pageText);

			PageView newPageView = new PageView(newPage);
			newPageView.setText(pageText);
			attachTextListener(newPageView, newPage);
			pagesContainer.getChildren().add(newPageView);
		}

		if (document.getPages().isEmpty()) {
			addNewPage();
		}
	}

	/**
	 * find the point to split string into to pages
	 * 
	 * @param text
	 * @return
	 */
	private int findSplitPoint(String text) {
		int limit = MAX_CHARS_PER_PAGE;
		if (text.length() <= limit) {
			return text.length();
		}

		int lastSpace = text.lastIndexOf(' ', limit);
		int lastNewline = text.lastIndexOf('\n', limit);

		int split = Math.max(lastSpace, lastNewline);

		if (split != -1) {
			return split;
		}

		// If no space before the limit, look for the next one after
		int nextSpace = text.indexOf(' ', limit);
		if (nextSpace != -1) {
			return nextSpace;
		}

		return limit;
	}

	/**
	 * check if the next page can merge into one
	 */
	private void handleMerge() {
		int index = 0;
		while (index < pagesContainer.getChildren().size() - 1) {
			PageView pageView = (PageView) pagesContainer.getChildren().get(index);
			PageView nextView = (PageView) pagesContainer.getChildren().get(index + 1);
			Page pageModel = document.getPages().get(index);

			String currentText = pageView.getText();
			String nextText = nextView.getText();

			if (nextText.isBlank() || (currentText.length() + nextText.length() < MAX_CHARS_PER_PAGE
					&& nextText.length() < MERGE_THRESHOLD)) {
				String merged = (currentText.trim() + " " + nextText.trim()).replaceAll("\\s+", " ").trim();
				pageModel.setContent(merged);
				pageView.setText(merged);

				pagesContainer.getChildren().remove(nextView);
				document.getPages().remove(index + 1);

				pageView.requestFocus();
				pageView.positionCaret(pageView.getText().length());

				continue;
			}
			index++;
		}
		// Remove next page if empty 
		if (pagesContainer.getChildren().size() > 1
				&& ((PageView) pagesContainer.getChildren().get(pagesContainer.getChildren().size() - 1)).getText()
						.isBlank()) {
			pagesContainer.getChildren().remove(pagesContainer.getChildren().size() - 1);
			document.getPages().remove(document.getPages().size() - 1);
		}
	}
	
	//getter and setter
	public VBox getPagesContainer() {
	    return pagesContainer;
	}
	
	public Document getDocument() {
		return this.document;
	}

}
