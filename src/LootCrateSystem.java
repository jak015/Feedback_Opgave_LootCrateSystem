import exceptions.*;
import models.*;

import java.util.ArrayList;
import java.util.Scanner;

public class LootCrateSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LootCrateSystem system = new LootCrateSystem();

        ArrayList<Player> players = new ArrayList<>();

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        players.add(player1);
        players.add(player2);

        LootCrate cobblestoneCrate = new LootCrate(1, 100);
        LootCrate diamondCrate = new LootCrate(2, 200);

        System.out.println("""
                \n--- Loot Crate System ---
                1. Alice opens Cobblestone Crate (cost: 100)
                2. Bob opens Cobblestone Crate (cost: 100)
                3. Add credits to Alice
                4. Random player tries to open Diamond Crate (cost: 200)
                5. Exit
                -----------------------------
                Enter your choice:
                """);

        while (true) {
            System.out.println("Choose case 1, 2, 3,4 or 5:");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> openCrate("Alice", cobblestoneCrate, 100, system, players);
                case 2 -> openCrate("Bob", cobblestoneCrate, 50, system, players);
                case 3 -> addCreditsToPlayer("Alice", system, players, sc);
                case 4 -> openCrate("Random", diamondCrate, 200, system, players);
                case 5 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    sc.close();
                    return;
                }
            }
            System.out.println();
        }
    }

    public Player findPlayer(String playerName, ArrayList<Player> players) throws PlayerNotFoundException {
        for (Player p : players) {
            if (p.getPlayerName().equalsIgnoreCase(playerName.trim())) {
                return p;
            }
        }
        throw new PlayerNotFoundException("Player " + playerName + " not found.");
    }

    private static void openCrate(String playerName, LootCrate crate, int extraCredits, LootCrateSystem
            system, ArrayList<Player> players) {
        try {
            Player player = system.findPlayer(playerName, players);
            if (extraCredits > 0) {
                player.addCredits(extraCredits);
            }

            if (crate.open(player)) {
                System.out.println(player.getPlayerName() + " opened crate '" + crate.getCrateId() + "'!");
            } else {
                System.out.println(player.getPlayerName() + " does not have enough credits to open crate '" + crate.getCrateId() + "'.");
            }
        } catch (NotEnoughCreditsException | PlayerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addCreditsToPlayer(String playerName, LootCrateSystem
            system, ArrayList<Player> players, Scanner sc) {
        try {
            Player player = system.findPlayer(playerName, players);
            System.out.println("How many credits would you like to add? ");
            int amount = sc.nextInt();
            sc.nextLine();
            player.addCredits(amount);
            System.out.println(player.getPlayerName() + " now has " + player.getCredits() + " credits.");
        } catch (NegativeAmountException | PlayerNotFoundException e) {
            System.out.println("Cannot add negative amount of credits.");
        }
    }
}

