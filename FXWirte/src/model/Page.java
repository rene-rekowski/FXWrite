package model;

/**
 * represent a page 
 * 
 *  @author rene-rekowski
 *  @version 1.0 
 */
public class Page {
    private String content;
    private final int pageNumber;

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        this.content = "";
    }
    
    /* Getter and Setter */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
