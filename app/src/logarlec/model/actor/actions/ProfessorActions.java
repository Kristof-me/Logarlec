public class ProfessorActions extends ActionState {
    @Override
    public void attack() {
        // Implementation goes here
    }

    @Override
    public boolean tick() {
        // Implementation goes here
        return false;
    }

    @Override
    public ActionState setNextState(ActionState state) {
        // Implementation goes here
        return null;
    }

    @Override
    public boolean move(Door door) {
        // Implementation goes here
        return false;
    }

    @Override
    public void use(Item item) {
        // Implementation goes here
    }

    @Override
    public boolean pickUp(Item item) {
        // Implementation goes here
        return false;
    }

    @Override
    public void drop(Item item) {
        // Implementation goes here
    }
}