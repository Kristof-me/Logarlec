package logarlec.control;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import logarlec.model.items.impl.SlideRule;
import logarlec.model.room.*;
import logarlec.view.frames.GameEndFrame;
import logarlec.view.frames.GameFrame;
import logarlec.view.frames.MenuFrame;
import logarlec.control.controller.JanitorAI;
import logarlec.control.controller.Player;
import logarlec.control.controller.ProfessorAI;
import java.util.Iterator;

public class GameManager {
    private final static int MERGE_PERCENT = 10;
    private final static int SLIT_PERCENT = 10;
    private final static float PROFESSOR_TO_STUDENT_RATIO = 0.5f;
    private final static float JANITOR_TO_STUDENT_RATIO = 0.3f;
    private final static int MAX_TURNS = 24;
    private static GameManager instance;
    private MapManager mapManager;
    private int currentTick = 0;
    private int lastMapChange = 0;

    private ArrayList<Player> students = new ArrayList<>();
    private ArrayList<ProfessorAI> professors = new ArrayList<>();
    private ArrayList<JanitorAI> janitors = new ArrayList<>();
    private List<Room> rooms;

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
        // TODO check for MAX_TURNS
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
        mapManager = null;
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

    public void slideRulePickedUp() {
        anySlideRulePickedUp = true;
    }
    
    public void startGame(MenuFrame menuFrame) {
        menuFrame.setVisible(false);

        // calculating the map size (about 280 students max)
        float x = (float) students.size();
        int r = 8; // approx. 8 rooms per student
        int s = (int) Math.floor(Math.sqrt(r * x) + 1); // map side

        mapManager = new MapManager(s, s);
        rooms = mapManager.getRooms();

        //  placing actors
        int professorCount = (int) Math.ceil(students.size() * PROFESSOR_TO_STUDENT_RATIO);
        int janitorCount = (int) Math.ceil(students.size() * JANITOR_TO_STUDENT_RATIO);

        for (int i = 0; i < students.size(); i++) {
            students.get(i).getActor().teleport(mapManager.getRandomEmptyRoom(), false);
        }

        String[] professorNames = { "Lorosz Ászló", "Sajdos Gándor", "Zenesóczky Boltán", "Jima Csudit", "Deszlér Szávid", "Vimon Silmos", "Pekler Éter", "Partinek Méter", "Gaszály Nábor", "Kartlein Hároly" };
        
        for (int i = 0; i < professorCount; i++) {
            ProfessorAI professor = new ProfessorAI();
            addProfessor(professor);

            int n = (int) (Math.random() * professorNames.length);
            professor.getActor().setName(professorNames[n]);
            Room t = mapManager.getRandomEmptyRoom();
            System.out.println("Professor " + professor.getActor().getName() + " in " + t.getId());
            professor.getActor().teleport(t, false);
        }

        String[] janitorNames = { "Mária", "Erzsébet", "Katalin", "Éva", "Ilona", "László", "István", "József", "Zoltán", "János" };
        String[] janitorLocations = { "Library", "Hallways", "Cafeteria", "Gym", "Storage Rooms", "Courtyard", "Rooftop" };

        for (int i = 0; i < janitorCount; i++) {
            JanitorAI janitor = new JanitorAI();
            addJanitor(janitor);

            int n = (int) (Math.random() * janitorNames.length), l = (int) (Math.random() * janitorLocations.length);

            janitor.getActor().setName(String.format("%s - Janitor of the %s", janitorNames[n], janitorLocations[l]));
            janitor.getActor().teleport(mapManager.getRandomEmptyRoom(), false);
        }

        Room room = mapManager.getRandomEmptyRoom();
        room.addItem(new SlideRule());
        System.out.println("Sliderule in " + room.getId());

        playerIterator = students.iterator();
        GameFrame gameFrame = GameFrame.getInstance();
        new Thread(() -> {
            gameFrame.setVisible(true);
        }).start();
        while (!isGameOver()) {
            playTurn();
            System.out.println("Tick " + currentTick);
        }

        System.out.println("Game over");
        GameEndFrame gameEndFrame = new GameEndFrame(menuFrame, isWon());
        gameEndFrame.setVisible(true);
        gameFrame.setVisible(false);
    }
    
    CountDownLatch turnLatch = new CountDownLatch(2);

    public long getStepCount() {
        return turnLatch.getCount();
    }

    public void takeStep() {
        System.out.println("Step taken");
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
        } else {
            playerIterator = students.iterator();
            turnLatch = new CountDownLatch(2);
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

    public List<Room> getRooms(){
        return rooms;
    }

    public int getLastMapChange() {
        return lastMapChange;
    }
}
