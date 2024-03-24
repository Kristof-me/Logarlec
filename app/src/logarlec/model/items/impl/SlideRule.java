package logarlec.model.items.impl;

import org.w3c.dom.events.Event;
import logarlec.model.actor.Actor;
import logarlec.model.items.Item;
import logarlec.model.items.ItemVisitor;
import logarlec.model.room.Room;

import logarlec.model.logger.*;

public class SlideRule extends Item {

    public SlideRule() {
        Logger.preConstructor(this);
        Logger.postConstructor(this);
    }

    @Override
    public void onPickup(Actor actor) {
        Logger.preExecute(this, "onPickup", actor);
        // signal game over
        System.out.println("Logarlec has been picked up 4!4!!!");
        super.onPickup(actor);
        Logger.postExecute();
    }

    @Override
    public void accept(ItemVisitor visitor) {
        Logger.preExecute(this, "accept", visitor);
        visitor.visit(this);
        Logger.postExecute();
    }

}
