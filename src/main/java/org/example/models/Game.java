package org.example.models;

import org.example.strategies.WinningStrategy;

import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private List<Move> moves;
}
