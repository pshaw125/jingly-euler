package com.rhejinald.euler.lib.cards;

import com.rhejinald.euler.lib.MathExt;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.TWO_PAIR;

public class TwoPair extends PokerHand {
    public TwoPair(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return TWO_PAIR;
    }

    @Override
    public boolean test() {
        Collection<Long> values = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting())).values();
        return values.size() == 3 && values.containsAll(Lists.newArrayList(2L, 2L, 1L));
    }

    @Override
    public long getTieBreakScore() {
        Map<Integer, Long> occurrencesOfNumerals = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        List<Integer> twoPairNumerals = occurrencesOfNumerals.entrySet().stream()
                .filter(e -> e.getValue() == 2L)
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        Integer singleCardNumeral = occurrencesOfNumerals.entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()).get(0);
        return MathExt.sum(twoPairNumerals.get(0)*20*20, twoPairNumerals.get(1)*20, singleCardNumeral);
    }
}
