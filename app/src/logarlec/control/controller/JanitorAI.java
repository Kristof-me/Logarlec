package logarlec.control.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import logarlec.control.GameManager;
import logarlec.model.actor.Janitor;
import logarlec.model.room.Door;
import logarlec.model.room.GasEffect;
import logarlec.model.room.Room;
import java.util.*;

public class JanitorAI extends Controller<Janitor> {
    private static Random random = new Random();
    private int lastRefresh = -1;
    Room targetedRoom = null;
    Integer[] reachedFrom;
    
    /*
     * Constructor for the JanitorAI class.
     */
    public JanitorAI() {
        super(new Janitor());
    }
    
    /*
     * One turn of the Janitor, the Janitor moves closer to the targetedRoom.
     */
    @Override
    public void takeTurn() {
        refreshTargetedRoom();
        Room nextRoom = getNextRoom();
        Door nextDoor = getDoor(nextRoom);
        if(nextDoor == null){
            // In this case tha janitor just skips the turn because there is a problem with the conncectivity of the rooms
            System.err.println("No door leads to that room! Janitor could not move!");
            return;
        }

        boolean success = move(nextDoor);
        System.err.println("Janitor move success: " + success + " to room: " + nextRoom.getName());
        System.err.println("Janitor is headed to: " + targetedRoom.getName());
    }

    @Override
    public Janitor getActor() {
        return actor;
    }
    
    /**
     * Refreshes the targeted room if needed
     */
    private void refreshTargetedRoom(){
        // check if we acutally need to refresh the targeted room
        if(targetedRoom == null || actor.getLocation().equals(targetedRoom) 
            || !targetedRoom.getRoomEffects().stream().anyMatch(effect -> effect instanceof GasEffect)
            || GameManager.getInstance().getLastMapChange() - lastRefresh > 0
            || GameManager.getInstance().getRooms().contains(targetedRoom) == false)
            {
            
            List<Room> rooms = GameManager.getInstance().getRooms();

            // create the adjacency matrix for the dijkstra input
            ArrayList<ArrayList<Integer>> mtx = new ArrayList<>();

            for(int i = 0; i < rooms.size(); i++) {
                ArrayList<Integer> row = new ArrayList<>();
                for(int j = 0; j < rooms.size(); j++){
                    if(i == j){
                        row.add(0);
                    } else {
                        Room from = rooms.get(i);
                        Room to = rooms.get(j);
                        row.add(from.getDoors().stream().anyMatch(door -> (door.leadsTo(from) != null && door.leadsTo(from).equals(to))) ? 1 : Integer.MAX_VALUE);
                    }
                }
                mtx.add(row);
            }
            
            int index = rooms.indexOf(actor.getLocation());
            if(index == -1){
                System.err.println("Actor is not in any room!");
            }
            DijkstraResult result = getDistances(mtx, index);
            reachedFrom = result.reachedFrom;
            
            // select the closest room with gas effect
            int minDistance = Integer.MAX_VALUE;
            for(int i = 0; i < rooms.size(); i++){
                Room room = rooms.get(i);
                if(room.getRoomEffects().stream().anyMatch(effect -> effect instanceof GasEffect) && result.distances.get(i) < minDistance) {
                    minDistance = result.distances.get(i);
                    targetedRoom = room;
                }
            }

            // if there is no room with gas effect, target a random room
            while(targetedRoom == null || targetedRoom.equals(actor.getLocation())){
                targetedRoom = rooms.get(random.nextInt(rooms.size()));
            }
            
            lastRefresh = GameManager.getInstance().getTick();
        }
    }

    /*
     * @return the next room the actor need to move into in order to reach the targeted room
     */
    Room getNextRoom() {
        List<Room> rooms = GameManager.getInstance().getRooms();
        int index = rooms.indexOf(actor.getLocation());
        if(index == -1 || rooms.indexOf(targetedRoom) == -1){
            System.err.println("Actor is not in any room!");
        }
        int nextIndex = firstStepTo(index, rooms.indexOf(targetedRoom), reachedFrom);

        return rooms.get(nextIndex);
    }

    /*
     * Returns the door that leads to the given room.
     */
    Door getDoor(Room toRoom) {
        return actor.getLocation().getDoors().stream()
            .filter(door -> Optional.ofNullable(door.leadsTo(actor.getLocation()))
                                    .map(room -> room.equals(toRoom))
                                    .orElse(false))
            .findFirst()
            .orElse(null); // or throw an exception, depending on your needs
    }
}
