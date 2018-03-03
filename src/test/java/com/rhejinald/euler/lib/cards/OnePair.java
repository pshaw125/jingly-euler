package com.rhejinald.euler.lib.cards;

import com.rhejinald.euler.lib.MathExt;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHandRank.ONE_PAIR;

public class OnePair extends PokerHand {
    private static final int tier4 = 1;
    private static final int tier3 = tier4 * 14;
    private static final int tier2 = tier3 * 14;
    private static final int tier1 = tier2 * 14;
    public OnePair(List<Card> cards) {
        super(cards);
    }

    @Override
    public PokerHandRank getHandRank() {
        return ONE_PAIR;
    }

    @Override
    public boolean test() {
        Collection<Long> values = cards.stream().map(Card::getNumeral)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting())).values();
        return values.size() == 4 && values.containsAll(Lists.newArrayList(2L, 1L, 1L, 1L));
    }

    @Override
    public long getTieBreakScore() {
        Map<Integer, Long> frequencyOfNumerals = cards.stream().map(Card::getNumeral).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        int tier1Val = tier1 * (frequencyOfNumerals.entrySet().stream().filter(e -> e.getValue() == 2L).findFirst().get().getKey());
        List<Integer> singleNumerals = frequencyOfNumerals.entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        return MathExt.sum(tier1Val,
                tier2 * singleNumerals.get(0),
                tier3 * singleNumerals.get(1),
                tier4 * singleNumerals.get(2));
    }
}
