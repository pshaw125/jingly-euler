package com.rhejinald.euler.lib.cards;


import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeOfAKindTest {
    @Test
    public void testTieBreakOnThreefer() throws Exception {
        ThreeOfAKind hand1 = new ThreeOfAKind(Lists.newArrayList(
                new Card(Card.QUEEN, HEART),
                new Card(Card.QUEEN, CLUB),
                new Card(Card.QUEEN, SPADE),
                new Card(2, CLUB),
                new Card(3, DIAMOND)));
        assertThat(hand1.test()).isTrue();

        ThreeOfAKind hand2 = new ThreeOfAKind(Lists.newArrayList(
                new Card(Card.JACK, HEART),
                new Card(Card.JACK, CLUB),
                new Card(Card.JACK, SPADE),
                new Card(Card.KING, HEART),
                new Card(Card.ACE, DIAMOND)));
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }
}