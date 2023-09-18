package org.voi.models.summary;

import org.voi.models.kpis.SubCategory;

import java.math.BigDecimal;
import java.util.Map;

public class KPISummary implements Summary {
    private final Map<SubCategory, BigDecimal> kpiSummary;

    public KPISummary(Map<SubCategory, BigDecimal> kpiSummary) {
        this.kpiSummary = kpiSummary;
    }

    public BigDecimal getTotalUnitsForKpiInRound(SubCategory subCategory) {
        return kpiSummary.getOrDefault(subCategory, BigDecimal.valueOf(0.0));
    }

    public Map<SubCategory, BigDecimal> getOwnershipPerRound() {
        return kpiSummary;
    }
}