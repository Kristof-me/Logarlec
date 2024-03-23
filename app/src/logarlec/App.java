
package logarlec;

import logarlec.tests.TestRunner;

public class App {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.startTesting();

    }

    public static class A<T> {

        public T get(T param) {
            return param;
        }
    }
}
