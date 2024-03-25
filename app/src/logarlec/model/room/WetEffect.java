package logarlec.model.room;

import logarlec.model.actor.Actor;
import logarlec.model.actor.Professor;
import logarlec.model.actor.Student;
import logarlec.model.actor.actions.StunnedStep;
import logarlec.model.logger.Logger;

public class WetEffect extends RoomEffect {
    public WetEffect(){
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    @Override
    public void applyEffect(Professor professor) {
        Logger.preExecute(this, "applyEffect", professor);
        StunnedStep step = new StunnedStep(professor);
        professor.setActionState(step);
        Logger.postExecute();
    }

    @Override
    public void applyEffect(Student student) {
        Logger.preExecute(this, "applyEffect", student);
        Logger.postExecute();

    }

    @Override
    public void addEffect(Actor actor) {
        Logger.preExecute(this, "addEffect", actor);
        actor.acceptEffect(this, null);
        Logger.postExecute();
    }
}
