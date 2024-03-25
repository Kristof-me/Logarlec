package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.logger.Logger;
import logarlec.model.items.*;
import java.util.List;
import java.util.ArrayList;

public class GasEffect extends RoomEffect {
    List<ItemFinder<? extends Item>> itemFinders = new ArrayList<>();

    public GasEffect(Integer timeLeft) {
        Logger.preConstructor(this, timeLeft);
        itemFinders.add(new BestGasMaskFinder());
        Logger.postConstructor(this);
    }

    @Override
    public void applyEffect(Professor professor) {
        Logger.preExecute(this, "applyEffect", professor);
        professor.dropAllTo(professor.getLocation());
        Logger.postExecute();
    }

    @Override
    public void applyEffect(Student student) {
        Logger.preExecute(this, "applyEffect", student);
        student.dropAllTo(student.getLocation());
        Logger.postExecute();
    }

    @Override
    public void addEffect(Actor actor) {
        Logger.preExecute(this, "addEffect", actor);
        actor.acceptEffect(this, null);
        Logger.postExecute();
    }
}
