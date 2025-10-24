package view.encoder;

import view.PageManager;
import view.PageView;
import model.Page;

import java.util.List;

/**
 * codied String from Pagemanager 
 * 
 * @author rene-rekowski
 * @version 1.0
 */
public class CoderActions {
	//Ceaser
    public static void encodeCaesar(PageManager pm, int shift) {
        String allText = getAllText(pm);
        String encoded = Caesar.encode(allText, shift);
        setTextToPages(pm, encoded);
    }
    public static void decodeCaesar(PageManager pm, int shift) {
        String allText = getAllText(pm);
        String decoded = Caesar.encode(allText, 26 - (shift % 26));
        setTextToPages(pm, decoded);
    }
    //LZW
    public static void encodeLZW(PageManager pm) {
        String allText = getAllText(pm);
        String encoded = LZW.encode(allText);
        setTextToPages(pm, encoded);
    }
    public static void decodeLZW(PageManager pm) {
    	 String allText = getAllText(pm);
        String decoded = LZW.decode(allText); 
        setTextToPages(pm, decoded);
    }

    //TODO:Maby Pagemanger
    private static String getAllText(PageManager pm) {
        StringBuilder allText = new StringBuilder();
        List<Page> pages = pm.getDocument().getPages();
        for (Page p : pages) {
            allText.append(p.getContent()).append("\n");
        }
        return allText.toString();
    }
    //TODO: maby pageManager
    private static void setTextToPages(PageManager pm, String text) {
        pm.getDocument().clear();
        pm.getPagesContainer().getChildren().clear();

        PageView newPage = pm.addNewPage();
        newPage.setText(text);
    }
}
