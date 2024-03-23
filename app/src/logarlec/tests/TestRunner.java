package logarlec.tests;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import logarlec.model.actor.*;
import logarlec.model.items.*;
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

    @Test(id = 1, name = "Example test")
    private void test1() {
        System.out.println("foo");
    }

    @Test(id = 2, name = "My test")
    private void funkyTestName() {
        System.out.println("bar");
    }

    // TODO Add more test cases here
}
