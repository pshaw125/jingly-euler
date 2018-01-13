package com.rhejinald.euler.lib.cards;


import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;

import static com.rhejinald.euler.lib.cards.Card.*;
import static com.rhejinald.euler.lib.cards.PokerHand.PokerHandRank.*;
import static com.rhejinald.euler.lib.cards.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PokerHandTest {
    @Test
    public void testStraightFlush() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(KING, HEART),
                new Card(QUEEN, HEART),
                new Card(JACK, HEART),
                new Card(TEN, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(STRAIGHT_FLUSH);
    }

    @Test
    public void testStraightFlushShiftedAndShuffled() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(KING, HEART),
                new Card(JACK, HEART),
                new Card(QUEEN, HEART),
                new Card(TEN, HEART),
                new Card(9, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(STRAIGHT_FLUSH);
    }

    @Test
    public void testStraightFlushLowAce() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(2, HEART),
                new Card(5, HEART),
                new Card(3, HEART),
                new Card(4, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(STRAIGHT_FLUSH);
    }

    @Test
    public void testFourOfAKind() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(KING, HEART),
                new Card(KING, SPADE),
                new Card(KING, CLUB),
                new Card(KING, DIAMOND),
                new Card(4, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(FOUR_OF_A_KIND);

        cards = Lists.newArrayList(
                new Card(7, SPADE),
                new Card(7, HEART),
                new Card(7, DIAMOND),
                new Card(7, CLUB),
                new Card(ACE, SPADE));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(FOUR_OF_A_KIND);
    }

    @Test
    public void testFullHouse() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(KING, HEART),
                new Card(KING, SPADE),
                new Card(KING, CLUB),
                new Card(4, DIAMOND),
                new Card(4, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(FULL_HOUSE);

        cards = Lists.newArrayList(
                new Card(KING, HEART),
                new Card(KING, SPADE),
                new Card(4, CLUB),
                new Card(4, DIAMOND),
                new Card(4, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(FULL_HOUSE);
    }

    @Test
    public void testFlush() throws Exception {
        ArrayList<Card> cards = Lists.newArrayList(
                new Card(KING, HEART),
                new Card(QUEEN, HEART),
                new Card(JACK, HEART),
                new Card(TEN, HEART),
                new Card(8, HEART));
        assertThat(new PokerHand(cards).getPokerHandRank()).isEqualTo(FLUSH);
    }
}