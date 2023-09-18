package org.voi.models.summary;

import org.voi.models.Player;
import org.voi.models.Token;

import java.math.BigDecimal;
import java.util.Map;

public class TokenOwnershipSummary implements Summary {
    private final Map<Token, BigDecimal> tokenSupply;
    private final Map<Token, Map<Player, BigDecimal>> tokenOwnership;

    public TokenOwnershipSummary(Map<Token, BigDecimal> tokenSupply, Map<Token, Map<Player, BigDecimal>> tokenOwnership) {
        this.tokenSupply = tokenSupply;
        this.tokenOwnership = tokenOwnership;
    }

    public BigDecimal getTokenSupply(Token token) {
        return tokenSupply.getOrDefault(token, BigDecimal.valueOf(0.0));
    }

    public BigDecimal getTokenOwnership(Token token, Player player) {
        if (tokenOwnership.containsKey(token)) {
            return tokenOwnership.get(token).getOrDefault(player, BigDecimal.valueOf(0.0));
        }
        return BigDecimal.valueOf(0.0);
    }

    public Map<Token, BigDecimal> getTokenSupply() {
        return tokenSupply;
    }

    public Map<Token, Map<Player, BigDecimal>> getTokenOwnership() {
        return tokenOwnership;
    }
}

