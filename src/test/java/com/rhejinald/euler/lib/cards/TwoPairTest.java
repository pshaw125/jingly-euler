package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.*;
import static com.rhejinald.euler.lib.cards.Suit.DIAMOND;
import static com.rhejinald.euler.lib.cards.Suit.HEART;
import static org.assertj.core.api.Assertions.assertThat;

public class TwoPairTest {


    @Test
    public void testTieBreakOnFirstPair() throws Exception {
        TwoPair hand1 = new TwoPair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.KING, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        TwoPair hand2 = new TwoPair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.QUEEN, CLUB),
                new Card(Card.TEN, SPADE),
                new Card(Card.QUEEN, HEART),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    @Test
    public void testTieBreakOnSecondPair() throws Exception {
        TwoPair hand1 = new TwoPair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.KING, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        TwoPair hand2 = new TwoPair(Lists.newArrayList(
                new Card(9, HEART),
                new Card(Card.KING, CLUB),
                new Card(9, SPADE),
                new Card(Card.KING, HEART),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    @Test
    public void testTieBreakOnHighCard() throws Exception {
        TwoPair hand1 = new TwoPair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.KING, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        TwoPair hand2 = new TwoPair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.KING, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.QUEEN, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }
}