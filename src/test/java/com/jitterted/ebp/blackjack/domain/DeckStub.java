package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeckStub extends Deck {

    private final Iterator<Card> iterator;

    public DeckStub(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(Suit.DIAMONDS, rank));
        }
        this.iterator = cards.iterator();
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
