package Organisms;

public class Neanderthal extends Tribe {
    public Neanderthal(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Neanderthal";
    }

    public void increasePropertyOneToFiveTimes() {
        this.setStrength(this.getStrength()+2);
    }
}
