package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;
import com.jitterted.ebp.blackjack.domain.Game;

public class Blackjack {
    public static void main(String[] args) {
        Game game = new Game();
        ConsoleGame consoleGame = new ConsoleGame(game);
        consoleGame.start();
    }
}
