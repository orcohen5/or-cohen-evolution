package Organisms;

public class Homosepian extends Organism {
    public Homosepian(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseOrganismProperty() {
        this.setIntelligence(this.getIntelligence() + 2);
    }
}
