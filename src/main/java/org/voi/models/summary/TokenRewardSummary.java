package org.voi.models.summary;

import org.voi.models.Token;

import java.math.BigDecimal;
import java.util.Map;

public class TokenRewardSummary implements Summary {

    private final Map<Token, BigDecimal> tokenRewardWeightSummary;
    private final Map<Token, BigDecimal> tokenRewardSummary;

    public TokenRewardSummary(Map<Token, BigDecimal> tokenRewardWeightSummary, Map<Token, BigDecimal> tokenRewardSummary) {
        this.tokenRewardWeightSummary = tokenRewardWeightSummary;
        this.tokenRewardSummary = tokenRewardSummary;
    }

    public BigDecimal getTokenRewardWeights(Token token) {
        return tokenRewardWeightSummary.getOrDefault(token, BigDecimal.valueOf(0.0));
    }

    public Map<Token, BigDecimal> getTokenRewardWeightSummary() {
        return tokenRewardWeightSummary;
    }

    public BigDecimal getTokenRewards(Token token) {
        return tokenRewardSummary.getOrDefault(token, BigDecimal.valueOf(0.0));
    }

    public Map<Token, BigDecimal> getTokenRewardSummary() {
        return tokenRewardSummary;
    }
}
