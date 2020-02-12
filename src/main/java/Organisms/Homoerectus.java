package Organisms;

public class Homoerectus extends Organism {
    public Homoerectus(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseOrganismProperty() {
        this.setTechnologicalMeans(this.getTechnologicalMeans() + 2);
    }
}
