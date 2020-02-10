package Organisms;

public class Homofloresiensis extends Tribe {
    public Homofloresiensis(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Homofloresiensis";
    }

    public void increasePropertyOneToFiveTimes() {
        this.setIntelligence(this.getIntelligence()+1);
        this.setTechnologicalMeans(this.getTechnologicalMeans()+1);
    }
}
