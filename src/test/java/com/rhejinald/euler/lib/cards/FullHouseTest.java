package com.rhejinald.euler.lib.cards;


import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.*;
import static com.rhejinald.euler.lib.cards.Suit.CLUB;
import static com.rhejinald.euler.lib.cards.Suit.HEART;
import static org.assertj.core.api.Assertions.assertThat;

public class FullHouseTest {


    @Test
    public void testTieBreakScore() throws Exception {
        FullHouse hand1 = new FullHouse(Lists.newArrayList(
                new Card(Card.ACE, HEART),
                new Card(Card.ACE, SPADE),
                new Card(Card.ACE, DIAMOND),
                new Card(7, CLUB),
                new Card(7, HEART)));
        FullHouse hand2 = new FullHouse(Lists.newArrayList(
                new Card(Card.KING, HEART),
                new Card(Card.KING, SPADE),
                new Card(Card.KING, DIAMOND),
                new Card(7, CLUB),
                new Card(7, HEART)));
        assertThat(hand1.test()).isTrue();
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    @Test
    public void testFullHouseSubLevel() throws Exception {
        FullHouse hand1 = new FullHouse(Lists.newArrayList(
                new Card(Card.ACE, HEART),
                new Card(Card.ACE, SPADE),
                new Card(Card.ACE, DIAMOND),
                new Card(7, CLUB),
                new Card(7, HEART)));
        FullHouse hand2 = new FullHouse(Lists.newArrayList(
                new Card(Card.ACE, HEART),
                new Card(Card.ACE, SPADE),
                new Card(Card.ACE, DIAMOND),
                new Card(6, CLUB),
                new Card(6, HEART)));
        assertThat(hand1.test()).isTrue();
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

}