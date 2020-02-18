package Organisms;

public class Homoebolis extends Organism {
    public Homoebolis(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseProperties() {
        this.setStrength(this.getStrength() + 1);
        this.setIntelligence(this.getIntelligence() + 1);
    }
}
