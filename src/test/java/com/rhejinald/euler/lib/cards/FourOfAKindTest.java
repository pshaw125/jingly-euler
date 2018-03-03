package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FourOfAKindTest {


    @Test
    public void testTieBreakScore() throws Exception {
        FourOfAKind hand1 = new FourOfAKind(Lists.newArrayList(
                new Card(Card.ACE, HEART),
                new Card(Card.ACE, SPADE),
                new Card(Card.ACE, DIAMOND),
                new Card(Card.ACE, CLUB),
                new Card(7, HEART)));
        FourOfAKind hand2 = new FourOfAKind(Lists.newArrayList(
                new Card(Card.KING, HEART),
                new Card(Card.KING, SPADE),
                new Card(Card.KING, DIAMOND),
                new Card(Card.KING, CLUB),
                new Card(7, HEART)));
        assertThat(hand1.test()).isTrue();
        assertThat(hand2.test()).isTrue();

        assertThat(hand1.getTieBreakScore()).isGreaterThan(hand2.getTieBreakScore());
    }

    /**
     * Motive: We can't just evaluate hands being better by counting digit sums; the 3-4oaK is better than any 2-4oaK.
     */
    @Test
    public void testTieBreakScoreEdgeCase() throws Exception {
        FourOfAKind hand1 = new FourOfAKind(Lists.newArrayList(
                new Card(2, HEART),
                new Card(2, SPADE),
                new Card(2, DIAMOND),
                new Card(2, CLUB),
                new Card(Card.ACE, HEART)));
        FourOfAKind hand2 = new FourOfAKind(Lists.newArrayList(
                new Card(3, HEART),
                new Card(3, SPADE),
                new Card(3, DIAMOND),
                new Card(3, CLUB),
                new Card(4, HEART)));
        assertThat(hand1.test()).isTrue();
        assertThat(hand2.test()).isTrue();

        assertThat(hand2.getTieBreakScore()).isGreaterThan(hand1.getTieBreakScore());
    }
}