package org.voi;

import org.voi.models.Player;
import org.voi.models.Round;
import org.voi.models.config.WarConfig;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        //Placeholder numbers
        BigDecimal rewards = BigDecimal.valueOf(300_000_000.00);
        BigDecimal pointsPerRound = BigDecimal.valueOf(10_000_000_000.00);
        BigDecimal pointsForWar = BigDecimal.valueOf(10_000_000_000.00);
        BigDecimal warRewardWeight = BigDecimal.valueOf(0.2);
        BigDecimal[] battleRewardWeights = {BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.2)};
        int numberOfTokens = 5;
        int numberOfPlayers = 5;

        if (isWeightingsInvalid(warRewardWeight, battleRewardWeights)) return;

        // Config
        WarConfig warConfig = new WarConfig(rewards, pointsPerRound, pointsForWar, battleRewardWeights, warRewardWeight, numberOfTokens, numberOfPlayers);

        // Start the war
        Simulator simulator = new Simulator(warConfig);

        // Create the records (simulate)
        simulator.initialize();

        // Analysis
        simulator.analyze();

        // Reward Analysis
        Map<Player, BigDecimal> finalRewards = new HashMap<>();
        Map<Round, Map<Player, BigDecimal>> roundRewards = new HashMap<>();

        for (Round round : simulator.getAllRounds()) {
            roundRewards.putIfAbsent(round, new HashMap<>());
            for (Map.Entry<Player, BigDecimal> playerToRewards : round.getPlayerRewardSummary().getPlayerRewardSummary().entrySet()) {
                finalRewards.putIfAbsent(playerToRewards.getKey(), BigDecimal.valueOf(0.0));
                finalRewards.computeIfPresent(playerToRewards.getKey(), (k, v) -> v.add(playerToRewards.getValue()));
                roundRewards.get(round).putIfAbsent(playerToRewards.getKey(), BigDecimal.valueOf(0.0));
                roundRewards.get(round).computeIfPresent(playerToRewards.getKey(), (k, v) -> v.add(playerToRewards.getValue()));
            }
        }

        System.out.println("finalRewards");
        finalRewards.forEach((key, value) -> System.out.println(key.getName() + " : " + value));
        System.out.println("\n\n");
        System.out.println("roundRewards");
        roundRewards.forEach((k, v) -> {
            System.out.println(k.getName());
            v.forEach((k1, v1) -> System.out.println("  " + k1.getName() + " : " + v1));
        });
    }

    private static boolean isWeightingsInvalid(BigDecimal warRewardWeight, BigDecimal[] battleRewardWeights) {
        BigDecimal sum = warRewardWeight;
        for (BigDecimal battleRewardWeight : battleRewardWeights) {
            sum = sum.add(battleRewardWeight);
        }
        return !(sum.compareTo(BigDecimal.valueOf(1.0)) == 0);
    }
}
