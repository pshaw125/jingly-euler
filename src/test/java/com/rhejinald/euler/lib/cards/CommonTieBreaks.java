package com.rhejinald.euler.lib.cards;

import com.rhejinald.euler.lib.MathExt;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommonTieBreaks {

    private static final int tier5 = 1;
    private static final int tier4 = tier5 * 15;
    private static final int tier3 = tier4 * 15;
    private static final int tier2 = tier3 * 15;
    private static final int tier1 = tier2 * 15;

    static long fiveTierTieBreak(List<Card> cards) {
        List<Integer> numerals = cards.stream().map(Card::getNumeral).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        return MathExt.sum(tier1 * numerals.get(0),
                tier2 * numerals.get(1),
                tier3 * numerals.get(2),
                tier4 * numerals.get(3),
                tier5 * numerals.get(4));
    }
}
