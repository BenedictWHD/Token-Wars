package org.voi.models.config;

import org.voi.models.Round;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoundConfig {

    private final int rank;
    private final List<CategoryConfig> categoryConfigs;
    private final BigDecimal rewardWeight;
    private final BigDecimal battlePoints;

    public RoundConfig(int rank, BigDecimal rewardWeight, BigDecimal battlePoints) {
        this.rank = rank;
        this.rewardWeight = rewardWeight;
        this.battlePoints = battlePoints;
        this.categoryConfigs = new ArrayList<>();
    }

    public void addCategory(CategoryConfig category) {
        categoryConfigs.add(category);
    }

    public boolean verifyCategoryWeighting() {
        BigDecimal totalWeight = BigDecimal.valueOf(0.0);
        for (CategoryConfig category : categoryConfigs) {
            totalWeight = totalWeight.add(category.getPointsWeight());
            if (!category.verifySubCategoryWeighting()) {
                return false;
            }
        }
        return totalWeight.compareTo(BigDecimal.valueOf(1.0)) == 0;
    }

    public Round createBattleInstance(BigDecimal totalRewards, String name) {
        boolean isWeightingCorrect = verifyCategoryWeighting();
        if (!isWeightingCorrect) {
            throw new RuntimeException("Invalid reward weightings");
        }
        BigDecimal battleRewards = rewardWeight.multiply(totalRewards);
        Round round = new Round(rank, name, battleRewards, battlePoints);
        for (CategoryConfig categoryConfig : categoryConfigs) {
            round.addCategory(categoryConfig.createCategoryInstance(battlePoints));
        }
        return round;
    }

    public int getRank() {
        return rank;
    }

    public List<CategoryConfig> getCategoryConfigs() {
        return categoryConfigs;
    }

    public BigDecimal getRewardWeight() {
        return rewardWeight;
    }

    public BigDecimal getBattlePoints() {
        return battlePoints;
    }
}
