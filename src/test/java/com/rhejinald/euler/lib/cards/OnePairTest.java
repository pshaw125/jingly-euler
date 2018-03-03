package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OnePairTest {
    @Test
    public void testTieBreakOnFirstCards() throws Exception {
        OnePair hand1 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, HEART),
                new Card(Card.QUEEN, CLUB),
                new Card(Card.JACK, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        OnePair hand2 = new OnePair(Lists.newArrayList(
                new Card(Card.JACK, HEART),
                new Card(Card.JACK, SPADE),
                new Card(2, HEART),
                new Card(4, CLUB),
                new Card(3, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand2.getTieBreakScore()).isGreaterThan(hand1.getTieBreakScore());
    }

    @Test
    public void testTieBreakOnSecondCard() throws Exception {
        OnePair hand1 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, HEART),
                new Card(2, CLUB),
                new Card(3, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        OnePair hand2 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.QUEEN, CLUB),
                new Card(Card.JACK, HEART),
                new Card(9, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    @Test
    public void testTieBreakOnThirdCard() throws Exception {
        OnePair hand1 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, HEART),
                new Card(Card.QUEEN, CLUB),
                new Card(3, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        OnePair hand2 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.JACK, HEART),
                new Card(9, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    @Test
    public void testTieBreakOnFourthCard() throws Exception {
        OnePair hand1 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, HEART),
                new Card(Card.QUEEN, CLUB),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        OnePair hand2 = new OnePair(Lists.newArrayList(
                new Card(Card.TEN, HEART),
                new Card(Card.TEN, SPADE),
                new Card(Card.KING, CLUB),
                new Card(Card.QUEEN, HEART),
                new Card(9, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }
}
