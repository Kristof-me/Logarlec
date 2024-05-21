package logarlec.control.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logarlec.model.actor.Actor;
import logarlec.model.actor.actions.IActions;
import logarlec.model.items.Item;
import logarlec.model.room.Door;

public abstract class Controller<T extends Actor> implements IActions {
    protected T actor;
    
    protected Controller(T actor) {
        this.actor = actor;
    }

    public abstract Actor getActor();

    public abstract void takeTurn();

    public void attack() { }

    public boolean move(Door door) {
        return actor.move(door);
    }

    public void use(Item item) {
        actor.use(item);
    }

    public boolean pickUp(Item item) {
        return actor.pickUp(item);
    }

    public void drop(Item item) {
        actor.drop(item);
    }

    public class DijkstraResult {
        public ArrayList<Integer> distances;
        public Integer[] reachedFrom;

        public DijkstraResult(ArrayList<Integer> distances, Integer[] reachedFrom){
            this.distances = distances;
            this.reachedFrom = reachedFrom;
        }
    }

    // do a dijkstra on the matrix
    protected DijkstraResult getDistances(ArrayList<ArrayList<Integer>> mtx, int start) {
        int size = mtx.size();
        if (start < 0 || start >= size) {
            throw new IllegalArgumentException("Start index out of bounds. start: " + start);
        }
    
        ArrayList<Integer> distances = new ArrayList<>(Collections.nCopies(size, Integer.MAX_VALUE));
        distances.set(start, 0);
        Integer[] reachedFrom = new Integer[size];
        Set<Integer> visited = new HashSet<>();
    
        while (visited.size() < size) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
    
            for (int i = 0; i < size; i++) {
                if (!visited.contains(i) && distances.get(i) < min) {
                    min = distances.get(i);
                    minIndex = i;
                }
            }
    
            // Break if all remaining nodes are unreachable
            if (minIndex == -1) {
                break;
            }
    
            visited.add(minIndex);
    
            for (int i = 0; i < size; i++) {
                if (mtx.get(minIndex).get(i) != 0 && !visited.contains(i)) {
                    int newDist = distances.get(minIndex) + mtx.get(minIndex).get(i);
                    if (newDist < distances.get(i)) {
                        distances.set(i, newDist);
                        reachedFrom[i] = minIndex;
                    }
                }
            }
        }
    
        return new DijkstraResult(distances, reachedFrom);
    }

    protected int firstStepTo(int from, int to, Integer[] reachedFrom) {
        if (reachedFrom[to] == null) {
            throw new IllegalArgumentException("No path exists from " + from + " to " + to);
        }
        
        Integer current = to;
        while (reachedFrom[current] != null && !reachedFrom[current].equals(from)) {
            current = reachedFrom[current];
            if (current == null) {
                throw new IllegalArgumentException("No path exists from " + from + " to " + to);
            }
        }
        
        if (reachedFrom[current] == null) {
            throw new IllegalArgumentException("No path exists from " + from + " to " + to);
        }
        
        return current;
    }
}
