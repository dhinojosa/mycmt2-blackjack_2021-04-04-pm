package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleCard;
import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;
import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleHand;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    public static void main(String[] args) {
        displayWelcomeScreen();
        playGame();
        resetScreen();
    }

    private static void resetScreen() {
        System.out.println(ansi().reset());
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    private static void displayWelcomeScreen() {
        System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
    }

    public Game() {
        deck = new Deck();
    }

    protected Game(Deck deck) {
        this.deck = deck;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    public void play() {
        playerTurn(); //console

        dealerTurn(); //core domain

        ConsoleGame.displayFinalGameState(this); //console

        System.out.println(determineOutcome().message()); //mixed, printing outcome, but determining is core
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic
        // (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    private void playerTurn() {
        // get Player's decision: hit until they stand, then they're done (or
        // they go bust)

        while (!playerHand.isBusted()) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                playerHits(); //domain: extractmethod called playerHits?
                if (playerHand.isBusted()) {
                    return;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
    }

    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(dealerHand.displayFirstCard()); // first card is
        // Face Up

        // second card is the hole card, which is hidden
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        ConsoleHand.cardsAsString(playerHand);
        System.out.println(" (" + playerHand.displayValue() + ")");
    }

    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }
}
