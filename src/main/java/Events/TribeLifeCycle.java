package Events;

import Organisms.*;

public class TribeLifeCycle implements Runnable {
    private Tribe tribe;

    public TribeLifeCycle(Tribe tribe) {
        this.tribe = tribe;
    }

    public Tribe getTribe() {
        return tribe;
    }

    public void runCycle() {
        this.tribe.randomOnePropertyFromThree();
        this.tribe.randomOneToFiveTimes();
        this.tribe.setBalance(tribe.getBalance()*tribe.getMultiplication());
    }

    public void run() {
        runCycle();
    }
}
