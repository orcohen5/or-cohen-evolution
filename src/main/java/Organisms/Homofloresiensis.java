package Organisms;

public class Homofloresiensis extends Organism {
    public Homofloresiensis(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseOrganismProperty() {
        this.setIntelligence(this.getIntelligence() + 1);
        this.setTechnologicalMeans(this.getTechnologicalMeans() + 1);
    }
}
