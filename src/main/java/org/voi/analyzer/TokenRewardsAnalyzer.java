package org.voi.analyzer;

import org.voi.models.Round;
import org.voi.models.Token;
import org.voi.models.summary.TokenRewardSummary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

public class TokenRewardsAnalyzer implements Analyzer<TokenRewardSummary> {

    private static TokenRewardsAnalyzer analyzer;

    public static TokenRewardsAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new TokenRewardsAnalyzer();
        }
        return analyzer;
    }

    @Override
    public TokenRewardSummary analyze(Round round) {
        Map<Token, BigDecimal> tokenRewardWeightSummary = new HashMap<>();
        Map<Token, BigDecimal> tokenRewardsSummary = new HashMap<>();
        for (Map.Entry<Token, BigDecimal> tokenDoubleEntry : round.getTokenPointsSummary().getTokenPointsSummary().entrySet()) {
            BigDecimal tokenPointsForBattle = tokenDoubleEntry.getValue();
            BigDecimal tokenRewardShare = tokenPointsForBattle.divide(round.getPoints(), MathContext.DECIMAL128);
            tokenRewardWeightSummary.put(tokenDoubleEntry.getKey(), tokenRewardShare);
            tokenRewardsSummary.put(tokenDoubleEntry.getKey(), tokenRewardShare.multiply(round.getReward()));
        }
        return new TokenRewardSummary(tokenRewardWeightSummary, tokenRewardsSummary);
    }
}
