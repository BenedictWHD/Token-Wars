package org.voi.models.kpis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private final BigDecimal points;
    private final List<SubCategory> subCategories;

    public Category(String name, BigDecimal points) {
        this.name = name;
        this.points = points;
        this.subCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory) {
        subCategories.add(subCategory);
    }
}
