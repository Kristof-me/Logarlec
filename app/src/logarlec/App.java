
package logarlec;

import java.util.List;
import java.util.ArrayList;

import logarlec.model.characters.*;
import logarlec.model.characters.visitor.AttackVisitor;
import logarlec.model.items.*;
import logarlec.model.labyrinth.Room;

public class App {
    public static void main(String[] args) throws Exception {
        Room IB028_ = new Room(10);
        Professor Szeszler = new Professor(IB028_);

        Student EgysegsugaruHallgato = new Student(IB028_);
        Student EltevedtVillanyos = new Student(IB028_);

        AttackVisitor Zh = new AttackVisitor();

        for (Actor actor : IB028_.actors) {
            actor.accepts(Zh);
        }
    }
}
