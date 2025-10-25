package model;

import java.util.ArrayList;
import java.util.List;

/**
 * represent a document with pages in it
 * has always one page.
 * 
 * @author rene-rekowski
 * @version 1.0
 */
public class Document {

	private final List<Page> pages = new ArrayList<>();

	public Document() {
		addPage(); // first page
	}

	public Page addPage() {
		Page newPage = new Page(pages.size() + 1);
		pages.add(newPage);
		return newPage;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void clear() {
		pages.clear();
		addPage();
	}
}
