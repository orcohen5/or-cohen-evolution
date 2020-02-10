package Organisms;

public class Homoebolis extends Tribe {
    public Homoebolis(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Homoebolis";
    }

    public void increasePropertyOneToFiveTimes() {
        this.setStrength(this.getStrength()+1);
        this.setIntelligence(this.getIntelligence()+1);
    }
}
