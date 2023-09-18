package org.voi.models.records;

import org.voi.models.Player;
import org.voi.models.Token;

import java.math.BigDecimal;

public class OwnershipRecord {
    private final Player player;
    private final Token token;
    private final int round;
    private final BigDecimal amountOwned;

    public OwnershipRecord(Player player, Token token, int round, BigDecimal amountOwned) {
        this.player = player;
        this.token = token;
        this.round = round;
        this.amountOwned = amountOwned;
    }

    public Player getPlayer() {
        return player;
    }

    public Token getToken() {
        return token;
    }

    public int getRound() {
        return round;
    }

    public BigDecimal getAmountOwned() {
        return amountOwned;
    }
}
