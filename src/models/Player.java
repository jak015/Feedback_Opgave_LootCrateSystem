package models;
import exceptions.NegativeAmountException;
import exceptions.NotEnoughCreditsException;

public class Player {
    private final String playerName;
    private int credits;

    public Player(String playerName, int credits) {
        if (playerName == null || playerName.isBlank()) {throw new IllegalArgumentException("Username cannot be blank"); }

        this.playerName = playerName;
        this.credits = credits;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int newCredits) {
        this.credits = newCredits;
    }


    @Override
    public String toString() {
        return "Name: " + playerName + ", Credits: " + credits;
    }
}

