package logarlec.model.labyrinth;

import java.util.ArrayList;
import java.util.List;
import logarlec.model.characters.Inventory;
import logarlec.model.characters.Professor;
import logarlec.model.characters.Student;
import logarlec.model.items.Item;

public class Room implements IHasLocation {
    private List<Door> doors;
    private Inventory items;
    private List<Student> students;
    private List<Professor> professors;
    private int capacity;
    // private List<Pair<Effects, int>> effects;

    public Room(int capacity) {
        this.capacity = capacity;
        items = new Inventory();
        doors = new ArrayList<>();
    }

    public void droppedItem(Item item) {
        items.addItem(item);
    }

    public List<Item> getItems() {
        return items.getItems();
    }

    public Item takeItem(Item item) {
        if (items.removeItem(item)) {
            return item;
        }

        return null;
    }

    public int itemCount() {
        return items.size();
    }

    public int actorCount() {
        return professors.size() + students.size();
    }

    public void Tick() {}

    public boolean Move(Professor professor, boolean forced) {
        if (actorCount() == capacity && !forced) {
            return false;
        }
        //Ha meg forced volt akkor meg adjuk hozzá és öljük meg xd

        professors.add(professor);
        return true;
    }

    public boolean Move(Student student, boolean forced) {
        if (actorCount() == capacity) {
            return false;
        }

        students.add(student);
        return true;
    }

    @Override
    public Room getLocation() {
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void moveOut(Professor professor) {
        professors.remove(professor);
    }

    public void moveOut(Student student) {
        students.remove(student);
    }
}

