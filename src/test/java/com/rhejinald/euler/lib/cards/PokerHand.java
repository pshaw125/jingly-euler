package com.rhejinald.euler.lib.cards;

import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.rhejinald.euler.lib.cards.PokerHand.PokerHandRank.*;

public class PokerHand {
    private static final ArrayList<PokerHandRank> HAND_RANKS_DESCENDING = Lists.newArrayList(STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD);
    private final PokerHandRank pokerHandRank;
    private final List<Card> cards;
    private final int tieBreakerScore;

    public PokerHand(List<Card> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("Too many or too few cards");
        }
        this.pokerHandRank = getHandTier(cards);
        this.cards = cards;
        tieBreakerScore = 0;
    }

    public PokerHandRank getPokerHandRank() {
        return pokerHandRank;
    }

    private PokerHandRank getHandTier(List<Card> cards) {
        for (PokerHandRank handRank : HAND_RANKS_DESCENDING) {
            if (handRank.test(cards)) {
                return handRank;
            }
        }
        throw new IllegalArgumentException("No hands matched what should have been at least a High Card");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokerHand other = (PokerHand) o;

        return cards != null
                ? cards.size() == other.cards.size() && cards.containsAll(other.cards)
                : other.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }

    public boolean winsAgainst(PokerHand other) {
        return false;
    }

    public List<Card> getCards() {
        return cards;
    }

    enum PokerHandRank {
        HIGH_CARD(0, (hand) -> true),
        ONE_PAIR(1, (hand) -> false),
        TWO_PAIR(2, (hand) -> false),
        THREE_OF_A_KIND(3, PokerHandRank::isThreeOfAKind),
        STRAIGHT(4, PokerHandRank::isStraight),
        FLUSH(5, PokerHandRank::isFlush),
        FULL_HOUSE(6, PokerHandRank::isFullHouse),
        FOUR_OF_A_KIND(7, PokerHandRank::isFourOfAKind),
        STRAIGHT_FLUSH(8, hand -> isStraight(hand) && isFlush(hand));

        private final int numericalTier;
        private final Predicate<List<Card>> isHandOfType;

        PokerHandRank(int numericalTier, Predicate<List<Card>> isHandOfType) {
            this.numericalTier = numericalTier;
            this.isHandOfType = isHandOfType;
        }

        private static boolean isFourOfAKind(List<Card> hand) {
            List<Integer> instances = hand.stream().map(Card::getNumeral).collect(Collectors.toList());
            return Sets.newHashSet(instances).size() == 2
                    && (instances.stream().filter(e -> e.equals(instances.get(0))).count() == 4
                    || instances.stream().filter(e -> e.equals(instances.get(1))).count() == 4);
        }

        private static boolean isFullHouse(List<Card> hand) {
            Map<Integer, Long> instancesOfEach = hand.stream().map(Card::getNumeral)
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return instancesOfEach.keySet().size() == 2
                    && instancesOfEach.values().containsAll(Lists.newArrayList(2L, 3L));
        }

        private static boolean isFlush(List<Card> hand) {
            return hand.stream().map(Card::getSuit).collect(Collectors.toSet()).size() == 1;
        }

        private static boolean isStraight(List<Card> hand) {
            Set<Integer> values = hand.stream().map(Card::getNumeral).collect(Collectors.toSet());
            if (values.contains(2) && values.remove(Card.ACE)) {
                values.add(Card.ACE_LOW);
            }
            return values.size() == 5 && (Collections.max(values) - Collections.min(values) == 4);
        }

        private static boolean isThreeOfAKind(List<Card> hand) {
            Map<Integer, Long> instancesOfEach = hand.stream().map(Card::getNumeral)
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return instancesOfEach.keySet().size() == 3
                    && instancesOfEach.values().containsAll(Lists.newArrayList(1, 1, 3));
        }

        private boolean test(List<Card> hand) {
            return isHandOfType.test(hand);
        }
    }


}
