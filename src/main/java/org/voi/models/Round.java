package org.voi.models;

import org.voi.models.records.KPIRecord;
import org.voi.models.records.OwnershipRecord;
import org.voi.models.summary.*;
import org.voi.models.kpis.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Round {

    private final int rank;
    private final String name;
    private final BigDecimal reward;
    private final BigDecimal points;
    private final List<Category> categories;
    private final List<OwnershipRecord> ownershipRecords;
    private final List<KPIRecord> kpiRecords;
    private KPISummary kpiSummary;
    private TokenOwnershipSummary tokenOwnershipSummary;
    private TokenPointsSummary tokenPointsSummary;
    private TokenRewardSummary tokenRewardSummary;
    private PlayerRewardSummary playerRewardSummary;


    public Round(int rank, String name, BigDecimal reward, BigDecimal points) {
        this.rank = rank;
        this.name = name;
        this.reward = reward;
        this.points = points;
        this.categories = new ArrayList<>();
        this.ownershipRecords = new ArrayList<>();
        this.kpiRecords = new ArrayList<>();
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public TokenPointsSummary getTokenPointsSummary() {
        return tokenPointsSummary;
    }

    public void setTokenPointsSummary(TokenPointsSummary tokenPointsSummary) {
        this.tokenPointsSummary = tokenPointsSummary;
    }

    public List<OwnershipRecord> getOwnershipRecords() {
        return ownershipRecords;
    }

    public List<KPIRecord> getKpiRecords() {
        return kpiRecords;
    }

    public TokenOwnershipSummary getTokenOwnershipSummary() {
        return tokenOwnershipSummary;
    }

    public KPISummary getKpiSummary() {
        return kpiSummary;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addOwnershipRecord(OwnershipRecord ownershipRecord) {
        ownershipRecords.add(ownershipRecord);
    }


    public void addKpiRecord(KPIRecord kpiRecord) {
        kpiRecords.add(kpiRecord);
    }

    public void setTokenOwnershipSummary(TokenOwnershipSummary tokenOwnershipSummary) {
        this.tokenOwnershipSummary = tokenOwnershipSummary;
    }

    public void setKpiSummary(KPISummary kpiSummary) {
        this.kpiSummary = kpiSummary;
    }

    public TokenRewardSummary getTokenRewardSummary() {
        return tokenRewardSummary;
    }

    public void setTokenRewardSummary(TokenRewardSummary tokenRewardSummary) {
        this.tokenRewardSummary = tokenRewardSummary;
    }

    public PlayerRewardSummary getPlayerRewardSummary() {
        return playerRewardSummary;
    }

    public void setPlayerRewardSummary(PlayerRewardSummary playerRewardSummary) {
        this.playerRewardSummary = playerRewardSummary;
    }

    public BigDecimal getPoints() {
        return points;
    }
}
