package logarlec.control.controller;

import java.util.ArrayList;

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
    protected DijkstraResult getDistances(ArrayList<ArrayList<Integer>> mtx, int start){
        ArrayList<Integer> distances = new ArrayList<>(mtx.size());
        Integer[] reachedFrom = new Integer[mtx.size()];
    
        for(int i = 0; i < mtx.size(); i++){
            if(i == start){
                distances.add(0);
            } 
            else if(mtx.get(start).get(i) != 0){
                distances.add(mtx.get(start).get(i));
            } 
            else {
                distances.add(Integer.MAX_VALUE);
            }
        }
        
        ArrayList<Integer> visited = new ArrayList<>();

        int prevMinIndex = start;

        while(visited.size() < mtx.size()){
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for(int i = 0; i < distances.size(); i++){
                if(!visited.contains(i) && distances.get(i) < min){
                    min = distances.get(i);
                    minIndex = i;
                }
            }

            visited.add(minIndex);
            reachedFrom[minIndex] = prevMinIndex;
            prevMinIndex = minIndex;

            for(int i = 0; i < mtx.get(minIndex).size(); i++){
                if(mtx.get(minIndex).get(i) != 0 && !visited.contains(i)){
                    if(distances.get(i) > distances.get(minIndex) + mtx.get(minIndex).get(i)){
                        distances.set(i, distances.get(minIndex) + mtx.get(minIndex).get(i));
                    }
                }
            }
        }

        return new DijkstraResult(distances, reachedFrom);
    }

    protected int firstStepTo(int from, int to, Integer[] reachedFrom){
        int current = to;
        while(reachedFrom[current] != from){
            current = reachedFrom[current];
        }
        return current;
    }
}
