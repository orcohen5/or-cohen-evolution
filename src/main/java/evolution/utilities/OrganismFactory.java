package evolution.utilities;

import evolution.entities.organisms.*;

public class OrganismFactory {

    public Organism getOrganism(String name, String type) {
        Organism organism = null;

        if ("Homoebolis".equals(type)) {
            organism = new Homoebolis(name);
        } else if ("Homoerectus".equals(type)) {
            organism = new Homoerectus(name);
        } else if ("Homofloresiensis".equals(type)) {
            organism = new Homofloresiensis(name);
        } else if ("Homosepian".equals(type)) {
            organism = new Homosepian(name);
        } else if ("Neanderthal".equals(type)) {
            organism = new Neanderthal(name);
        }

        return organism;
    }
}
