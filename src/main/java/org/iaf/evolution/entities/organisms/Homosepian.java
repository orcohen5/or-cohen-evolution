package org.iaf.evolution.entities.organisms;

public class Homosepian extends Organism {
    public Homosepian(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    public void increaseProperties() {
        this.setIntelligence(this.getIntelligence() + 2);
    }
}
