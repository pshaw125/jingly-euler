package com.rhejinald.euler.lib.cards;

import java.util.List;

import static com.rhejinald.euler.lib.cards.PokerHandRank.STRAIGHT_FLUSH;

public class StraightFlush extends PokerHand {

    private final Flush flush;
    private final Straight straight;

    public StraightFlush(List<Card> cards) {
        super(cards);
        flush = new Flush(cards);
        straight = new Straight(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return STRAIGHT_FLUSH;
    }

    @Override
    public boolean test() {
        return flush.test() && straight.test();
    }

    @Override
    public long getTieBreakScore() {
        return straight.getTieBreakScore();
    }
}
