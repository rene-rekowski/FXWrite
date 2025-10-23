package view;

import javafx.application.Platform;
import model.Document;
import model.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SampleLoader {

    private static Document documentRef;
    private static PageManager pageManagerRef;

    public static void init(Document document, PageManager pageManager) {
        documentRef = document;
        pageManagerRef = pageManager;
    }

    public static void loadSampleFromResource(String resourcePath) {
        if (documentRef == null || pageManagerRef == null) return;

        String text = loadTextFile(resourcePath);
        if (text == null) {
            System.err.println("⚠️ Datei konnte nicht geladen werden: " + resourcePath);
            return;
        }

        documentRef.clear();
        Page page = documentRef.getPages().get(0);
        page.setContent(text);

        Platform.runLater(pageManagerRef::refreshView);
    }

    private static String loadTextFile(String resourcePath) {
        try (InputStream is = SampleLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) return null;

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
