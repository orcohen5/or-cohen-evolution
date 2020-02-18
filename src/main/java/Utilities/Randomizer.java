package Utilities;

import java.util.Random;

public class Randomizer {
    private static Random random = new Random();

    public static int getRandomNumber(int maximumValue) {
        int randomNumber = random.nextInt(maximumValue + 1);
        return randomNumber;
    }

    public static int getRandomNumber(int minimumValue, int maximumValue) {
        int randomNumber = random.nextInt((maximumValue - minimumValue) + 1) + minimumValue;
        return randomNumber;
    }
}
