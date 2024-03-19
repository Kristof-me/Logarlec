public interface IActions {
    void attack();
    boolean move(Door door);
    void use(Item item);
    boolean pickUp(Item item);
    void drop(Item item);
}