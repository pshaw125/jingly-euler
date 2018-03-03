package com.rhejinald.euler.lib.cards;

import com.rhejinald.euler.lib.MathExt;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThreeOfAKind extends PokerHand {
    public ThreeOfAKind(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return PokerHandRank.THREE_OF_A_KIND;
    }

    @Override
    public boolean test() {
        Map<Integer, Long> instancesOfEach = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return instancesOfEach.keySet().size() == 3
                && instancesOfEach.values().containsAll(Lists.newArrayList(1L, 1L, 3L));
    }

    /**
     * Strategy:
     * 3 of a kind trumps other two cards
     * 20*20*3oaK + 20*mid + 1*min should give a unique value
     */
    @Override
    public long getTieBreakScore() {
        List<Integer> collect = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting())).entrySet().stream()
                .map(e -> e.getValue() == 3 ? Integer.valueOf(20*20 * e.getKey() * e.getKey()) : e.getKey())
                .collect(Collectors.toList());
        Integer threeOfAKind = Collections.max(collect);
        collect.remove(threeOfAKind);

        Integer min = Collections.min(collect);
        collect.remove(min);

        return MathExt.sum(threeOfAKind, collect.get(0) * 20, min);
    }
}
