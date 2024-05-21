package logarlec.control;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logarlec.App;
import logarlec.model.items.Item;
import logarlec.model.items.impl.AirFreshener;
import logarlec.model.items.impl.Beer;
import logarlec.model.items.impl.Camembert;
import logarlec.model.items.impl.Cocktail;
import logarlec.model.items.impl.FakeItem;
import logarlec.model.items.impl.GasMask;
import logarlec.model.items.impl.SlideRule;
import logarlec.model.items.impl.Sponge;
import logarlec.model.items.impl.Transistor;
import logarlec.model.items.impl.Tvsz;
import logarlec.model.room.Door;
import logarlec.model.room.Room;
import java.util.Random;
public class MapManager {
    private static Random random = new Random();
    private int height;
    private int width;
    private static final double excessDoorChance = 0.1;
    private static final double oneWayDoorChance = 0.5;
    private static final double itemSpawnChance = 0.6;
    private static final double fakeItemChance = 0.15;  
    private static final Constructor[] constructors = new Constructor[] {SlideRule.class.getConstructors()[0],
            AirFreshener.class.getConstructors()[0], Beer.class.getConstructors()[0], Camembert.class.getConstructors()[0],
            Cocktail.class.getConstructors()[0], GasMask.class.getConstructors()[0], Sponge.class.getConstructors()[0],
            Transistor.class.getConstructors()[0], Tvsz.class.getConstructors()[0],};
    private List<Room> rooms;
    private Room solution;

    /*
     * Getter method for the rooms.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /*
     * Constructor for the MapManager class.
     */
    public MapManager(int h, int w) {
        height = h;
        width = w;
        rooms = generateMap(height, width);
    }

    /*
     * Merges two random rooms.
     */
    public void mergeRooms() {
        int room1 = random.nextInt(rooms.size());
        Room r1 = rooms.get(room1);
        int door = random.nextInt(r1.getDoors().size());
        Room r2 = r1.getDoors().get(door).leadsTo(r1);
        if (r2 == null) {
            return;
        }
        // r2 will die here and will be merged into r1
        r2.merge(r1);
        rooms.remove(r2);
    }

    public void splitRoom() {
        int room = random.nextInt(rooms.size());
        Room r = rooms.get(room);
        rooms.add(r.split());
    }

    /*
     * Hides a random door in a random room.
     */
    public void hideDoor() {
        if(rooms.isEmpty())
            return;
        int room = random.nextInt(rooms.size());
        Room r = rooms.get(room);
        if (r.getDoors().isEmpty())
            return;    
        int door = random.nextInt(r.getDoors().size());
        Door d = r.getDoors().get(door);
        if (d.getRemainingInvisibility() > 0) return;
        d.hide(random.nextInt(4));
    }

    /*
     * Main method to generate the map.
     */
    private List<Room> generateMap(int height, int width) {
        this.height = height;
        this.width = width;
        Room[][] map = new Room[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Room(10);
            }
        }

        boolean[][] visited = new boolean[width][height];
        BFS(map, visited, 0, 0);
        addItems(map);
        solution = spawnSliderule(map);
        // DrawMap(map);

        List<Room> rooms = new ArrayList<Room>(height * width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rooms.add(map[i][j]);
            }
        }
        return rooms;
    }

    /*
     * Breadth-first to assure that all the rooms will be connected on the map.
     */
    private void BFS(Room[][] map, boolean[][] visited, int x, int y) {
        visited[x][y] = true;
        // 0 = right, 1 = left, 2 = up, 3 = down
        List<Integer> dirs = new ArrayList<Integer>(4);
        for (int k = 0; k < 4; k++) {
            dirs.add(k);
        }
        Collections.shuffle(dirs);

        for (int dir : dirs) {
            int nx = x;
            int ny = y;
            switch (dir) {
                case 0:
                    nx = x + 1;
                    break;
                case 1:
                    nx = x - 1;
                    break;
                case 2:
                    ny = y + 1;
                    break;
                case 3:
                    ny = y - 1;
                    break;
            }

            // safeguard
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                Room next = map[nx][ny];
                if (!visited[nx][ny]) {
                    new Door(map[x][y], next, false);
                    BFS(map, visited, nx, ny);
                    if (random.nextDouble() < excessDoorChance) {
                        addExcessDoors(map, x, y);
                    }
                }
            }
        }
    }

    /*
     * Adds excess doors to a room.
     */
    private void addExcessDoors(Room[][] map, int x, int y) {
        List<Room> adjacent = new ArrayList();
        if (x > 0) {
            adjacent.add(map[x - 1][y]);
        }
        if (y > 0) {
            adjacent.add(map[x][y - 1]);
        }
        if (x < width - 1) {
            adjacent.add(map[x + 1][y]);
        }
        if (y < height - 1) {
            adjacent.add(map[x][y + 1]);
        }
        Collections.shuffle(adjacent);
        for (Room room : adjacent) {
            if (!hasWay(map[x][y], room)) {
                new Door(map[x][y], room, random.nextDouble() < oneWayDoorChance);
                return;
            }
        }
    }

    /*
     * Checks if there is a way between two rooms.
     */
    private boolean hasWay(Room a, Room b) {
        for (Door door : a.getDoors()) {
            if (door.leadsTo(a) == b) {
                return true;
            }
        }
        return false;
    }

    /*
     * Adding random items to all of the rooms.
     */
    private void addItems(Room[][] map) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean spawned = false;
                do {
                    spawned = false;
                    if (random.nextDouble() < itemSpawnChance) {
                        Item toAdd;
                        try {
                            int roll = (int) (random.nextDouble() * constructors.length);
                            toAdd = (Item) constructors[roll].newInstance();
                            if (random.nextDouble() < fakeItemChance || roll == 0) {
                                map[i][j].addItem(new FakeItem(toAdd));
                            } else {
                                map[i][j].addItem(toAdd);
                            }
                            spawned = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (spawned);
            }
        }
    }

    /*
     * Spawning the sliderule in a random room.
     */
    private Room spawnSliderule(Room[][] map) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        map[x][y].addItem(new SlideRule());
        return map[x][y];
    }

    /*
     * Getter method for the solution room.
     */
    public Room getSolution() {
        return solution;
    }

    /*
     * @return the first random room witch has no actors in it.
     */
    public Room getRandomEmptyRoom() {
        Room room = null;
        do {
            room = rooms.get(random.nextInt(rooms.size()));
        } while (room == null || !room.getActors().isEmpty());
        return room;
    }
}
