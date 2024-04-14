package logarlec.control;

import java.util.ArrayList;
import java.util.List;

import logarlec.model.actor.*;
import logarlec.model.room.*;

public class GameManager {
    private static GameManager instance;
    private boolean randomness = false;
    private int currentTick = 0;

    private List<Student> students = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Janitor> janitors = new ArrayList<>();
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

        for (Student player : students) {
            if(player.isAlive()) {
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

    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void addJanitor(Janitor janitor) {
        janitors.add(janitor);
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

        for (Professor professor : professors) {
            professor.tick();
            professor.tick();
            // TODO ai move
        }

        for (Janitor janitor : janitors) {
            janitor.tick();
            janitor.tick();
            // TODO ai move
        }

        for (Student student : students) {
            student.tick();
            // TODO give them control
            student.tick();
            // TODO give them control again 
            // cause everyone has 2 actions
        }
    }

    public int getTick() {
        return currentTick;
    }    
}
