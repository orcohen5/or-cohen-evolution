package Organisms;

public class Homoerectus extends Tribe {
    public Homoerectus(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Homoerectus";
    }

    public void increasePropertyOneToFiveTimes() {
        this.setTechnologicalMeans(this.getTechnologicalMeans()+2);
    }
}
