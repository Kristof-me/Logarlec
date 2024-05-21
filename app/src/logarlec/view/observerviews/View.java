package logarlec.view.observerviews;

import javax.swing.JPanel;

/**
 * A JPanel that can be updated when an observed GameObject changes
 */
public abstract class View extends JPanel {
    /**
     * Updates the view to reflect the current state of the observed object
     */
    public abstract void updateView();

    /**
     * Unsubscribes from everything it knows about
     * Returns itself so it can be chained in another removeView call
     * @return the view being removed
     */
    public abstract View removeView();
}
