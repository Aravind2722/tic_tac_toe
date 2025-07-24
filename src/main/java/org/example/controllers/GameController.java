package org.example.controllers;

import org.example.exceptions.InvalidGameParamsException;
import org.example.models.Game;
import org.example.models.GameStatus;
import org.example.models.Player;
import org.example.strategies.winning_strategies.WinningStrategy;

import java.util.List;

public class GameController {
    // Instead of passing game object as parameters for every method here, we could have simply had a game reference object in here.
    // But, in future, if we want to support multiple games happening parallely via different threads, we can use the same controller to work with different objects of the games running paralley
    // In that case if we had a shared reference, we cant make multiple games running paralley as it would require different instances of controllers according to instances of different games.
    public Game createGame(int size, List<Player> players, List<WinningStrategy> winningStrategies) throws InvalidGameParamsException {
        return Game.getBuilder()
                .setSize(size)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void displayBoard(Game game) {
        game.displayBoard();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void undo(Game game) {
        game.undo();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }


    public void printResult(Game game) {
        game.displayResult();
    }
}
