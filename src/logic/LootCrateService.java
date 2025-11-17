package logic;

import dal.*;
import exceptions.*;
import models.*;

public class LootCrateService {
    private final PlayerRepository playerRepository;
    private final LootCrateRepository lootCrateRepository;

    public LootCrateService(PlayerRepository playerRepository, LootCrateRepository lootCrateRepository) {
        this.playerRepository = playerRepository;
        this.lootCrateRepository = lootCrateRepository;
    }

    public void createPlayer(String playerName, int startCredits) {
        if (startCredits < 0) {
            throw new NegativeAmountException("Start credits cannot be negative.");
        }

        Player player = new Player(playerName, startCredits);
        playerRepository.addPlayer(player);
    }

    public void addCreditsToPlayer(String playerName, int amount) throws PlayerNotFoundException {
        if (amount < 0) {
            throw new NegativeAmountException("Amount to add cannot be negative.");
        }

        Player player = playerRepository.findByPlayerName(playerName);
        int newCredits = player.getCredits() + amount;
        player.setCredits(newCredits);
        playerRepository.updatePlayerCredits(playerName, newCredits);
    }

    public void openCrateForPlayer(String playerName, int crateId) throws PlayerNotFoundException, LootCrateNotFoundException, NotEnoughCreditsException {
        Player player = playerRepository.findByPlayerName(playerName);
        LootCrate crate = lootCrateRepository.findById(crateId);

        if (player.getCredits() < crate.getPrice()) {
            throw new NotEnoughCreditsException("Not enough credits. Crate costs " + crate.getPrice());
        }

        int newCredits = player.getCredits() - crate.getPrice();
        player.setCredits(newCredits);
        playerRepository.updatePlayerCredits(playerName, newCredits);
    }

    public Player getPlayer(String playerName) throws PlayerNotFoundException {
        return playerRepository.findByPlayerName(playerName);
    }
    public void addLootCrate(int crateId, int price, String name) {
        LootCrate crate = new LootCrate(crateId, price, name);
        lootCrateRepository.addLootCrate(crate);
    }
}
