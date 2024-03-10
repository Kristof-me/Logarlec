
package logarlec;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<A> lista = new ArrayList<A>();

        lista.add(new A<Integer>());
        lista.add(new A<String>());

        var a = lista.get(0).get(1);
        var b = lista.get(1).get("a");

        System.out.println(a.getClass());
        System.out.println(b.getClass());

    }

    public static class A<T> {

        public T get(T param) {
            return param;
        }
    }
}
