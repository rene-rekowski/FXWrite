package test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.Document;

public class DocumentTest {
	
	@Test
	void testAddAndClearPages() {
	    Document doc = new Document();
	    assertEquals(1, doc.getPages().size());

	    doc.addPage();
	    assertEquals(2, doc.getPages().size());

	    doc.clear();
	    assertEquals(1, doc.getPages().size());
	}

}
