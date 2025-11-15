package models;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughCreditsException;

public class Player {
    private final String playerName;
    private int credits;

    public Player(String playerName) {
        if (playerName == null || playerName.isBlank()) {throw new IllegalArgumentException("Username cannot be blank"); }

        this.playerName = playerName;
        this.credits = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int amount) {
        if (amount < 0) {throw new NegativeAmountException("Amount to add cannot be negative");}
        credits += amount;
    }

    public boolean useCredits(int amount) throws NotEnoughCreditsException {
        if (amount < 0) {throw new NegativeAmountException("Amount to use cannot be negative");}
        if (credits < amount) throw new NotEnoughCreditsException(playerName + " does not have enough credits.");
        credits -= amount;
        return false;
    }

    @Override
    public String toString() {
            return "Name: " + playerName + ", Credits: " + credits;
        }
    }

