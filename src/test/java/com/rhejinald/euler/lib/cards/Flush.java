package com.rhejinald.euler.lib.cards;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.FLUSH;
import static org.assertj.core.api.Assertions.assertThat;

public class Flush extends PokerHand {
    public Flush(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return FLUSH;
    }

    @Override
    public boolean test() {
        return cards.stream().map(Card::getSuit).collect(Collectors.toSet()).size() == 1;
    }

    @Override
    public long getTieBreakScore() {
        return CommonTieBreaks.fiveTierTieBreak(cards);
    }


}
