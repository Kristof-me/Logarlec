package logarlec.model.labyrinth;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály felelős a labirintus szobáinak, valamint azok ajtajainak kezeléséért
 */
public class Labyrinth {
    private List<Room> rooms = new ArrayList<>();

    public Labyrinth() {
        generate();
    }

    /**
     * Generál egy labirintust
     */
    private void generate() {}

    /**
     * Véletlenszerűen eltűntet egy vagy több ajtót, de a szobák összefüggőek maradnak
     */
    public void hideDoors() {
        // get the doors like this
        rooms.get(0).getDoors().get(0);
        // calculate the doors to disappear, but all the rooms should be still in one
        // component
        rooms.get(0).hideDoor(0);
    }

    /**
     * Összeolvaszt két szobát
     */
    public void merge(Room a, Room b) {}

    /**
     * Kettéválaszt egy szobát
     */
    public void split(Room a) {}

    /**
     * Eltelik egy időegység
     */
    public void tick() {
        for (Room room : rooms) {
            // do something
        }
    }
}
