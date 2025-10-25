package test.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Document;
import service.CoderService;

class CoderServiceTest {

    @Test
    void testEncodeCaesarWithoutJavaFX() {
        Document doc = new Document();
        doc.getPages().get(0).setContent("ABC");

        // Direkter Aufruf (du kannst die Methode anpassen oder Helper hinzufügen)
        CoderService.encodeCaesar(doc, 3);

        String result = doc.getPages().get(0).getContent();
        assertEquals("DEF", result);
    }

    @Test
    void testEncodeAndDecodeLZWWithoutJavaFX() {
        Document doc = new Document();
        doc.getPages().get(0).setContent("Hallo Hallo");

        CoderService.encodeLZW(doc);
        String encoded = doc.getPages().get(0).getContent();
        assertTrue(encoded.matches("\\d+(,\\d+)*"), "Should look like numeric codes");

        CoderService.decodeLZW(doc);
        String decoded = doc.getPages().get(0).getContent();
        assertEquals("Hallo Hallo", decoded);
    }
}
