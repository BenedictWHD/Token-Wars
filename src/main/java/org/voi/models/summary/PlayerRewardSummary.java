package org.voi.models.summary;

import org.voi.models.Player;

import java.math.BigDecimal;
import java.util.Map;

public class PlayerRewardSummary implements Summary {
    private final Map<Player, BigDecimal> playerRewardSummary;

    public PlayerRewardSummary(Map<Player, BigDecimal> playerRewardSummary) {
        this.playerRewardSummary = playerRewardSummary;
    }

    public BigDecimal getPlayerRewards(Player player) {
        return playerRewardSummary.getOrDefault(player, BigDecimal.valueOf(0.0));
    }

    public Map<Player, BigDecimal> getPlayerRewardSummary() {
        return playerRewardSummary;
    }
}
