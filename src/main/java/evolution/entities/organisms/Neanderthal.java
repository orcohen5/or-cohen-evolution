package evolution.entities.organisms;

public class Neanderthal extends Organism {
    public Neanderthal(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseProperties() {
        this.setStrength(this.getStrength() + 2);
    }
}
