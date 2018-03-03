package com.rhejinald.euler.lib.cards;

public class Card {
    static final int ACE_LOW = 1;
    static final int ACE = 14;
    static final int KING = 13;
    static final int QUEEN = 12;
    static final int JACK = 11;
    static final int TEN = 10;
    private final Suit suit;
    private final int numeral;

    /**
     * @param card of the format "QH" for the Queen of Hearts. T for 10.
     *             "1" will also be treated as an Ace. It's up to you to decide if it's treated as High or Low.
     */
    public Card(String card) {
        String numeral = card.substring(0, 1);
        this.numeral = parseNumeral(numeral);
        this.suit = Suit.toSuit(card.substring(1, 2));
    }

    public Card(int rawNumeral, Suit suit) {
        this.numeral = rawNumeral;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (numeral != card.numeral) return false;
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + numeral;
        return result;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNumeral() {
        return numeral;
    }

    private int parseNumeral(String numeral) {
        switch (numeral.toUpperCase()) {
            case "T":
                return TEN;
            case "J":
                return JACK;
            case "Q":
                return QUEEN;
            case "K":
                return KING;
            case "A":
            case "1":
                return ACE;
            default:
                return Integer.parseInt(numeral);
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", numeral=" + numeral +
                '}';
    }
}
