package test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Page;

class PageTest {

    @Test
    void testPageContentAndNumber() {
        Page p = new Page(5);
        assertEquals(5, p.getPageNumber());

        p.setContent("Hallo");
        assertEquals("Hallo", p.getContent());
    }

    @Test
    void testEmptyContentByDefault() {
        Page p = new Page(1);
        assertEquals("", p.getContent());
    }
}
