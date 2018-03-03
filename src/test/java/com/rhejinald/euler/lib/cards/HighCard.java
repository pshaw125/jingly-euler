package com.rhejinald.euler.lib.cards;

import java.util.List;

import static com.rhejinald.euler.lib.cards.PokerHandRank.HIGH_CARD;

class HighCard extends PokerHand {

    public HighCard(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return HIGH_CARD;
    }

    @Override
    public boolean test() {
        return true;
    }

    @Override
    public long getTieBreakScore() {
        return CommonTieBreaks.fiveTierTieBreak(cards);
    }

}
