package Organisms;

public abstract class Tribe extends Thread{
    private double strength;
    private double intelligence;
    private double technologicalMeans;
    private double mutation;
    private int balance;
    private int multiplication;
    private String name;

    public Tribe(String name) {
        this.strength = 1.0;
        this.intelligence = 1.0;
        this.technologicalMeans = 1.0;
        this.mutation = 1.5;
        this.balance = 1;
        this.multiplication = 3;
        this.name = name;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getTechnologicalMeans() {
        return technologicalMeans;
    }

    public void setTechnologicalMeans(double technologicalMeans) {
        this.technologicalMeans = technologicalMeans;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getMultiplication() {
        return multiplication;
    }

    public abstract String getType();

    public void randomOnePropertyFromThree() {
        double randomChooser = Math.random();

        if (randomChooser >= 0 && randomChooser < 0.3333) {
            this.strength = this.strength * this.mutation;
        }
        else if (randomChooser >= 0.3333 && randomChooser < 0.6666) {
            this.intelligence = this.intelligence * this.mutation;
        }
        else {
            this.technologicalMeans = this.technologicalMeans * this.mutation;
        }
    }

    public void randomOneToFiveTimes() {
        double randomOneToFive = Math.random();

        if(randomOneToFive>=0 && randomOneToFive<=0.2) {
            increasePropertyOneToFiveTimes();
        }
    }

    public abstract void increasePropertyOneToFiveTimes();

    public void run() {

    }

    public String toString() {
        return ""+this.name+" -> [Strength="+strength+", Intelligence="+intelligence+", Technological Means="+ technologicalMeans +", Balance="+balance+"] Thread["+Thread.currentThread().getName()+"]["+getType()+"]";
    }
}
