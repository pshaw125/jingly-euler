package com.rhejinald.euler.lib.cards;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.FOUR_OF_A_KIND;

public class FourOfAKind extends PokerHand {
    public FourOfAKind(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return FOUR_OF_A_KIND;
    }

    @Override
    public boolean test() {
        Map<Integer, Long> instancesOfEach = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return instancesOfEach.size() == 2 && instancesOfEach.values().containsAll(Sets.newHashSet(1L, 4L));
    }

    @Override
    public long getTieBreakScore() {
        return cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting())).entrySet().stream()
                .map(e -> e.getValue() == 4 ? Integer.valueOf(20 * e.getKey() * e.getKey()) : e.getKey())
                .mapToLong(e -> e)
                .sum();
    }
}
