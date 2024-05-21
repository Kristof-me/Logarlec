package logarlec.control.controller;

import java.util.ArrayList;
import java.util.Random;

import logarlec.model.actor.Professor;
import logarlec.model.room.Door;

public class ProfessorAI extends Controller<Professor> {    
    private Random random = new Random();
    
    public ProfessorAI() {
        super(new Professor());
    }

    @Override
    public void takeTurn() {
        Door nextDoor = getDoor();
        if(nextDoor == null){
            // In this case the professor just skips the turn because there is a problem with the conncectivity of the rooms
            System.err.println("No door leads to that room! Professor could not move!");
            return;
        }

        boolean success = move(nextDoor);
    }

    @Override
    public Professor getActor() {
        return actor;
    }
    
    /*
     * Get a random door from the professors room
     */
    private Door getDoor(){
        ArrayList<Door> allDoors = actor.getLocation().getDoors();
        if (allDoors.isEmpty()) {
            return null;
        }
        return allDoors.get(random.nextInt(allDoors.size()));
    }
}
