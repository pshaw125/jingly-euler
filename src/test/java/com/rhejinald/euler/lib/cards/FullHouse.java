package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.FULL_HOUSE;

public class FullHouse extends PokerHand {
    private static final int THREE_PART_MULTIPLIER = 20;
    private static final int TWO_PART_MULTIPLIER = 1;

    public FullHouse(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return FULL_HOUSE;
    }

    @Override
    public boolean test() {
        Map<Integer, Long> instancesOfEach = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return instancesOfEach.keySet().size() == 2
                && instancesOfEach.values().containsAll(Lists.newArrayList(2L, 3L));
    }

    @Override
    public long getTieBreakScore() {
        return cards.stream()
                .map(Card::getNumeral)
                .collect(Collectors.groupingBy(e1 -> e1, Collectors.counting()))
                .entrySet().stream()
                .map(e -> e.getValue() == 3L ? e.getKey() * THREE_PART_MULTIPLIER : e.getKey() * TWO_PART_MULTIPLIER)
                .mapToLong(e -> e)
                .sum();
    }
}
