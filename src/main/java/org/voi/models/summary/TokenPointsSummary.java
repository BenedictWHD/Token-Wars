package org.voi.models.summary;

import org.voi.models.Token;

import java.math.BigDecimal;
import java.util.Map;

public class TokenPointsSummary implements Summary {
    private final Map<Token, BigDecimal> tokenPointsSummary;

    public TokenPointsSummary(Map<Token, BigDecimal> tokenPointsSummary) {
        this.tokenPointsSummary = tokenPointsSummary;
    }

    public BigDecimal getTokensPoints(Token token) {
        return tokenPointsSummary.getOrDefault(token, BigDecimal.valueOf(0.0));
    }

    public Map<Token, BigDecimal> getTokenPointsSummary() {
        return tokenPointsSummary;
    }
}
