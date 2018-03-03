package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;

import static com.rhejinald.euler.lib.cards.PokerHandRank.*;

public abstract class PokerHand {
    private static final ArrayList<PokerHandRank> HAND_RANKS_DESCENDING = Lists.newArrayList(STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD);
    protected final List<Card> cards;

    public PokerHand(List<Card> cards) {
        this.cards = cards;
    }

    public static PokerHand create(List<Card> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("Too many or too few cards");
        }
        for (PokerHandRank pokerHandRank : HAND_RANKS_DESCENDING) {
            PokerHand potentialHand = pokerHandRank.toHand(cards);
            if (potentialHand.test()) {
                return potentialHand;
            }
        }
        throw new IllegalArgumentException("Card hand type not found");
    }

    public boolean winsAgainst(PokerHand other) {
        PokerHandRank thisHandRank = this.getHandRank();
        PokerHandRank otherHandRank = other.getHandRank();
        return (thisHandRank.getNumericalTier() > otherHandRank.getNumericalTier())
                || (thisHandRank.getNumericalTier() == otherHandRank.getNumericalTier() && this.getTieBreakScore() > other.getTieBreakScore());
    }

    public abstract PokerHandRank getHandRank();

    abstract boolean test();

    abstract long getTieBreakScore();

    public List<Card> getCards() {
        return cards;
    }

}
