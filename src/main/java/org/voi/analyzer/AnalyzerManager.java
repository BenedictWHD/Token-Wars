package org.voi.analyzer;

import org.voi.models.summary.Summary;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerManager {
    private static Map<Class<? extends Analyzer>, Analyzer> analyzers = new HashMap<>();

    static {
        analyzers.put(KPIAnalyzer.class, KPIAnalyzer.getAnalyzer());
        analyzers.put(PlayerRewardAnalyzer.class, PlayerRewardAnalyzer.getAnalyzer());
        analyzers.put(TokenOwnershipAnalyzer.class, TokenOwnershipAnalyzer.getAnalyzer());
        analyzers.put(TokenPointsAnalyzer.class, TokenPointsAnalyzer.getAnalyzer());
        analyzers.put(TokenRewardsAnalyzer.class, TokenRewardsAnalyzer.getAnalyzer());
    }

    public static <T extends Summary> Analyzer<T> getAnalyzer(Class<? extends Analyzer<T>> analyzerClass) {
        return analyzers.get(analyzerClass);
    }
}
