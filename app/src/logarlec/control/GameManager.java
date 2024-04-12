package logarlec.control;

import java.util.ArrayList;
import java.util.List;

import logarlec.model.actor.*;
import logarlec.model.room.*;

public class GameManager {
    private static GameManager instance;
    private boolean randomness = true;

    private List<Student> students = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Janitor> janitors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private GameManager() {}

    public boolean isWon() {
        // TODO implement this method
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
    public boolean isGameOver() {
        for (Student player : students) {
            if(player.isAlive()) {
                return false;
            }
        }

        return true;

        // TODO implement this method
    }

    public void reset() {
        students.clear();
        professors.clear();
        janitors.clear();
        rooms.clear();

        // TODO: implement this method
    }

    public void AddStudent(Student student) {
        students.add(student);
    }
    
    public void AddProfessor(Professor professor) {
        professors.add(professor);
    }

    public void AddJanitor(Janitor janitor) {
        janitors.add(janitor);
    }

    public void AddRoom(Room room) {
        rooms.add(room);
    }

    public void setRandomness(boolean value) {
        randomness = value;
    }

    public boolean isRandom() {
        return randomness;
    }
}
