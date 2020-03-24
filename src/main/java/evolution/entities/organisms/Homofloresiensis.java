package evolution.entities.organisms;

public class Homofloresiensis extends Organism {
    public Homofloresiensis(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseProperties() {
        this.setIntelligence(this.getIntelligence() + 1);
        this.setTechnologicalMeans(this.getTechnologicalMeans() + 1);
    }
}
