package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

	public static void saveToFile(Document document, File file) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (int i = 0; i < document.getPages().size(); i++) {
				writer.write(document.getPages().get(i).getContent());
				if (i < document.getPages().size() - 1) {
					writer.write("\n--- PAGE BREAK ---\n");
				}
			}
		}
	}

	public static void loadFromFile(Document document, File file) throws IOException {
	    document.getPages().clear();
	    StringBuilder sb = new StringBuilder();

	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.equals("--- PAGE BREAK ---")) {
	                Page page = new Page(document.getPages().size() + 1);
	                page.setContent(sb.toString());
	                document.getPages().add(page);
	                sb = new StringBuilder();
	            } else {
	                sb.append(line).append("\n");
	            }
	        }
	    }

	    // if there more text in buffer, add last page
	    if (sb.length() > 0 || document.getPages().isEmpty()) {
	        Page page = new Page(document.getPages().size() + 1);
	        page.setContent(sb.toString());
	        document.getPages().add(page);
	    }
	}

}
