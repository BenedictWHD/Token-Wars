package org.voi.models.config;


import org.voi.models.kpis.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CategoryConfig {

    private final String name;
    private final List<SubCategoryConfig> subCategoryConfigs;
    private final BigDecimal pointsWeight;

    public CategoryConfig(String name, BigDecimal pointsWeight) {
        this.name = name;
        this.pointsWeight = pointsWeight;
        this.subCategoryConfigs = new ArrayList<>();
    }

    public void addSubCategory(String name, BigDecimal weight) {
        SubCategoryConfig subCategory = new SubCategoryConfig(name, weight);
        subCategoryConfigs.add(subCategory);
    }

    public boolean verifySubCategoryWeighting() {
        BigDecimal totalWeight = BigDecimal.valueOf(0.0);
        for (SubCategoryConfig sc : subCategoryConfigs) {
            totalWeight = totalWeight.add(sc.getPointsWeight());
        }
        return totalWeight.compareTo(BigDecimal.valueOf(1.0)) == 0;
    }

    public String getName() {
        return name;
    }

    public List<SubCategoryConfig> getSubCategoryConfigs() {
        return subCategoryConfigs;
    }

    public BigDecimal getPointsWeight() {
        return pointsWeight;
    }

    public Category createCategoryInstance(BigDecimal pointsForBattle) {
        BigDecimal categoryPoints = pointsWeight.multiply(pointsForBattle);
        Category category = new Category(name, categoryPoints);
        for (SubCategoryConfig subCategoryConfig : subCategoryConfigs) {
            category.addSubCategory(subCategoryConfig.createSubCategoryInstance(categoryPoints));
        }
        return category;
    }
}
