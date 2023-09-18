package org.voi.models.records;

import org.voi.models.Token;
import org.voi.models.kpis.SubCategory;

import java.math.BigDecimal;

public class KPIRecord {//e.g. Transactions for Token
    private final Token token;
    private final SubCategory subCategory;
    private final int round;
    private final BigDecimal units;
    private BigDecimal points;

    public KPIRecord(Token token, SubCategory subCategory, int round, BigDecimal units) {
        this.token = token;
        this.subCategory = subCategory;
        this.round = round;
        this.units = units;
    }

    public KPIRecord(KPIRecord kpiRecord, SubCategory subCategory) {
        this.token = kpiRecord.getToken();
        this.round = kpiRecord.getRound();
        this.units = kpiRecord.getUnits();
        this.subCategory = subCategory;
    }

    public Token getToken() {
        return token;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public int getRound() {
        return round;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

}
