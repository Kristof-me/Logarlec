package logarlec.model;

import java.util.List;
import logarlec.view.observerviews.View;
import java.util.ArrayList;

public abstract class GameObject {
    private List<View> views = new ArrayList<>();

    public void addListener(View view) {
        if(view != null && !views.contains(view)) {
            views.add(view);
        }
    }

    public void removeListener(View view) {
        views.remove(view);
    }

    public void update() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).updateView();   
        }
    }

    public abstract View createOwnView();
}
