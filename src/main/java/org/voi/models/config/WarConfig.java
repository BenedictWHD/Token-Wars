package org.voi.models.config;

import org.voi.Simulator;
import org.voi.models.Player;
import org.voi.models.Token;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WarConfig {

    private final List<RoundConfig> battleConfigs;

    private final RoundConfig warConfig;

    private final List<Player> players;

    private final List<Token> tokens;

    private final BigDecimal rewards;

    public WarConfig(BigDecimal rewards, BigDecimal pointsPerBattle, BigDecimal pointsForWar, BigDecimal[] battleRewardWeights, BigDecimal warRewardWeight, int numberOfTokens, int numberOfPlayers) {
        this.rewards = rewards;
        this.tokens = initializeTokens(numberOfTokens);
        this.players = initializePlayers(numberOfPlayers);
        this.battleConfigs = initializeBattleConfigs(battleRewardWeights, pointsPerBattle);
        this.warConfig = initializeWarConfig(battleRewardWeights.length + 1, warRewardWeight, pointsForWar);
    }


    private List<Token> initializeTokens(int numberOfTokens) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < numberOfTokens; i++) {
            Token tokenConfig = new Token(i, "Token " + i);
            tokens.add(tokenConfig);
        }
        return tokens;
    }

    private List<Player> initializePlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player playerConfig = new Player(i, "Player " + (i + 1));
            players.add(playerConfig);
        }
        return players;
    }

    private List<RoundConfig> initializeBattleConfigs(BigDecimal[] battleRewardWeights, BigDecimal pointsPerBattle) {
        List<RoundConfig> roundConfigs = new ArrayList<>();
        for (int i = 0; i < battleRewardWeights.length; i++) {
            RoundConfig roundConfig = new RoundConfig(i + 1, battleRewardWeights[i], pointsPerBattle);
            addCategories(roundConfig);
            roundConfigs.add(roundConfig);
        }
        return roundConfigs;
    }

    private RoundConfig initializeWarConfig(int warRank, BigDecimal warRewardWeight, BigDecimal pointsForWar) {
        RoundConfig warConfig = new RoundConfig(warRank, warRewardWeight, pointsForWar);
        addCategories(warConfig);
        return warConfig;
    }

    private void addCategories(RoundConfig roundConfig) {//Placeholder numbers
        CategoryConfig activity = new CategoryConfig("Activity", BigDecimal.valueOf(0.4));
        activity.addSubCategory("Activity 1", BigDecimal.valueOf(0.1));
        activity.addSubCategory("Activity 2", BigDecimal.valueOf(0.2));
        activity.addSubCategory("Activity 3", BigDecimal.valueOf(0.2));
        activity.addSubCategory("Activity 4", BigDecimal.valueOf(0.2));
        activity.addSubCategory("Activity 5", BigDecimal.valueOf(0.2));
        activity.addSubCategory("Activity 6", BigDecimal.valueOf(0.1));
        roundConfig.addCategory(activity);

        CategoryConfig engagement = new CategoryConfig("Engagement", BigDecimal.valueOf(0.2));
        engagement.addSubCategory("Engagement 1", BigDecimal.valueOf(0.25));
        engagement.addSubCategory("Engagement 2", BigDecimal.valueOf(0.25));
        engagement.addSubCategory("Engagement 3", BigDecimal.valueOf(0.25));
        engagement.addSubCategory("Engagement 4", BigDecimal.valueOf(0.25));
        roundConfig.addCategory(engagement);

        CategoryConfig market = new CategoryConfig("Market", BigDecimal.valueOf(0.4));
        market.addSubCategory("Market 1", BigDecimal.valueOf(0.2));
        market.addSubCategory("Market 2", BigDecimal.valueOf(0.2));
        market.addSubCategory("Market 3", BigDecimal.valueOf(0.2));
        market.addSubCategory("Market 4", BigDecimal.valueOf(0.2));
        market.addSubCategory("Market 5", BigDecimal.valueOf(0.2));
        roundConfig.addCategory(market);
    }

    public List<RoundConfig> getBattleConfigs() {
        return battleConfigs;
    }

    public RoundConfig getWarRoundConfig() {
        return warConfig;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public BigDecimal getRewards() {
        return rewards;
    }
}
