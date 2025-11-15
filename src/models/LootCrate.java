package models;
import exceptions.NotEnoughCreditsException;

public class LootCrate {
    private final int crateId;
    private final int price;

    public LootCrate(int crateId, int price) {
        if (crateId < 0) {throw new IllegalArgumentException("Crate Id cannot be negative");}
        if (price < 0) {throw new IllegalArgumentException("Price cannot be negative");}

        this.crateId = crateId;
        this.price = price;
    }

    public int getCrateId() {
        return crateId;
    }

    public int getPrice() {
        return price;
    }

    public boolean open(Player player) throws NotEnoughCreditsException {
        return player.useCredits(price);
    }
}
