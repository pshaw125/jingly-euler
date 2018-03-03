package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Suit.HEART;
import static com.rhejinald.euler.lib.cards.Suit.SPADE;
import static org.assertj.core.api.Assertions.assertThat;

public class StraightTest {
    @Test
    public void testTieBreakWithHighAce() throws Exception {
        Straight straight = new Straight(Lists.newArrayList(new Card(14, HEART), new Card(2, SPADE), new Card(3, HEART), new Card(4, HEART), new Card(5, HEART)));
        assertThat(straight.test()).isTrue();
        assertThat(straight.getTieBreakScore()).isEqualTo(5);
    }

    @Test
    public void testTieBreakWithRegularStraight() throws Exception {
        Straight straight = new Straight(Lists.newArrayList(new Card(6, HEART), new Card(2, SPADE), new Card(3, HEART), new Card(4, HEART), new Card(5, HEART)));
        assertThat(straight.test()).isTrue();
        assertThat(straight.getTieBreakScore()).isEqualTo(6);
    }

}