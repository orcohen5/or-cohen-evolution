package org.iaf.evolution.entities.organisms;

public class Homoerectus extends Organism {
    public Homoerectus(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseProperties() {
        this.setTechnologicalMeans(this.getTechnologicalMeans() + 2);
    }
}
