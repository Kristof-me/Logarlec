package logarlec.control;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import logarlec.model.room.*;
import logarlec.view.frames.GameEndFrame;
import logarlec.view.frames.GameFrame;
import logarlec.view.frames.MenuFrame;
import logarlec.control.controller.JanitorAI;
import logarlec.control.controller.Player;
import logarlec.control.controller.ProfessorAI;
import java.util.Iterator;
import java.util.Random;
public class GameManager {
    private static Random random = new Random();
    private static final int MERGE_PERCENT = 10;
    private static final int SLIT_PERCENT = 10;
    private static final float PROFESSOR_TO_STUDENT_RATIO = 0.5f;
    private static final float JANITOR_TO_STUDENT_RATIO = 0.3f;
    private static final int MAX_TURNS = 24;
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
        return anySlideRulePickedUp;
    }
    
    public boolean isGameOver() {
        if(isWon()){
            return true;
        }

        if(currentTick > MAX_TURNS) {
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
        Room.nextId = 1111;
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

        currentPlayer.skipTurn();
    }
    
    public void startGame(MenuFrame menuFrame) {
        menuFrame.setVisible(false);

        // calculating the map size (about 280 students max)
        float x = students.size();
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
            
            int n = (random.nextInt(professorNames.length));
            professor.getActor().setName(professorNames[n]);
            Room t = mapManager.getRandomEmptyRoom();
            professor.getActor().teleport(t, false);
        }

        String[] janitorNames = { "Mária", "Erzsébet", "Katalin", "Éva", "Ilona", "László", "István", "József", "Zoltán", "János" };
        String[] janitorLocations = { "Library", "Hallways", "Cafeteria", "Gym", "Storage Rooms", "Courtyard", "Rooftop" };

        for (int i = 0; i < janitorCount; i++) {
            JanitorAI janitor = new JanitorAI();
            addJanitor(janitor);

            int n = random.nextInt(janitorNames.length);
            int l = random.nextInt(janitorLocations.length);

            Room t = mapManager.getRandomEmptyRoom();
            janitor.getActor().setName(String.format("%s - Janitor of the %s", janitorNames[n], janitorLocations[l]));
            janitor.getActor().teleport(t, false);
        }

        playerIterator = students.iterator();
        GameFrame gameFrame = GameFrame.getInstance();
        new Thread(() -> gameFrame.setVisible(true)).start();
        while (!isGameOver()) {
            playTurn();
        }

        GameEndFrame gameEndFrame = new GameEndFrame(menuFrame, isWon());
        gameEndFrame.setVisible(true);
        gameFrame.setVisible(false);
    }
    
    CountDownLatch turnLatch = new CountDownLatch(2);

    public long getStepCount() {
        return turnLatch.getCount();
    }

    public void takeStep() {
        turnLatch.countDown();
    }

    private Player currentPlayer;
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public synchronized void playTurn() {
        if (playerIterator.hasNext()) {
            currentPlayer = playerIterator.next();
            currentPlayer.getActor().tick();

            if (currentPlayer.getActor().isAlive()) {
                currentPlayer.takeTurn();
                try {
                    turnLatch.await();
                    turnLatch = new CountDownLatch(2);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // counting the number of students alive
            List<Player> playedLastRound = new ArrayList<>();

            for (Player player : students) {
                if (player.getActor().isAlive()) {
                    playedLastRound.add(player);
                }
            }

            // the ai turn
            aiTurn();
            
            // checking for deaths
            String died = "";
            
            for (Player player : playedLastRound) {
                if (!player.getActor().isAlive()) {
                    died.concat(player.getActor().getName() + "(" + player.getActor().getLocation().getName() + ")\n");
                }
            }

            if (died.length() > 0) {
                died = died.substring(0, died.length() - 2);
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "The following students died\n: " + died, "Death", JOptionPane.PLAIN_MESSAGE);
            }

            playerIterator = students.iterator();
            turnLatch = new CountDownLatch(2);
        }
    }

    public synchronized void aiTurn() {
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
        if (random.nextInt(101) < MERGE_PERCENT) {
            mapManager.mergeRooms();
            lastMapChange = currentTick;
        }
        if (random.nextInt(101) < SLIT_PERCENT) {
            mapManager.splitRoom();
            lastMapChange = currentTick;
        }
        int hideCount = random.nextInt(3);
        for (int i = 0; i < hideCount; i++) {
            mapManager.hideDoor();
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
