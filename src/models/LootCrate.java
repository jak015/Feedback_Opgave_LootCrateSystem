package models;
import exceptions.NotEnoughCreditsException;

public class LootCrate {
    private final int crateId;
    private final int price;
    private final String name;

    public LootCrate(int crateId, int price, String name) {
        if (crateId < 0) {throw new IllegalArgumentException("Crate Id cannot be negative");}
        if (price < 0) {throw new IllegalArgumentException("Price cannot be negative");}
        if (name == null || name.isEmpty()) {throw new IllegalArgumentException("Name cannot be null or empty");}

        this.crateId = crateId;
        this.price = price;
        this.name = name;
    }

    public int getCrateId() {
        return crateId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
