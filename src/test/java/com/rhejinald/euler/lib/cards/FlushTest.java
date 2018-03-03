package com.rhejinald.euler.lib.cards;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.rhejinald.euler.lib.cards.Card.*;
import static com.rhejinald.euler.lib.cards.Card.TEN;
import static com.rhejinald.euler.lib.cards.Suit.HEART;
import static org.assertj.core.api.Assertions.assertThat;

public class FlushTest {
    @Test
    public void testTieBreakFlush() throws Exception {
        Flush aceHighFlush = new Flush(
                Lists.newArrayList(
                        new Card(ACE, HEART),
                        new Card(2, HEART),
                        new Card(3, HEART),
                        new Card(4, HEART),
                        new Card(6, HEART)));
        assertThat(aceHighFlush.test()).isTrue(); //sanity: validate is a flush

        Flush kingHighFlush = new Flush(Lists.newArrayList(
                new Card(KING, HEART),
                new Card(QUEEN, HEART),
                new Card(JACK, HEART),
                new Card(TEN, HEART),
                new Card(8, HEART)));
        assertThat(kingHighFlush.test()).isTrue(); //sanity: validate is a flush

        assertThat(aceHighFlush.getTieBreakScore()).isGreaterThan(kingHighFlush.getTieBreakScore());
    }

    @Test
    public void testTieBreakFlushOnSecondCard() throws Exception {
        Flush aceKing = new Flush(
                Lists.newArrayList(
                        new Card(ACE, HEART),
                        new Card(KING, HEART),
                        new Card(4, HEART),
                        new Card(3, HEART),
                        new Card(2, HEART)));
        assertThat(aceKing.test()).isTrue(); //sanity: validate is a flush

        Flush aceQueen = new Flush(Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(QUEEN, HEART),
                new Card(JACK, HEART),
                new Card(TEN, HEART),
                new Card(9, HEART)));
        assertThat(aceQueen.test()).isTrue(); //sanity: validate is a flush

        assertThat(aceKing.getTieBreakScore()).isGreaterThan(aceQueen.getTieBreakScore());
    }

    @Test
    public void testTieBreakFlushOnThirdCard() throws Exception {
        Flush aceKingQueen = new Flush(
                Lists.newArrayList(
                        new Card(ACE, HEART),
                        new Card(KING, HEART),
                        new Card(QUEEN, HEART),
                        new Card(3, HEART),
                        new Card(2, HEART)));
        assertThat(aceKingQueen.test()).isTrue(); //sanity: validate is a flush

        Flush aceKingJack = new Flush(Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(KING, HEART),
                new Card(JACK, HEART),
                new Card(TEN, HEART),
                new Card(9, HEART)));
        assertThat(aceKingJack.test()).isTrue(); //sanity: validate is a flush

        assertThat(aceKingQueen.getTieBreakScore()).isGreaterThan(aceKingJack.getTieBreakScore());
    }

    @Test
    public void testTieBreakFlushOnFourthCard() throws Exception {
        Flush aceKingQueenJack = new Flush(
                Lists.newArrayList(
                        new Card(ACE, HEART),
                        new Card(KING, HEART),
                        new Card(QUEEN, HEART),
                        new Card(JACK, HEART),
                        new Card(2, HEART)));
        assertThat(aceKingQueenJack.test()).isTrue(); //sanity: validate is a flush

        Flush aceKingQueenTen = new Flush(Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(KING, HEART),
                new Card(QUEEN, HEART),
                new Card(TEN, HEART),
                new Card(9, HEART)));
        assertThat(aceKingQueenTen.test()).isTrue(); //sanity: validate is a flush

        assertThat(aceKingQueenJack.getTieBreakScore()).isGreaterThan(aceKingQueenTen.getTieBreakScore());
    }

    @Test
    public void testTieBreakFlushOnFinalCard() throws Exception {
        Flush low8 = new Flush(
                Lists.newArrayList(
                        new Card(ACE, HEART),
                        new Card(KING, HEART),
                        new Card(QUEEN, HEART),
                        new Card(JACK, HEART),
                        new Card(8, HEART)));
        assertThat(low8.test()).isTrue(); //sanity: validate is a flush

        Flush low9 = new Flush(Lists.newArrayList(
                new Card(ACE, HEART),
                new Card(KING, HEART),
                new Card(QUEEN, HEART),
                new Card(JACK, HEART),
                new Card(9, HEART)));
        assertThat(low9.test()).isTrue(); //sanity: validate is a flush

        assertThat(low9.getTieBreakScore()).isGreaterThan(low8.getTieBreakScore());
    }
}