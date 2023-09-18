package org.voi.analyzer;

import org.voi.models.Round;
import org.voi.models.kpis.SubCategory;
import org.voi.models.records.KPIRecord;
import org.voi.models.summary.KPISummary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class KPIAnalyzer implements Analyzer<KPISummary> {
    private static KPIAnalyzer analyzer;

    public static KPIAnalyzer getAnalyzer() {
        if (analyzer == null) {
            analyzer = new KPIAnalyzer();
        }
        return analyzer;
    }

    @Override
    public KPISummary analyze(Round round) {
        Map<SubCategory, BigDecimal> subCategoryTotalUnitsPerRound = new HashMap<>();
        for (KPIRecord record : round.getKpiRecords()) {
            subCategoryTotalUnitsPerRound
                    .putIfAbsent(record.getSubCategory(), BigDecimal.valueOf(0.0));
            subCategoryTotalUnitsPerRound.computeIfPresent(record.getSubCategory(), (t, current) -> current.add(record.getUnits()));
        }
        return new KPISummary(subCategoryTotalUnitsPerRound);
    }


}

