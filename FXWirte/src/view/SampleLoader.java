package view;

import model.Document;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * SampleLoader managed to load out the ressours-file sample texts
 */
public class SampleLoader {
    private static Document document;
    private static PageManager pageManager;

    public static void init(Document doc, PageManager pm) {
        document = doc;
        pageManager = pm;
    }

    public static void loadSampleFromResource(String resourcePath) {
        try (InputStream is = SampleLoader.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // check if page exist
            //TODO: Maby not nessary
            if (document.getPages().isEmpty()) {
                pageManager.addNewPage();
            }

            // Den Text in die erste PageView laden, um den Listener zu triggern
            PageView firstPageView = (PageView) pageManager.getPagesContainer().getChildren().get(0);
            firstPageView.setText(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
