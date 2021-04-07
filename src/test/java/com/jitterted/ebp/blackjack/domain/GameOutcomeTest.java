package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOutcomeTest {
    @Test
    void testPlayerLoses() {

        //Blackjack
        //1P. 2   ID
        //2D. 10  ID
        //3P. 2   ID
        //4D. J   ID
        //playHits
        //5P. Q

        DeckStub deckStub = new DeckStub(Rank.TWO, Rank.TEN, Rank.TWO, Rank.JACK, Rank.QUEEN);
        Game game = new Game(deckStub);
        game.initialDeal(); //2 cards each
        game.playerHits();

        assertThat(game.determineOutcome()).isEqualTo(GameOutcome.PLAYER_LOSES);
    }
}
