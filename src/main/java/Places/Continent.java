package Places;

import Organisms.Tribe;

import java.util.ArrayList;

public class Continent {

    private String name;
    private ArrayList<Tribe> tribesInContinent;

    public Continent(String name) {
        this.name = name;
        tribesInContinent = new ArrayList<Tribe>();

    }

    public void addTribeToContinent(Tribe tribe) {
        tribesInContinent.add(tribe);
    }

    public ArrayList<Tribe> getTribesInContinent() {
        return tribesInContinent;
    }

    public void setTribesInContinent(ArrayList<Tribe> tribesInContinent) {
        this.tribesInContinent = tribesInContinent;
    }

    @Override
    public String toString() {
        String info = "";

        for(Tribe tribe: tribesInContinent) {
            info += ""+name+" -> "+tribe.toString()+"\n";
        }
        return info;
    }
}
