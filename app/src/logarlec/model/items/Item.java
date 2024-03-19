public abstract class Item {
    protected int usesLeft;

    public abstract void use(Actor invoker);
    public abstract int getUsesLeft();
    public abstract void onPickup(Actor actor);
    public abstract void onDrop(Room room);
    public abstract void accept(ItemVisitor visitor);
}