package logarlec.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import logarlec.model.room.*;
import logarlec.view.frames.GameFrame;
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
    private int lastMapChange = 0;

    private ArrayList<Player> students = new ArrayList<>();
    private ArrayList<ProfessorAI> professors = new ArrayList<>();
    private ArrayList<JanitorAI> janitors = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();

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
        anySlideRulePickedUp = false;
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
        // reset();
        //TODO: create profs and janitors based on number of players
        playerIterator = students.iterator();
    }
    
    CountDownLatch turnLatch = new CountDownLatch(2);

    public long getStepCount() {
        System.out.println("latch: " + turnLatch.getCount());
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
            System.out.println("Player " + currentPlayer.getActor().getName() + "'s turn");
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
            System.out.println("Turn ended");
        } else {
            playerIterator = students.iterator();
            aiTurn();
        }
    }

    public void aiTurn() {
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

        //if random is 10 then merge
        //if random is 10 then split
        if (Math.random() * 100 < MERGE_PERCENT) {
            mapManager.mergeRooms();
            lastMapChange = currentTick;
        }
        if (Math.random() * 100 < SLIT_PERCENT) {
            mapManager.splitRoom();
            lastMapChange = currentTick;
        }

        // handling room and actor ticks
        currentTick++; 
        
        for (Room room : rooms) {
            room.tick();
        }
    }

    public int getTick() {
        return currentTick;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public int getLastMapChange() {
        return lastMapChange;
    }
}
