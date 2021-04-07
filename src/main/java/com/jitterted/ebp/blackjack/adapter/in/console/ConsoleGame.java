package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Game;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {
    private final Game game;

    public ConsoleGame(Game game) {
        this.game = game;
    }

    public void start() {
        displayWelcomeScreen();
        playGame();
        resetScreen();
    }

    private void playGame() {
        Game game = new Game();
        game.initialDeal();
        playerTurn(); //console

        game.dealerTurn(); //core domain

        displayFinalGameState(); //console

        System.out.println(game.determineOutcome().message()); //console
    }

    public void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        ConsoleHand.cardsAsString(game.dealerHand());
        System.out.println(" (" + game.dealerHand().displayValue() + ")");

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.cardsAsString(game.playerHand());
        System.out.println(" (" + game.playerHand().displayValue() + ")");
    }

    public void resetScreen() {
        System.out.println(ansi().reset());
    }

    public void displayWelcomeScreen() {
        System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
    }

    public void playerTurn() {
        // get Player's decision: hit until they stand, then they're done (or
        // they go bust)

        while (!game.playerHand().isBusted()) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                game.playerHits();
                if (game.playerHand().isBusted()) {
                    return;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
    }

    public void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(game.dealerHand().displayFirstCard()); // first
        // card is
        // Face Up

        // second card is the hole card, which is hidden
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.cardsAsString(game.playerHand());
        System.out.println(" (" + game.playerHand().displayValue() + ")");
    }

    public String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
