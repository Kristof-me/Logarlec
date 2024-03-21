
package logarlec;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.actor.Student;
import logarlec.model.room.Room;

public class App {
    public static void main(String[] args) {

        Room room = new Room();
        room.enter(new Student(), true);


    }

    public static class A<T> {

        public T get(T param) {
            return param;
        }
    }
}
