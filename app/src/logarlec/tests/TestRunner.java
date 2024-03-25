package logarlec.tests;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import logarlec.model.logger.*;
import logarlec.model.actor.*;
import logarlec.model.actor.strategy.*;
import logarlec.model.items.*;
import logarlec.model.items.impl.*;
import logarlec.model.room.*;

public class TestRunner {
    private List<Entry<Test, Method>> cases = new ArrayList<>();
    private static final String EXIT = "q";
    private InputStreamReader isr = new InputStreamReader(System.in);
    private BufferedReader br = new BufferedReader(isr);

    /**
     * Constructor for TestRunner class.
     * 
     * @implNote This function initializes the cases list with all the test cases.
     * @implNote The test cases are sorted by their id, so they can be easily selected.
     */
    public TestRunner() {

        Method[] methods = TestRunner.class.getDeclaredMethods();

        for (Method method : methods) {
            Test annotation = method.getAnnotation(Test.class);

            if (annotation != null) {
                cases.add(Map.entry(annotation, method));
            }
        }

        cases.sort((a, b) -> a.getKey().id() - b.getKey().id());
    }

    /**
     * Prints all the test cases.
     */
    private void printTests() {
        System.out.println("Test cases:");

        for (Entry<Test, Method> entry : cases) {
            Test test = entry.getKey();
            System.out.println(String.format("%d. %s", test.id(), test.name()));
        }
    }

    /**
     * Starts a loop that allows the user to select and run test cases.
     */
    public void startTesting() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String input = "";

        // printing the test cases for the first time
        printTests();

        while (!input.equals(EXIT)) {
            input = readSelection();
            int selected;

            // try parsing the input
            try {
                selected = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                selected = -1;
            }

            // find the selected test case
            int index = findIndex(selected);

            if (index != -1) {
                Method test = cases.get(index).getValue();

                // running the selected test case
                try {
                    test.invoke(this);

                    // printing the test cases after a successful run
                    System.out.print("\nPress enter to continue");
                    br.readLine();

                    printTests();
                } catch (Exception e) {
                    System.err.println("Test failed: " + e.getMessage());
                }
            }
        }

        try {
            br.close();
            isr.close();
        } catch (Exception e) {
            System.err.println("Error closing the input stream: " + e.getMessage());
        }

        System.exit(0);
    }

    /**
     * Finds the index of the test case with the given id.
     * 
     * @param id the id of the test case we are looking for
     * @return
     */
    private int findIndex(int id) {
        int left = 0;
        int right = cases.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int currentId = cases.get(middle).getKey().id();

            if (currentId == id) {
                return middle;
            } else if (middle < id) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    /**
     * Reads the user input.
     * 
     * @return the selected test case id
     */
    private String readSelection() {
        System.out.print("Selected = ");

        String line;
        try {
            line = br.readLine();
        } catch (Exception error) {
            line = EXIT;
        }

        return line;
    }

    // ------------------------------------------------
    // TEST CASES:
    // Add your test cases below. Please use the @Test
    // annotation to mark the test cases.
    // ------------------------------------------------

    @Test(id = 1, name = "Beer Defense")
    private void BeerDefense() {
        Logger.setInstanceNames("room", "room1inventory", "room2", "room2inventory", "door", "student", "studentInventory", "studentDefense", "studentAction", "beerDefense", "professor", "professorInventory", "professorDefense");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student = new Student();
        BeerDefense beerDefense = new BeerDefense(student);
        student.setDefenseStrategy(beerDefense);
        Professor professor = new Professor();
        student.teleport(room1, false);
        professor.teleport(room2, false);

        student.move(door);
    }

    @Test(id = 2, name = "Beer Effect Ticks")
    private void BeerEffectTicks() {
        Logger.setInstanceNames("student", "studentAction", "studentInventory", "studentDefense", "beerDefense", "defaultDefense");
        Student student = new Student();
        BeerDefense beerDefense = new BeerDefense(student);
        student.setDefenseStrategy(beerDefense);

        student.tick();
    }

    @Test(id = 3, name = "Door Appear")
    private void DoorAppear() {
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "student", "studentInventory", "studentDefense", "studentAction", "door");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Student student = new Student();
        Door door = new Door(room1, room2, false);
        student.teleport(room1, false);
        door.hide(1);

        student.move(door);
    }

    @Test(id = 4, name = "Gas Effect Ticks")
    private void GasEffectTicks() {
        Logger.setInstanceNames("room", "roomInventory", "gasEffect");
        Room room = new Room(null);
        GasEffect gasEffect = new GasEffect(null);
        room.addEffect(gasEffect);

        room.tick();
    }

    @Test(id = 5, name = "Professor Moves")
    private void ProfessorMoves() {
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "professor", "professorInventory", "professorDefense", "professorActions", "student", "studentInventory", "studentDefense", "studentAction");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Professor professor = new Professor();
        Student student = new Student();
        professor.teleport(room1, false);
        student.teleport(room2, false);

        professor.move(door);
    }

    @Test(id = 6, name = "Professor Moves to Room with Professor")
    private void ProfessorMovesToRoomWithProfessor() {
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "professor1",  "professor1Inventory", "professor1Defense", "professor1Actions", "professor2", "professor2Inventory", "professor2Defense", "professor2Actions");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();
        professor1.teleport(room1, false);
        professor2.teleport(room2, false);

        professor1.move(door);
    }

    @Test(id = 7, name = "Professor Steps into Wet Room")
    private void ProfessorStepsIntoWetRoom() {
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "professor", "professorInventory", "professorDefense", "professorActions", "wetEffect", "stunnedStep");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Professor professor = new Professor();
        WetEffect wetEffect = new WetEffect();
        room2.addEffect(wetEffect);
        professor.teleport(room1, false);

        professor.move(door);
    }

    @Test(id = 8, name = "Professor Moves to a Gased Room with Gasmask")
    private void ProfessorTeleportsWGasmask() {
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "professor1", "professor1Inventory", "professo1rDefense", "professor1State", "gasEffect", "bestGasMaskFinder", "gasMask");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Professor professor = new Professor();
        GasEffect gasEffect = new GasEffect(null);
        GasMask gasMask = new GasMask();
        room2.addEffect(gasEffect);
        Inventory professorInventory = professor.getInventory();
        professorInventory.addItem(gasMask);
        professor.teleport(room1, false);

        professor.teleport(room1, true);
    }

    @Test(id = 9, name = "Professor Gets Wet")
    private void ProfessorGetsWet(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student", "studentInventory", "studentDefense", "studentActions", "professor", "professorInventory", "professorDefense", "professorActions", "sponge", "stunnedStep");
        Room room1 = new Room(null);
        Door door = new Door(room1, null, false);
        Student student = new Student();
        Professor professor = new Professor();
        student.teleport(room1, false);
        professor.teleport(room1, false);
        Sponge sponge = new Sponge();
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(sponge);

        student.use(sponge);
    }

    @Test(id = 10, name = "Two Rooms merge with one of them having Wet effect and the other with Gas effect")
    private void RoomMergeWithWetAndGas(){
        Logger.setInstanceNames("room1", "room1inventory",  "room2", "room2inventory", "door", "wetEffect", "gasEffect");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        WetEffect wetEffect = new WetEffect();
        GasEffect gasEffect = new GasEffect(null);
        room1.addEffect(wetEffect);
        room2.addEffect(gasEffect);

        room1.merge(room2);
    }

    @Test(id = 11, name = "Room splits with Two Students, without any effects")
    private void RoomSplitNoEffect(){
        Logger.setInstanceNames("oRoom", "oRoomInventory", "student1", "student1Inventory", "student1Defense", "student1State", "student2", "student2Inventory", "student2Defense", "student2State", "nRoom", "nRoomInventory", "door");
        Room oRoom = new Room(null);
        Student student1 = new Student();
        Student student2 = new Student();
        student1.teleport(oRoom, false);
        student2.teleport(oRoom, false);

        oRoom.split();
    }

    @Test(id = 12, name = "Room splits with Two Students, with Wet and Gas Effects")
    private void RoomSplitWithEffects(){
        Logger.setInstanceNames("oRoom", "oRoomInventory", "student1", "student1Inventory", "student1Defense", "student1State", "student2", "student2Inventory", "student2Defense", "student2State", "nRoom", "nRoomInventory", "door", "wetEffect", "gasEffect");
        Room oRoom = new Room(null);
        Student student1 = new Student();
        Student student2 = new Student();
        WetEffect wetEffect = new WetEffect();
        oRoom.addEffect(wetEffect);
        GasEffect gasEffect = new GasEffect(null);
        oRoom.addEffect(gasEffect);
        student1.teleport(oRoom, false);
        student2.teleport(oRoom, false);

        oRoom.split();
    }

    @Test(id = 13, name = "Student Drops Transistor")
    private void StudentDropsTransistor(){
        Logger.setInstanceNames("student", "studentInventory", "studentDefense", "studentAction", "transistorA", "transistorB", "room", "roomInventory", "room2", "room2Inventory");

        Student student = new Student();
        Transistor transistorA = new Transistor();
        Transistor transistorB = new Transistor();
        Room room = new Room(null);
        Room room2 = new Room(null);
        student.teleport(room, false);
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(transistorA);
        studentInventory.addItem(transistorB);

        student.drop(transistorA);
    }

    @Test(id = 14, name = "Student Uses a Camembert with no Gasmask")
    private void StudentGasesOwnRoomNoMask(){
        Logger.setInstanceNames("room1", "room1inventory", "student", "studentInventory", "studentDefense", "studentAction", "camembert", "gasEffect", "bestGasMaskFinder", "stunnedStep");
        Room room1 = new Room(null);
        Student student = new Student();
        Camembert camembert = new Camembert();
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(camembert);
        student.teleport(room1, false);

        student.use(camembert);
    }

    @Test(id = 15, name = "Student Item Pickup")
    private void StudentItemPickup(){
        Logger.setInstanceNames("room", "roomInventory", "student", "studentInventory", "studentDefense", "studentAction", "beer");
        Room room = new Room(null);
        Student student = new Student();
        Beer beer = new Beer();
        room.addItem(beer);

        student.pickUp(beer);
    }

    @Test(id = 16, name = "Student Moves")
    private void StudentMoves(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student", "studentInventory", "studentDefense", "studentAction", "professor", "professorInventory", "professorDefense", "professorAction");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student = new Student();
        Professor professor = new Professor();
        student.teleport(room1, false);
        professor.teleport(room2, false);
        
        student.move(door);
    }

    @Test(id = 17, name = "Student Moves to Empty Wet Room")
    private void StudentMovesToEmptyWetRoom(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student1", "student1Inventory", "student1Defense", "student1State", "wetEffect");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student = new Student();
        WetEffect wetEffect = new WetEffect();
        room2.addEffect(wetEffect);
        student.teleport(room1, false);

        student.move(door);
    }

    @Test(id = 18, name = "Student Moves to Room with Student")
    private void StudentMovesToRoomWithStudent(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student1", "student1Inventory", "student1Defense", "student1State", "student2", "student2Inventory", "student2Defense", "student2State");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student1 = new Student();
        Student student2 = new Student();
        student1.teleport(room1, false);
        student2.teleport(room2, false);

        student1.move(door);
    }

    @Test(id = 19, name = "Student Moves to Room with TVSZ")
    private void StudentMovesToRoomWithTVSZ(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student", "studentInventory", "studentDefense", "studentAction", "professor", "professorInventory", "professorDefense", "professorAction", "tvsz", "tvszFinder");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student = new Student();
        Professor professor = new Professor();
        Tvsz tvsz = new Tvsz();
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(tvsz);
        student.teleport(room1, false);
        professor.teleport(room2, false);

        student.move(door);
    }

    @Test(id = 20, name = "Student Pairs Transistor")
    private void StudentPairsTransistor(){
        Logger.setInstanceNames("student", "studentInventory", "studentDefense", "studentAction", "transistorA", "transistorB", "room", "roomInventory", "room2", "room2Inventory");
        Student student = new Student();
        Transistor transistorA = new Transistor();
        Transistor transistorB = new Transistor();
        Room room = new Room(null);
        Room room2 = new Room(null);
        student.teleport(room, false);
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(transistorA);
        studentInventory.addItem(transistorB);

        student.use(transistorA);
    }

    @Test(id = 21, name = "Rooms merge with one Student in each, enough room for both Students")
    private void StudentsMerge(){
        Logger.setInstanceNames("room1", "room1inventory", "room2", "room2inventory", "door", "student1", "student1Inventory", "student1Defense", "student1State", "student2", "student2Inventory", "student2Defense", "student2State", "beer", "tvsz");
        Room room1 = new Room(null);
        Room room2 = new Room(null);
        Door door = new Door(room1, room2, false);
        Student student1 = new Student();
        Student student2 = new Student();
        Beer beer = new Beer();
        Tvsz tvsz = new Tvsz();
        room1.addItem(beer);
        room2.addItem(tvsz);
        student1.teleport(room1, false);
        student2.teleport(room2, false);

        room1.merge(room2);
    }

    @Test(id = 22, name="Student Uses Beer")
    private void StudentUsesBeer(){
        Logger.setInstanceNames("student", "studentInventory", "studentDefense", "studentAction", "beer", "beerEffect");
        Student student = new Student();
        Beer beer = new Beer();
        student.getInventory().addItem(beer);

        student.use(beer);
    }

    @Test(id = 23, name="Student Uses Cocktail")
    private void StudentUsesCocktail(){
        Logger.setInstanceNames("room", "roomInventory", "student1", "student1Inventory", "student1Action", "student1State", "student2", "student2Inventory", "student2Defense", "student2Action", "cocktail");
        Room room = new Room(null);
        Student student1 = new Student();
        Student student2 = new Student();
        student1.teleport(room, false);
        student2.teleport(room, false);
        Cocktail cocktail = new Cocktail();
        Inventory student1Inventory = student1.getInventory();
        student1Inventory.addItem(cocktail);

        student1.use(cocktail);
    }

    @Test(id = 24, name = "Student Uses Transistor")
    private void StudentUseTransistor(){
        Logger.setInstanceNames("student", "studentInventory", "studentDefense", "studentAction", "transistorA", "transistorB", "room", "roomInventory", "room2", "room2Inventory");
        Student student = new Student();
        Transistor transistorA = new Transistor();
        Transistor transistorB = new Transistor();
        Room room = new Room(null);
        Room room2 = new Room(null);
        student.teleport(room, false);
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(transistorA);
        studentInventory.addItem(transistorB);

        student.use(transistorA);
        student.use(transistorA);
    }

    @Test(id = 25, name = "Use Sponge")
    private void UseSponge(){
        Logger.setInstanceNames("room1", "room1inventory", "student", "studentInventory", "studentDefense", "studentAction", "sponge", "wetEffect");
        Room room1 = new Room(null);
        Student student = new Student();
        Sponge sponge = new Sponge();
        Inventory studentInventory = student.getInventory();
        studentInventory.addItem(sponge);
        student.teleport(room1, false);

        student.use(sponge);
    }
}
