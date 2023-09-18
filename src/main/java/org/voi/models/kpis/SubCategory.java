package org.voi.models.kpis;

import java.math.BigDecimal;

public class SubCategory {
    private final String name;
    private final BigDecimal points;

    public SubCategory(String name, BigDecimal points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPoints() {
        return points;
    }

}
