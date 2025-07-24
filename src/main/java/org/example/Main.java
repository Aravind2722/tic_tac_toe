package org.example;

import org.example.controllers.GameController;
import org.example.exceptions.InvalidGameParamsException;
import org.example.models.*;
import org.example.strategies.bot_playing_strategies.BotPlayingStrategy;
import org.example.strategies.winning_strategies.OrderOneColumnWinningStrategy;
import org.example.strategies.winning_strategies.OrderOneDiagonalWinningStrategy;
import org.example.strategies.winning_strategies.OrderOneRowWinningStrategy;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create players
        List<Player> players = List.of(
                new Bot("Com", new Symbol('O'), BotDifficultyLevel.EASY),
                new Player("Aravind", new Symbol('X'), PlayerType.HUMAN),
                new Player("Harish", new Symbol('Y'), PlayerType.HUMAN)
        );

        // Create Game
        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController();
        int size = 4;
        Game game;
        try {
            game = gameController.createGame(
                    size,
                    players,
                    List.of(
                            new OrderOneRowWinningStrategy(size, players),
                            new OrderOneColumnWinningStrategy(size, players),
                            new OrderOneDiagonalWinningStrategy(players)
                    )
            );
        }catch (InvalidGameParamsException e) {
            System.out.println(e.getMessage());
            return;
        }

        // While Game is in progress, continue the game;
        System.out.println("----------------- Game Starts... -----------------");
        while (gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {
            //  - Display the board
            System.out.println("''''''''''Board''''''''''");
            gameController.displayBoard(game);
            //  - Display undo option
            System.out.println("Does any player want to undo the last move (y/n): ");
            String verdict = scanner.next();
            if (verdict.equalsIgnoreCase("y")) {
                gameController.undo(game);
            }else {
                //  - Get Inputs for turns
                gameController.makeMove(game);
            }
        }
        // Handle Winner or DRAW at last
        gameController.printResult(game);
        gameController.displayBoard(game);
    }
}