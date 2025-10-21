package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

	// --- Speichern & Laden ---

	public void saveToFile(File file) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < pages.size(); i++) {
				writer.write(pages.get(i).getContent());
				if (i < pages.size() - 1) {
					writer.write("\n--- PAGE BREAK ---\n");
				}
			}
		}
	}

	public void loadFromFile(File file) throws IOException {
		pages.clear();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("--- PAGE BREAK ---")) {
					Page page = new Page(pages.size() + 1);
					page.setContent(sb.toString());
					pages.add(page);
					sb = new StringBuilder();
				} else {
					sb.append(line).append("\n");
				}
			}
		}

		// Letzte Seite hinzufügen
		if (sb.length() > 0) {
			Page page = new Page(pages.size() + 1);
			page.setContent(sb.toString());
			pages.add(page);
		}

		if (pages.isEmpty()) {
			addPage();
		}
	}
}
