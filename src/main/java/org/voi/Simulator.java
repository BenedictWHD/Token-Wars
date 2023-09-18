package org.voi;

import org.voi.analyzer.*;
import org.voi.models.Player;
import org.voi.models.Round;
import org.voi.models.Token;
import org.voi.models.config.RoundConfig;
import org.voi.models.config.WarConfig;
import org.voi.models.kpis.Category;
import org.voi.models.kpis.SubCategory;
import org.voi.models.records.KPIRecord;
import org.voi.models.records.OwnershipRecord;
import org.voi.models.summary.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Simulator {
    private final List<Round> battles;
    private Round war;
    private List<Round> allRounds;
    private final List<Player> players;
    private final List<Token> tokens;

    public Simulator(WarConfig warConfig) {
        this.tokens = new ArrayList<>(warConfig.getTokens());
        this.players = new ArrayList<>(warConfig.getPlayers());

        List<Round> battles = new ArrayList<>();
        for (RoundConfig roundConfig : warConfig.getBattleConfigs()) {
            battles.add(roundConfig.createBattleInstance(warConfig.getRewards(), "Battle " + roundConfig.getRank()));
        }

        this.battles = battles;
        this.war = warConfig.getWarRoundConfig().createBattleInstance(warConfig.getRewards(), "War");

    }


    public List<Round> getBattles() {
        return battles;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void initialize() {
        initializeBattleRecords();
        initializeWarRecords();
    }

    private void initializeBattleRecords() {//TODO: These 2 records types should be input into this application instead of generated how they are at the moment
        for (Round round : getBattles()) {
            initializeOwnershipRecords(round);
            initializeKpiRecords(round);
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void initializeOwnershipRecords(Round round) {
        for (Player players : getPlayers()) {
            for (Token tokens : getTokens()) {
                BigDecimal amountOwned = BigDecimal.valueOf(10);//BigDecimal.valueOf(getRandomNumber(0, 100));
                OwnershipRecord ownershipRecord = new OwnershipRecord(players, tokens, round.getRank(), amountOwned);
                round.addOwnershipRecord(ownershipRecord);
            }
        }
    }

    private void initializeKpiRecords(Round round) {
        for (Token token : getTokens()) {
            for (Category category : round.getCategories()) {
                for (SubCategory subCategory : category.getSubCategories()) {
                    BigDecimal units = BigDecimal.valueOf(10);//BigDecimal.valueOf(getRandomNumber(0, 100));
                    KPIRecord kpiRecord = new KPIRecord(token, subCategory, round.getRank(), units);
                    round.addKpiRecord(kpiRecord);
                }
            }
        }
    }

    private void initializeWarRecords() {
        List<KPIRecord> battleKPIRecords = getBattles().stream().flatMap(e -> e.getKpiRecords().stream()).toList();
        Map<String, SubCategory> allSubCategories = getWar().getCategories().stream().flatMap(e -> e.getSubCategories().stream()).collect(Collectors.toMap(SubCategory::getName, (v) -> v));

        for (KPIRecord kpiRecord : battleKPIRecords) {
            SubCategory battleRecordsSubCategory = kpiRecord.getSubCategory();
            SubCategory subCategory = allSubCategories.get(battleRecordsSubCategory.getName());
            getWar().addKpiRecord(new KPIRecord(kpiRecord, subCategory));
        }
    }

    public void analyze() {
        analyzeRecords();
        assignPointsToTokensKpis();
        assignPointsToTokens();
        assignRewardsToTokens();
        assignRewardsToPlayers();
    }

    private void analyzeRecords() {
        analyzeTokenOwnership();
        setupAllRounds();
        analyzeKPIRecords();
    }

    private void setupAllRounds() {
        ArrayList<Round> allRounds = new ArrayList<>(getBattles());
        allRounds.add(getWar());
        setAllRounds(allRounds);
    }

    private void assignPointsToTokensKpis() {//TODO: Implement caps
        for (Round round : getAllRounds()) {
            for (KPIRecord kpiRecord : round.getKpiRecords()) {// group them by their KPI subcategory
                BigDecimal pointsForKpi = kpiRecord.getSubCategory().getPoints();
                BigDecimal totalKpiUnitsForAllTokens = round.getKpiSummary().getTotalUnitsForKpiInRound(kpiRecord.getSubCategory());
                BigDecimal kpiUnitsForThisToken = kpiRecord.getUnits();
                BigDecimal kpiPointsForThisToken = pointsForKpi.multiply(kpiUnitsForThisToken.divide(totalKpiUnitsForAllTokens, MathContext.DECIMAL128));
                kpiRecord.setPoints(kpiPointsForThisToken);
            }
        }
    }

    private void analyzeTokenOwnership() {
        for (Round round : getBattles()) {
            TokenOwnershipSummary tokenOwnershipSummary = AnalyzerManager.getAnalyzer(TokenOwnershipAnalyzer.class).analyze(round);
            round.setTokenOwnershipSummary(tokenOwnershipSummary);
        }
        TokenOwnershipSummary finalTokenOwnership = getFinalTokenOwnership();
        getWar().setTokenOwnershipSummary(finalTokenOwnership);
    }

    private void analyzeKPIRecords() {
        for (Round round : getAllRounds()) {
            KPISummary kpiSummary = AnalyzerManager.getAnalyzer(KPIAnalyzer.class).analyze(round);
            round.setKpiSummary(kpiSummary);
        }
    }

    private void assignPointsToTokens() {
        for (Round round : getAllRounds()) {
            TokenPointsSummary tokenPointsSummary = AnalyzerManager.getAnalyzer(TokenPointsAnalyzer.class).analyze(round);
            round.setTokenPointsSummary(tokenPointsSummary);
        }
    }

    private void assignRewardsToTokens() {
        for (Round round : getAllRounds()) {
            TokenRewardSummary tokenRewardSummary = AnalyzerManager.getAnalyzer(TokenRewardsAnalyzer.class).analyze(round);
            round.setTokenRewardSummary(tokenRewardSummary);
        }
    }

    private void assignRewardsToPlayers() {
        for (Round round : getAllRounds()) {
            PlayerRewardSummary playerRewardSummary = AnalyzerManager.getAnalyzer(PlayerRewardAnalyzer.class).analyze(round);
            round.setPlayerRewardSummary(playerRewardSummary);
        }
    }

    private TokenOwnershipSummary getFinalTokenOwnership() {
        Round finalRound = getFinalBattle();
        return finalRound.getTokenOwnershipSummary();
    }

    private Round getFinalBattle() {
        int numberOfBattles = getBattles().size();
        Optional<Round> finalBattle = getBattles().stream().filter(e -> e.getRank() == numberOfBattles).findFirst();
        if (finalBattle.isPresent()) {
            return finalBattle.get();
        }
        throw new RuntimeException();
    }

    public void setAllRounds(List<Round> allRounds) {
        this.allRounds = allRounds;
    }

    public Round getWar() {
        return war;
    }

    public List<Round> getAllRounds() {
        return allRounds;
    }

}
