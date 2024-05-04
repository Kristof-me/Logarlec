package logarlec.control;

import java.util.ArrayList;
import java.util.List;

import logarlec.model.room.*;
import logarlec.control.controller.Controller;
import logarlec.control.controller.JanitorAI;
import logarlec.control.controller.Player;
import logarlec.control.controller.ProfessorAI;
import logarlec.model.actor.*;

public class GameManager {
    private static GameManager instance;
    private boolean randomness = false;
    private int currentTick = 0;

    private List<Player> students = new ArrayList<>();
    private List<ProfessorAI> professors = new ArrayList<>();
    private List<JanitorAI> janitors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    private boolean anySlideRulePickedUp = false;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private GameManager() { reset(); }

    public boolean isWon() {
        if(anySlideRulePickedUp) {
            return true;
        }

        return false;
    }
    
    public boolean isGameOver() {
        if(isWon()){
            return true;
        }

        for (Player player : students) {
            if(player.getActor().isAlive()) {
                return false;
            }
        }

        return true;
    }

    public void reset() {
        students.clear();
        professors.clear();
        janitors.clear();
        rooms.clear();

        anySlideRulePickedUp = false;
        setRandomness(false);
    }

    public void addPlayer(Player player) {
        students.add(player);
    }
    public void addProfessor(ProfessorAI professor) {
        professors.add(professor);
    }
    public void addJanitor(JanitorAI janitor) {
        janitors.add(janitor);
    }
    // public void addStudent(Student student) {
    //     students.add(student);
    // }
    
    // public void addProfessor(Professor professor) {
    //     professors.add(professor);
    // }

    // public void addJanitor(Janitor janitor) {
    //     janitors.add(janitor);
    // }
    public Controller getControllerForActor(Actor actor){
        for (Player player : students) {
            if (player.getActor() == actor) return player;
        }
        for (ProfessorAI professor : professors) {
            if (professor.getActor() == actor) return professor;
        }
        for (JanitorAI janitor : janitors) {
            if (janitor.getActor() == actor) return janitor;
        }
        return null;
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void mergeRooms(Room room1, Room room2){
        room2.merge(room1);
        rooms.remove(room2);
    }

    public Room splitRoom(Room room){
        Room newRoom = room.split();
        addRoom(newRoom);
        return newRoom;
    }

    public void setRandomness(boolean value) {
        randomness = value;
    }

    public boolean isRandom() {
        return randomness;
    }

    public void slideRulePickedUp() {
        anySlideRulePickedUp = true;
    }

    public void simulateTurn() {
        // handling room and actor ticks
        currentTick++; 
        
        for (Room room : rooms) {
            room.tick();
        }

        for (ProfessorAI professor : professors) {
            professor.getActor().tick();
            professor.getActor().tick();
            professor.prepareTurn();
            professor.prepareTurn();
        }

        for (JanitorAI janitor : janitors) {
            janitor.getActor().tick();
            janitor.getActor().tick();
            janitor.prepareTurn();
            janitor.prepareTurn();
        }

        for (Player player : students) {
            player.getActor().tick();
            player.getActor().tick();
            player.prepareTurn();
            player.prepareTurn();
        }
    }

    public int getTick() {
        return currentTick;
    }    
}
