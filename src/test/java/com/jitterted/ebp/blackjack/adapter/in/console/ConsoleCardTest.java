package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleCardTest {
    private static final Rank DUMMY_RANK = Rank.TEN;

    @Test
    public void displayAsTenCard() throws Exception {
        Card tenCard = new Card(Suit.CLUBS, Rank.TEN);

        Assertions.assertThat(ConsoleCard.display(tenCard))
                  .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    public void displayNonTenCard() throws Exception {
        Card threeCard = new Card(Suit.DIAMONDS, Rank.THREE);

        assertThat(ConsoleCard.display(threeCard))
            .isEqualTo("[31m┌─────────┐[1B[11D│3        │[1B[11D│         │[1B[11D│    ♦    │[1B[11D│         │[1B[11D│        3│[1B[11D└─────────┘");
    }

    @Test
    public void suitOfHeartsOrDiamondsIsDisplayedInRed() throws Exception {
        // given a card with Hearts or Diamonds
        Card heartsCard = new Card(Suit.HEARTS, DUMMY_RANK);
        Card diamondsCard = new Card(Suit.DIAMONDS, DUMMY_RANK);

        // when we ask for its display representation
        String ansiRedString = ansi().fgRed().toString();

        // then we expect a red color ansi sequence
        assertThat(heartsCard.display())
            .contains(ansiRedString);
        assertThat(diamondsCard.display())
            .contains(ansiRedString);
    }
}
