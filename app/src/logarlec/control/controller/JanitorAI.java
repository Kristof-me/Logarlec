package logarlec.control.controller;

import java.util.ArrayList;
import java.util.List;

import logarlec.App;
import logarlec.control.GameManager;
import logarlec.model.actor.Janitor;
import logarlec.model.room.Door;
import logarlec.model.room.GasEffect;
import logarlec.model.room.Room;

public class JanitorAI extends Controller<Janitor> {
    private int lastRefresh = -1;
    Room targetedRoom = null;
    Integer[] reachedFrom;
    
    public JanitorAI() {
        super(new Janitor());
    }
    
    @Override
    public void takeTurn() {
        refreshTargetedRoom();
        Room nextRoom = getNextRoom();
        Door nextDoor = getDoor(nextRoom);

        boolean success = move(nextDoor);
        System.err.println("Janitor move success: " + success);
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
        if(targetedRoom == null || !actor.getLocation().equals(targetedRoom) 
            || actor.getLocation().getRoomEffects().stream().anyMatch(effect -> effect instanceof GasEffect)
            || GameManager.getInstance().getLastMapChange() - lastRefresh > 0){
            
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
            
            DijkstraResult result = getDistances(mtx, rooms.indexOf(actor.getLocation()));
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
            if(targetedRoom == null){
                targetedRoom = rooms.get((int)(App.random.nextDouble() * rooms.size()));
            }
            
            lastRefresh = GameManager.getInstance().getTick();
        }
    }

    Room getNextRoom() {
        List<Room> rooms = GameManager.getInstance().getRooms();
        int index = rooms.indexOf(actor.getLocation());
        int nextIndex = firstStepTo(index, rooms.indexOf(targetedRoom), reachedFrom);
        return rooms.get(nextIndex);
    }

    Door getDoor(Room toRoom) {
        return actor.getLocation().getDoors().stream().filter(door -> door.leadsTo(actor.getLocation()).equals(toRoom)).findFirst().get();
    }
}
