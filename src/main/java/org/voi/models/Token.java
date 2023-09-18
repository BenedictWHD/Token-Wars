package org.voi.models;

import java.math.BigDecimal;

public class Token {
    private final int id;
    private final String name;

    public Token(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
