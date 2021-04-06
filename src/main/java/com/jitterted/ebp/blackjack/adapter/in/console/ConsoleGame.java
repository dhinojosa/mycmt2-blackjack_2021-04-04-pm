package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Game;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {
    public static void displayFinalGameState(Game game) {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        ConsoleHand.cardsAsString(game.dealerHand());
        System.out.println(" (" + game.dealerHand().displayValue() + ")");

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.cardsAsString(game.playerHand());
        System.out.println(" (" + game.playerHand().displayValue() + ")");
    }
}
