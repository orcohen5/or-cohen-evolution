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
    private List<Organism> currentAttackers;
    private List<Organism> currentDefenders;
    private ExecutorService executor;
    private String fightData;

    public Continent(String name, List<Organism> organisms) {
        this.name = name;
        this.organisms = organisms;
        this.executor = ExecutorServiceUtil.getExecutor();
        this.fightData = "";
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

    private synchronized void startLifeCycle() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String continentData = "";

        for (int i = 0; i < organisms.size(); i++) {
            executor.execute(organisms.get(i));
        }
        continentData = getContinentData();
        startOrganismsFight();

        if(fightData == "")
            LoggerUtil.logData(continentData);
        else
            LoggerUtil.logData(continentData + fightData);
    }

    private void startOrganismsFight() {
        currentAttackers = new ArrayList();
        currentDefenders = new ArrayList();
        List<Organism> defendersToRemove = new ArrayList();
        List<Organism> attackersToRemove = new ArrayList();
        fightData = "";

        for(Organism attacker : organisms) {

            for(Organism defender : organisms) {

                if(isFightPossible(attacker, defender)) {
                    currentAttackers.add(attacker);
                    currentDefenders.add(defender);
                    long originalAttackerBalance = Long.parseLong("" + attacker.getBalance());
                    announceFight(attacker, defender);

                    if(attacker.getBalance() < (defender.getBalance() / 2)) {
                        attacker.setBalance(0);
                        attacker.setOrganismAlive(false);
                        attackersToRemove.add(attacker);
                        announceDraw(attacker, defender);
                    }
                    else {
                        attacker.setBalance(attacker.getBalance() - (defender.getBalance() / 2));
                        announceWinnerAndLoser(attacker, defender, originalAttackerBalance);
                    }
                    defender.setOrganismAlive(false);
                    defendersToRemove.add(defender);
                    currentDefenders.remove(defender);
                    currentAttackers.remove(attacker);
                }
            }
        }
        removeOrganisms(defendersToRemove);
        removeOrganisms(attackersToRemove);
    }

    public boolean isFightPossible(Organism attacker, Organism defender) {
        return isAttackerNotDefending(attacker) && isDefenderNotFighting(defender) &&
                (attacker.getSumOfProperties() - defender.getSumOfProperties() > 5) &&
                isAttackerAndDefenderAlive(attacker, defender) &&
                attacker.getBalance() >= 1 && defender.getBalance() >= 1;
    }

    private boolean isAttackerNotDefending(Organism attacker) {
        return !currentDefenders.contains(attacker);
    }

    private boolean isDefenderNotFighting(Organism defender) {
        return !currentDefenders.contains(defender) && !currentAttackers.contains(defender);
    }

    private boolean isAttackerAndDefenderAlive(Organism attacker, Organism defender) {
        return attacker.isOrganismAlive() && defender.isOrganismAlive();
    }

    private void announceFight(Organism attacker, Organism defender) {
        fightData += "\nThere is a fight in " + getContinentName() + ":\n" +
                attacker.getOrganismName() + " attack " + defender.getOrganismName() + "\n" +
                "Battle Attacker -> " + attacker.toString() + "\n" +
                "Battle Defender -> " + defender.toString() + "\n";
    }

    private void announceDraw(Organism attacker, Organism defender) {
        fightData += attacker.getOrganismName() + " attack " + defender.getOrganismName() +
                " -> result = Draw\n" +
                attacker.getOrganismName() + " and " + defender.getOrganismName() +
                " have extincted from " + getContinentName() + "\n";
    }

    private void announceWinnerAndLoser(Organism attacker, Organism defender, long originalAttackerBalance) {
        fightData += attacker.getOrganismName() + " attack " + defender.getOrganismName() +
                " -> result = " + attacker.getOrganismName() + " wins\n" +
                attacker.getOrganismName() + " has reduced from " + originalAttackerBalance +
                " to " + attacker.getBalance() + "\n" +
                defender.getOrganismName() + " has extincted from " + getContinentName() + "\n";
    }
}
