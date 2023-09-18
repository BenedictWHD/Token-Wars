package org.voi.analyzer;

import org.voi.models.Round;
import org.voi.models.Token;
import org.voi.models.records.KPIRecord;
import org.voi.models.summary.TokenPointsSummary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TokenPointsAnalyzer implements Analyzer<TokenPointsSummary> {
    private static TokenPointsAnalyzer analyzer;

    public static TokenPointsAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new TokenPointsAnalyzer();
        }
        return analyzer;
    }

    @Override
    public TokenPointsSummary analyze(Round round) {
        Map<Token, BigDecimal> tokenPointsSummary = new HashMap<>();
        for (KPIRecord kpiRecord : round.getKpiRecords()) {
            tokenPointsSummary.putIfAbsent(kpiRecord.getToken(), BigDecimal.valueOf(0.0));
            tokenPointsSummary.computeIfPresent(kpiRecord.getToken(), (t, current) -> current.add(kpiRecord.getPoints()));
        }
        return new TokenPointsSummary(tokenPointsSummary);
    }
}
