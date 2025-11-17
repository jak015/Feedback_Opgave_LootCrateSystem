package cli;

import config.DatabaseConfig;
import dal.*;
import exceptions.*;
import logic.LootCrateService;
import models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LootCrateApp {
    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        LootCrateRepository lootCrateRepository = new LootCrateRepository(config);

        LootCrateService service = new LootCrateService(playerRepository, lootCrateRepository);

        try (Connection conn = config.getConnection()) {
            System.out.println("Forbindelse oprettet: " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            System.out.println("""
            \n--- Loot Crate System ---
            1. Create player
            2. Add Credits to player
            3. Open Crate for player
            4. Show Player
            5. Add LootCrate
            6. Exit
            -----------------------------
            Enter your choice:
            """);

            while (running) {
                System.out.println("Choose case 1, 2, 3, 4, 5 or 6:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> handleCreatePlayer(scanner, service);
                    case 2 -> handleAddCredits(scanner, service);
                    case 3 -> handleOpenCrate(scanner, service);
                    case 4 -> handleShowPlayer(scanner, service);
                    case 5 -> handleAddLootCrate(scanner, service);
                    case 6 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();
                        return;
                    }
                }
                System.out.println();
            }
        }
    }

    private static void handleCreatePlayer(Scanner scanner, LootCrateService service) {
        System.out.print("Brugernavn: ");
        String username = scanner.next();

        System.out.print("Startcredits: ");
        String creditsInput = scanner.next();

        try {
            int startCredits = Integer.parseInt(creditsInput);
            service.createPlayer(username, startCredits);
            System.out.println("Spiller oprettet");
        } catch (NumberFormatException e) {
            System.out.println("Du skal skrive et helt tal for credits");
        } catch (NegativeAmountException e) {
            System.out.println("Fejl: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Uventet fejl: " + e.getMessage());
        }
    }

    private static void handleAddCredits(Scanner scanner, LootCrateService service) {
        System.out.print("Brugernavn: ");
        String username = scanner.next();

        System.out.print("Beløb: ");
        int amount = scanner.nextInt();

        try {
            service.addCreditsToPlayer(username, amount);
            System.out.println("Credits tilføjet");
        } catch (NumberFormatException e) {
            System.out.println("Du skal skrive et helt tal for beløb");
        } catch (PlayerNotFoundException e) {
            System.out.println("Fejl: " + e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println("Fejl: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Uventet fejl: " + e.getMessage());
        }
    }

    private static void handleOpenCrate(Scanner scanner, LootCrateService service) {
        System.out.print("Brugernavn: ");
        String username = scanner.next();

        System.out.print("Crate id: ");
        int crateId = scanner.nextInt();
        scanner.nextLine();

        try {
            service.openCrateForPlayer(username, crateId);
            System.out.println("Crate åbnet");
            int random = (int) (Math.random()*101);
            System.out.println("Tillykke! Du fik " + random + ": ");
            if (random > 91) {
                System.out.println("Wow! Du fik et EPIC item!");
            } else if ( random > 70 && random <=91) {
                System.out.println("Nice! Du fik et RARE item!");
            } else {
                System.out.println("Du fik et COMMON item. Prøv igen!");

            }
        } catch (PlayerNotFoundException | LootCrateNotFoundException | NotEnoughCreditsException e) {
            System.out.println("Fejl: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Uventet fejl: " + e.getMessage());
        }
    }

    private static void handleShowPlayer(Scanner scanner, LootCrateService service) {
        System.out.print("Brugernavn: ");
        String username = scanner.next();

        try {
            Player player = service.getPlayer(username);
            System.out.println(player);
        } catch (PlayerNotFoundException e) {
            System.out.println("Fejl: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Uventet fejl: " + e.getMessage());
        }
    }
    private static void handleAddLootCrate(Scanner scanner, LootCrateService service) {
        System.out.print("Crate id: ");
        int crateId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Price: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();


        try {
            service.addLootCrate(crateId, price, name);
            System.out.println("Lootcrate added");
        } catch (RuntimeException e) {
            System.out.println("Uventet fejl: " + e.getMessage());
        }
    }
}

