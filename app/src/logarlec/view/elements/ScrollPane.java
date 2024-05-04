package logarlec.view.elements;

import javax.swing.JScrollPane;
import java.awt.Component;

/**
 * The ScrollPane class is a custom JScrollPane class that is uses a custom scroll bar.
 * 
 * @see JScrollPane
 */
public class ScrollPane extends JScrollPane {
    public ScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        this.getVerticalScrollBar().setUI(new ScrollUI());
        this.getHorizontalScrollBar().setUI(new ScrollUI());
    }

    public ScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }

    public ScrollPane(Component view) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public ScrollPane() {
        this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
