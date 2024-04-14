package logarlec.control;

import java.util.ArrayList;
import java.util.List;

import logarlec.model.actor.*;
import logarlec.model.room.*;

public class GameManager {
    private static GameManager instance;
    private boolean randomness = false;

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

    public Door addDoor(Room from, Room to, boolean isOnwWay){
        Door door = new Door(from, to, isOnwWay);
        from.getDoors().add(door);
        to.getDoors().add(door);
        return door;
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
}
