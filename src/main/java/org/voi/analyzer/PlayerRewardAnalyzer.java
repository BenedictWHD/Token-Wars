package org.voi.analyzer;

import org.voi.models.Player;
import org.voi.models.Round;
import org.voi.models.Token;
import org.voi.models.summary.PlayerRewardSummary;
import org.voi.models.summary.TokenOwnershipSummary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

public class PlayerRewardAnalyzer implements Analyzer<PlayerRewardSummary> {

    private static PlayerRewardAnalyzer analyzer;

    public static PlayerRewardAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new PlayerRewardAnalyzer();
        }
        return analyzer;
    }

    private PlayerRewardAnalyzer() {
    }

    @Override
    public PlayerRewardSummary analyze(Round round) {
        Map<Player, BigDecimal> playerRewardSummary = new HashMap<>();
        TokenOwnershipSummary tokenOwnershipSummary = round.getTokenOwnershipSummary();
        for (Map.Entry<Token, Map<Player, BigDecimal>> tokenToPlayer : tokenOwnershipSummary.getTokenOwnership().entrySet()) {
            Token token = tokenToPlayer.getKey();
            BigDecimal tokenRewards = round.getTokenRewardSummary().getTokenRewards(token);
            BigDecimal tokenSupply = tokenOwnershipSummary.getTokenSupply(token);
            for (Map.Entry<Player, BigDecimal> playerToOwnership : tokenToPlayer.getValue().entrySet()) {
                Player player = playerToOwnership.getKey();
                BigDecimal tokensOwned = playerToOwnership.getValue();
                BigDecimal rewardShare = tokensOwned.divide(tokenSupply, MathContext.DECIMAL128);
                BigDecimal playerRewards = tokenRewards.multiply(rewardShare);
                playerRewardSummary.putIfAbsent(player, BigDecimal.valueOf(0.0));
                playerRewardSummary.computeIfPresent(player, (p, current) -> current.add(playerRewards));
            }
        }
        return new PlayerRewardSummary(playerRewardSummary);
    }
}
