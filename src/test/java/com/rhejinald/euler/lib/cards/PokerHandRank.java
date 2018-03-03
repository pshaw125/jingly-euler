package com.rhejinald.euler.lib.cards;

import java.util.List;
import java.util.function.Function;

public enum PokerHandRank {
    HIGH_CARD(0, HighCard::new),
    ONE_PAIR(1, OnePair::new),
    TWO_PAIR(2, TwoPair::new),
    THREE_OF_A_KIND(3, ThreeOfAKind::new),
    STRAIGHT(4, Straight::new),
    FLUSH(5, Flush::new),
    FULL_HOUSE(6, FullHouse::new),
    FOUR_OF_A_KIND(7, FourOfAKind::new),
    STRAIGHT_FLUSH(8, StraightFlush::new);

    private final int numericalTier;
    private final Function<List<Card>, PokerHand> toHand;

    PokerHandRank(int numericalTier, Function<List<Card>, PokerHand> toHand) {
        this.numericalTier = numericalTier;
        this.toHand = toHand;
    }

    public PokerHand toHand(List<Card> cards) {
        return toHand.apply(cards);
    }

    public int getNumericalTier() {
        return numericalTier;
    }
}
