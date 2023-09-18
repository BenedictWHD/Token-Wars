package org.voi.models.config;

import org.voi.models.kpis.SubCategory;

import java.math.BigDecimal;

public class SubCategoryConfig {
    private final String name;
    private final BigDecimal pointsWeight;

    public SubCategoryConfig(String name, BigDecimal pointsWeight) {
        this.name = name;
        this.pointsWeight = pointsWeight;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPointsWeight() {
        return pointsWeight;
    }

    public SubCategory createSubCategoryInstance(BigDecimal categoryPoints) {
        BigDecimal subCategoryPoints = pointsWeight.multiply(categoryPoints);
        return new SubCategory(name, subCategoryPoints);
    }
}
