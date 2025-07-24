package org.example.strategies.winning_strategies;

import org.example.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneDiagonalWinningStrategy implements WinningStrategy {
    private Map<Symbol, Integer> leftDiagonalMap;
    private Map<Symbol, Integer> rightDiagonalMap;
    public OrderOneDiagonalWinningStrategy(List<Player> players) {
        leftDiagonalMap = new HashMap<>();
        rightDiagonalMap = new HashMap<>();

        for (Player player : players) {
            leftDiagonalMap.put(player.getSymbol(), 0);
            rightDiagonalMap.put(player.getSymbol(), 0);
        }
    }
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow(), col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if (row == col) {
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) + 1);
        }

        if (row + col == board.getSize() -1) {
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) + 1);
        }

        // Why did we not check for winner in the above if else blocks itself?
        // Because, lets say a player wins by adding his symbol in the middle cell. The middle cell is common for both left and right diagonals
        // if we validate and return true after updating the left diagonal itself, we would miss updating the right diagonal
        // And in the future, lets say undo feature is made available even after somebody wins... We would end up missing this update in the right diagonal
        // Hence, we are first updating both the diagonals with the change, and then we check for the winnner.

        if (row == col) {
            if (leftDiagonalMap.get(symbol) == board.getSize()) return true;
        }

        if (row + col == board.getSize() -1) {
            if (rightDiagonalMap.get(symbol) == board.getSize()) return true;
        }
        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow(), col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if (row == col) {
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) - 1);
        }
        if (row + col == board.getSize()-1) {
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) - 1);
        }
    }
}
