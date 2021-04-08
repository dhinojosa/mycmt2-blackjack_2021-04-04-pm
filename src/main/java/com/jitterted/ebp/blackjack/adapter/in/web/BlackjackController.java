package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.Supplier;

@Controller
public class BlackjackController {

    private Game game;//current Game
    private final Supplier<Game> gameSupplier;

    public BlackjackController(Supplier<Game> gameSupplier) {
        this.gameSupplier = gameSupplier;
    }

    @PostMapping("/start-game")
    public String startGame() {
        game = gameSupplier.get();
        game.initialDeal();
        return "redirect:/game";
    }

    @GetMapping("/game")
    public String viewGame(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        if (game.playerHand().isBusted()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    @GetMapping("/done")
    public String gameDone(Model model) {
        game.dealerTurn();
        model.addAttribute("outcome", game.determineOutcome().message());
        model.addAttribute("gameView", GameView.of(game));
        return "done";
    }

    @PostMapping("/stand")
    public String standCommand() {
        return "redirect:/done";
    }
}
