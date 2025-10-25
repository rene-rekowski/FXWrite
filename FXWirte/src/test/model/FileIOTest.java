package test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Document;
import model.FileIO;

public class FileIOTest {
	
	@Test
	void testSaveAndLoadFile() throws IOException {
	    Document doc = new Document();
	    doc.getPages().get(0).setContent("Hallo Welt!");

	    File tempFile = File.createTempFile("testDoc", ".txt");
	    FileIO.saveToFile(doc, tempFile);

	    Document loaded = new Document();
	    FileIO.loadFromFile(loaded, tempFile);

	    assertEquals("Hallo Welt!\n", loaded.getPages().get(0).getContent());
	}
	
	@Test
	void testSaveAndLoadMultiplePages() throws IOException {
	    Document doc = new Document();
	    doc.getPages().get(0).setContent("Page 1");
	    doc.addPage().setContent("Page 2");

	    File tempFile = File.createTempFile("multiDoc", ".txt");
	    FileIO.saveToFile(doc, tempFile);

	    Document loaded = new Document();
	    FileIO.loadFromFile(loaded, tempFile);

	    assertEquals(2, loaded.getPages().size());
	    assertTrue(loaded.getPages().get(0).getContent().contains("Page 1"));
	}
	
	@Test
	void testLoadEmptyFile() throws IOException {
	    File tempFile = File.createTempFile("empty", ".txt");
	    Document doc = new Document();
	    FileIO.loadFromFile(doc, tempFile);

	    assertEquals(1, doc.getPages().size(), "Empty file should create at least one page");
	}



}

