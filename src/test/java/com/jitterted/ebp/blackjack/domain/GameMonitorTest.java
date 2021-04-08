package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GameMonitorTest {

    @Test
    public void playerStandsCompletesGameSendsToMonitor() {
        // creates the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(new Deck(), gameMonitorSpy);
        game.initialDeal();

        game.playerStands();

        // verify that the roundCompleted method was called with any instance of a Game class
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsGoesBustThenGameSendsToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Deck deck = new DeckStub(Rank.TEN, Rank.TWO, Rank.THREE, Rank.JACK, Rank.QUEEN);

        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsDoesNotBustThenResultNotSentToMonitor() {

        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Deck deck = new DeckStub(Rank.TEN, Rank.TWO, Rank.THREE, Rank.JACK, Rank.TWO);

        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }

}
