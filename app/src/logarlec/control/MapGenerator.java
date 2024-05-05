package logarlec.control;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

public class MapGenerator {
    private static int height;
    private static int width;
    private final static double excessDoorChance = 0.5;
    private final static double oneWayDoorChance = 0.5;
    private final static double itemSpawnChance = 0.5;
    private final static double fakeItemChance = 0.5;
    private final static Constructor[] constructors = new Constructor[] {
            SlideRule.class.getConstructors()[0],
            AirFreshener.class.getConstructors()[0],
            Beer.class.getConstructors()[0],
            Camembert.class.getConstructors()[0],
            Cocktail.class.getConstructors()[0],
            GasMask.class.getConstructors()[0],
            Sponge.class.getConstructors()[0],
            Transistor.class.getConstructors()[0],
            Tvsz.class.getConstructors()[0],
    };
    public static List<Room> generateMap(int height, int width, Room solution) {
        MapGenerator.height = height;
        MapGenerator.width = width;
        double roomHeight = 1.0/height;
        double roomWidth = 1.0/width;
        Room[][] map = new Room[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Room(10, i*roomWidth, j*roomHeight);
            }
        }
        
        boolean[][] visited = new boolean[width][height];
        BFS(map, visited, 0, 0);
        addItems(map);
        solution = spawnSliderule(map);
        DrawMap(map);

        List<Room> rooms = new ArrayList<Room>(height * width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rooms.add(map[i][j]);
            }
        }
        return rooms;
    }

    private static void BFS(Room[][] map, boolean[][] visited, int x, int y) {
        visited[x][y] = true;
        //0 = right, 1 = left, 2 = up, 3 = down
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
            
            //safeguard
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                Room next = map[nx][ny];
                if (!visited[nx][ny]) {
                    new Door(map[x][y], next, false);
                    BFS(map, visited, nx, ny);
                    if (Math.random() < excessDoorChance) {
                        addExcessDoors(map, x, y);
                    }
                }
            }
        }
    }

    private static void addExcessDoors(Room[][] map, int x, int y) {
        List<Room> adjacent = new ArrayList();
        if (x > 0) {
            adjacent.add(map[x-1][y]);
        }
        if (y > 0) {
            adjacent.add(map[x][y-1]);
        }
        if (x < width - 1) {
            adjacent.add(map[x+1][y]);
        }
        if (y < height - 1) {
            adjacent.add(map[x][y+1]);
        }
        Collections.shuffle(adjacent);
        for (Room room : adjacent) {
            if (!hasWay(map[x][y], room)) {
                new Door(map[x][y], room, Math.random() < oneWayDoorChance);
                return;
            }
        }
    }

    private static boolean hasWay(Room a, Room b) {
        for (Door door : a.getDoors()) {
            if (door.leadsTo(a) == b) {
                return true;
            }
        }
        return false;
    }

    private static void addItems(Room[][] map) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean spawned = false;
                do {
                    spawned = false;
                    if (Math.random() < itemSpawnChance) {
                        Item toAdd;
                        try {
                            int roll = (int) (Math.random() * constructors.length);
                            toAdd = (Item) constructors[roll].newInstance();
                            if (Math.random() < fakeItemChance || roll == 0) {
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

    private static Room spawnSliderule(Room[][] map) {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        map[x][y].addItem(new SlideRule());
        return map[x][y];
    }

    //TODO: remove before prod
    private static void DrawMap(Room[][] map) {
        for(int i = 0; i < width; i++) {
            System.out.print(" _ ");
        }
        System.out.println();
        for(int i = 0; i <height; i++) {
            for(int j = 0; j < width; j++) {
                boolean leads = false;
                if (j > 0) {
                    for (Door door : map[j][i].getDoors()) {
                        if (door.leadsTo(map[j][i]) == map[j-1][i]) {
                            leads = true;
                            break;
                        }
                    }
                    System.out.print(leads ? " " : "|");
                }
                else {
                    System.out.print("|");
                }
                leads = false;
                if (i < height -1) {
                    for (Door door : map[j][i].getDoors()) {
                        if (door.leadsTo(map[j][i]) == map[j][i+1]) {
                            leads = true;
                            break;
                        }
                    }
                    System.out.print(leads ? " " : "_");
                }
                else {
                    System.out.print("_");
                }
                leads = false;
                if (j < width -1) {
                    for (Door door : map[j][i].getDoors()) {
                        if (door.leadsTo(map[j][i]) == map[j + 1][i]) {
                            leads = true;
                            break;
                        }
                    }
                    System.out.print(leads ? " " : "|");
                }
                else {
                    System.out.println("|");
                }
            }
        }
    }
}
