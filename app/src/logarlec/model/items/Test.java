package logarlec.model.items;

public class Test {
    public static void main(String[] args) {
        Base[] items = new Base[] {new A(), new A(), new B()};

        Visitor<A> v = new Visitor<A>();

        for (Base item : items) {
            System.out.print("visit: ");
            item.accept(v);
        }
    }

    abstract static class Base {
        int i = 0;

        public int getI() {
            return i;
        }

        public abstract void accept(Visitor v);
    }

    static class A extends Base {
        public A() {
            this.i = 1;
        }

        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    static class B extends Base {
        public B() {
            this.i = 2;
        }

        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    static class Visitor<T extends Base> {
        public <T extends Base> void visit(T t) {
            t.getI();
        }

        public void visit(A a) {
            System.out.println("A");
        }

        public void visit(B b) {
            System.out.println("B");
        }
    }

    static class Filter extends Visitor {
        public void visit(A a) {
            System.out.println("A");
        }

        public void visit(B b) {
            System.out.println("B");
        }
    }
}
