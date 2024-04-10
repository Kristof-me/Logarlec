package logarlec.control;

public class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private GameManager() {}
    
    public boolean isGameOver() {
        // TODO: implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void reset() {
        // TODO: implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
