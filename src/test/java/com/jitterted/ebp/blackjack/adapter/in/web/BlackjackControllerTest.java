package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.DeckStub;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static com.jitterted.ebp.blackjack.domain.Rank.*;
import static com.jitterted.ebp.blackjack.domain.Suit.*;
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

    @SuppressWarnings("ConstantConditions")
    @Test
    void gameViewPopulatesViewModel() {
        Deck deck = new DeckStub(List.of(
            new Card(DIAMONDS, TEN),
            new Card(HEARTS, TWO),
            new Card(DIAMONDS, KING),
            new Card(CLUBS, THREE)));

        Game game = new Game(deck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.viewGame(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
            .isNotNull()
            .containsExactly("2♥", "3♣");
    }

    @Test
    void hitCommandDealsAdditionalCardToPlayer() {
        Deck deck = new DeckStub(List.of(
            new Card(DIAMONDS, TEN), //p
            new Card(HEARTS, TWO),   //d
            new Card(DIAMONDS, KING),//p
            new Card(CLUBS, THREE),  //d
            new Card(DIAMONDS, ACE) //p after hit
        ));
        Game game = new Game(deck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();
        String response = blackjackController.hitCommand();
        assertThat(game.playerHand().cards()).hasSize(3);
        assertThat(response).isEqualTo("redirect:/game");
    }

    @Test
    void testWhenPlayerIsDoneAfterBustingRedirectToDone() {
        Deck deck = new DeckStub(List.of(
            new Card(DIAMONDS, TEN),
            new Card(HEARTS, TWO),
            new Card(DIAMONDS, KING),
            new Card(CLUBS, THREE),
            new Card(DIAMONDS, SEVEN)
        ));

        Game game = new Game(deck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String response = blackjackController.hitCommand();
        assertThat(response).isEqualTo("redirect:/done");
    }
}

