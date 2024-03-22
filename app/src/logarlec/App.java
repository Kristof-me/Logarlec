
package logarlec;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Student;
import logarlec.model.proxy.InstanceName;
import logarlec.model.room.Room;

public class App {
    public static void main(String[] args) {

        Room room1 = new Room();
        Room room2 = new Room();

        Student peti = new Student();
        room1.enter(peti, true);
        room2.enter(peti, true);


    }

    public static class A<T> {

        public T get(T param) {
            return param;
        }
    }
}
