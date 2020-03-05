package Places;

import Organisms.Organism;
import Utilities.ExecutorServiceUtil;
import Utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Continent extends Thread {
    private String name;
    private List<Organism> organisms;
    private List<Organism> defendersToRemove;
    private List<Organism> attackersToRemove;
    private ExecutorService executor;

    public Continent(String name, List<Organism> organisms) {
        this.name = name;
        this.organisms = organisms;
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public String getContinentName() {
        return name;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void addOrganisms(List<Organism> organismsList) {

        for(Organism organism : organismsList) {
            organisms.add(organism);
        }
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganisms(List<Organism> organismsList) {
        Iterator<Organism> iterator = organismsList.iterator();

        while(iterator.hasNext()) {
            Organism defender = iterator.next();
            organisms.remove(defender);
        }
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    @Override
    public void run() {
        startLifeCycle();
    }

    @Override
    public String toString() {
        return getContinentData();
    }

    private String getContinentData() {
        StringBuilder builder = new StringBuilder();

        for(Organism organism : organisms) {
            builder.append(name + " -> " + organism.toString() + System.getProperty("line.separator"));
        }
        String continentData = builder.toString();
        return continentData;
    }

    private void startLifeCycle() {

        for(int i = 0; i < organisms.size(); i++) {
            executor.execute(organisms.get(i));
        }
        startOrganismsFight();
        LoggerUtil.logData(getContinentData());
    }

    private void startOrganismsFight() {
        defendersToRemove = new ArrayList();
        attackersToRemove = new ArrayList();

        for(Organism attacker : organisms) {

            for(Organism defender : organisms) {

                if(isAttackerNotDefending(attacker) && isDefenderNotFighting(defender)) {

                    if(attacker.getSumOfProperties() - defender.getSumOfProperties() > 5) {

                        if(attacker.getBalance() >= 1 && defender.getBalance() >= 1) {
                            attacker.setAttacker(true);
                            defender.setDefender(true);
                            LoggerUtil.logData("Battle Attacker -> " + attacker.toString());
                            LoggerUtil.logData("Battle Defender -> " + defender.toString());

                            if(attacker.getBalance() < (defender.getBalance() / 2)) {
                                attacker.setBalance(0);
                                attackersToRemove.add(attacker);
                                LoggerUtil.logData(attacker.getOrganismName() + " attack " + defender.getOrganismName() +
                                        "-> result = Tie" + "\n");
                            }
                            else {
                                attacker.setBalance(attacker.getBalance() - (defender.getBalance() / 2));
                                LoggerUtil.logData(attacker.getOrganismName() + " attack " + defender.getOrganismName() +
                                        "-> result = " + attacker.getOrganismName() + " Wins" + "\n");
                            }
                            attacker.setAttacker(false);
                            defender.setDefender(false);
                            defendersToRemove.add(defender);
                        }
                    }
                }
            }
        }
        removeOrganisms(defendersToRemove);
        removeOrganisms(attackersToRemove);
    }

    private boolean isAttackerNotDefending(Organism attacker) {
        return !attacker.isDefender();
    }

    private boolean isDefenderNotFighting(Organism defender) {
        return !defender.isDefender() && !defender.isAttacker();
    }
}
