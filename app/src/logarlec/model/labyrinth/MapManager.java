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

    public void hideDoors() {}

    public void merge() {}

    public void split() {}

    public void tick() {}
}
