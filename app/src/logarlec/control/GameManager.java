package logarlec.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import logarlec.model.room.*;
import logarlec.control.controller.JanitorAI;
import logarlec.control.controller.Player;
import logarlec.control.controller.ProfessorAI;
import java.util.Iterator;
public class GameManager {
    private final int MERGE_PERCENT = 10;
    private final int SLIT_PERCENT = 10;
    private static GameManager instance;
    private MapManager mapManager;
    private int currentTick = 0;

    private ArrayList<Player> students = new ArrayList<>();
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

    private Iterator<Player> playerIterator;

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
        playerIterator = students.iterator();
        mapManager = new MapManager(50, 50);
        currentTick = 0;
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

    public void slideRulePickedUp() {
        anySlideRulePickedUp = true;
    }
    
    public void startGame() {
        playerIterator = students.iterator();
    }
    
    CountDownLatch turnLatch = new CountDownLatch(2);

    public long getStepCount() {
        return turnLatch.getCount();
    }

    public void takeStep(){
        turnLatch.countDown();
    }
    private Player currentPlayer;
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public void playTurn() {
        if (playerIterator.hasNext()) {
            currentPlayer = playerIterator.next();
            currentPlayer.getActor().tick();
            if (currentPlayer.getActor().isAlive()){
                currentPlayer.takeTurn();
                try {
                    turnLatch.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            turnLatch = new CountDownLatch(2);
        } else {
            playerIterator = students.iterator();
            aiTurn();
        }
    }


    public void aiTurn() {
        // handling room and actor ticks
        currentTick++; 
        
        for (Room room : rooms) {
            room.tick();
        }
        //if random is 10 then merge
        //if random is 10 then split
        if (Math.random() * 100 < MERGE_PERCENT) {
            mapManager.mergeRooms();
        }
        if (Math.random() * 100 < SLIT_PERCENT) {
            mapManager.splitRoom();
        }

        for (ProfessorAI professor : professors) {
            professor.getActor().tick();
            professor.takeTurn();
            professor.takeTurn();
        }

        for (JanitorAI janitor : janitors) {
            janitor.getActor().tick();
            janitor.takeTurn();
            janitor.takeTurn();
        }
    }

    public int getTick() {
        return currentTick;
    }    
}
