package logarlec.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import logarlec.model.room.Door;
import logarlec.model.room.Room;

public class MapManager {
    private int height;
    private int width;
    private List<Room> rooms;
    public List<Room> getRooms() {
        return rooms;
    }
    public MapManager(int h, int w){
        height = h;
        width = w;
        rooms = generateMap(height, width);
    }

    public void mergeRooms(){
        int room1 = (int)(Math.random() * rooms.size());
        int room2 = (int)(Math.random() * rooms.size());
        while(room1 == room2){
            room2 = (int)(Math.random() * rooms.size());
        }
        Room r1 = rooms.get(room1);
        Room r2 = rooms.get(room2);
        r2.merge(r1);
        rooms.remove(r1);
    }

    public void splitRoom(){
        int room = (int)(Math.random() * rooms.size());
        Room r = rooms.get(room);
        rooms.add(r.split());
    }


    private  List<Room> generateMap(int height, int width) {
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
        //DrawMap(map);

        List<Room> rooms = new ArrayList<Room>(height * width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rooms.add(map[i][j]);
            }
        }
        return rooms;
    }

    private void BFS(Room[][] map, boolean[][] visited, int x, int y) {
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
                }
            }
        }
    }

    //TODO: remove before prod
    // private void DrawMap(Room[][] map) {
    //     for(int i = 0; i < width; i++) {
    //         System.out.print(" _ ");
    //     }
    //     System.out.println();
    //     for(int i = 0; i <height; i++) {
    //         for(int j = 0; j < width; j++) {
    //             boolean leads = false;
    //             if (j > 0) {
    //                 for (Door door : map[j][i].getDoors()) {
    //                     if (door.leadsTo(map[j][i]) == map[j-1][i]) {
    //                         leads = true;
    //                         break;
    //                     }
    //                 }
    //                 System.out.print(leads ? " " : "|");
    //             }
    //             else {
    //                 System.out.print("|");
    //             }
    //             leads = false;
    //             if (i < height -1) {
    //                 for (Door door : map[j][i].getDoors()) {
    //                     if (door.leadsTo(map[j][i]) == map[j][i+1]) {
    //                         leads = true;
    //                         break;
    //                     }
    //                 }
    //                 System.out.print(leads ? " " : "_");
    //             }
    //             else {
    //                 System.out.print("_");
    //             }
    //             leads = false;
    //             if (j < width -1) {
    //                 for (Door door : map[j][i].getDoors()) {
    //                     if (door.leadsTo(map[j][i]) == map[j + 1][i]) {
    //                         leads = true;
    //                         break;
    //                     }
    //                 }
    //                 System.out.print(leads ? " " : "|");
    //             }
    //             else {
    //                 System.out.println("|");
    //             }
    //         }
    //     }
    // }
}
