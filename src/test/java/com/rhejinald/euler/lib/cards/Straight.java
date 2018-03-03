package com.rhejinald.euler.lib.cards;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.STRAIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class Straight extends PokerHand {
    public Straight(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return STRAIGHT;
    }

    @Override
    public boolean test() {
        Set<Integer> values = cards.stream().map(Card::getNumeral).collect(Collectors.toSet());
        if (values.contains(2) && values.remove(Card.ACE)) {
            values.add(Card.ACE_LOW);
        }
        return values.size() == 5 && (Collections.max(values) - Collections.min(values) == 4);
    }

    @Override
    public long getTieBreakScore() {
        return cards.stream()
                .map(Card::getNumeral)
                .collect(Collectors.toSet())
                .stream()
                .filter(e -> e != Card.ACE)
                .mapToLong(e -> e)
                .max().getAsLong();
    }


}
