package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlackjackControllerTest {
    @Test
    void startGameThenInitializeCardsThatAreDealt() {
        Game game = new Game();
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        assertThat(game.playerHand().cards()).hasSize(2);
        assertThat(game.dealerHand().cards()).hasSize(2);
    }
}

