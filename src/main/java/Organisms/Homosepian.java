package Organisms;

public class Homosepian extends Tribe {
    public Homosepian(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Homosepian";
    }

    public void increasePropertyOneToFiveTimes() {
        this.setIntelligence(this.getIntelligence()+2);
    }
}
