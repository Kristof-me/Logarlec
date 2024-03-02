package logarlec.model.labyrinth;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private List<Room> rooms;

    public MapManager() {
        rooms = new ArrayList<>();
        generate();
    }

    private void generate() {}

    public void hideDoors() {
        // get the doors like this
        rooms.get(0).getDoors().get(0);
        // calculate the doors to disappear, but all the rooms should be still in one component
        Integer[] idxs = {0, 1};
        rooms.get(0).hideDoors(idxs);
    }

    public void merge() {}

    public void split() {}

    public void tick() {}
}
