package org.voi.analyzer;

import org.voi.models.Player;
import org.voi.models.Round;
import org.voi.models.Token;
import org.voi.models.records.OwnershipRecord;
import org.voi.models.summary.TokenOwnershipSummary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TokenOwnershipAnalyzer implements Analyzer<TokenOwnershipSummary> {
    private static TokenOwnershipAnalyzer analyzer;

    public static TokenOwnershipAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new TokenOwnershipAnalyzer();
        }
        return analyzer;
    }

    @Override
    public TokenOwnershipSummary analyze(Round round) {
        Map<Token, BigDecimal> tokenSupply = new HashMap<>();
        Map<Token, Map<Player, BigDecimal>> tokenOwnership = new HashMap<>();
        for (OwnershipRecord record : round.getOwnershipRecords()) {
            tokenSupply.putIfAbsent(record.getToken(), BigDecimal.valueOf(0.0));
            tokenSupply.computeIfPresent(record.getToken(), (t, current) -> current.add(record.getAmountOwned()));
            tokenOwnership.putIfAbsent(record.getToken(), new HashMap<>());
            tokenOwnership.get(record.getToken()).putIfAbsent(record.getPlayer(), record.getAmountOwned());
        }
        return new TokenOwnershipSummary(tokenSupply, tokenOwnership);
    }
}

