package com.rhejinald.euler.lib.cards;

public enum Suit {
    CLUB, SPADE, DIAMOND, HEART;

    public static Suit toSuit(String id) {
        switch (id.toUpperCase()) {
            case "D":
                return Suit.DIAMOND;
            case "C":
                return Suit.CLUB;
            case "S":
                return Suit.SPADE;
            case "H":
                return Suit.HEART;
            default:
                throw new IllegalArgumentException();
        }
    }
}
