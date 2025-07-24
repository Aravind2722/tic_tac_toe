package org.example.strategies.winning_strategies;

import org.example.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneColumnWinningStrategy implements  WinningStrategy {
    private List<Map<Symbol, Integer>> colMaps;
    public OrderOneColumnWinningStrategy(int size, List <Player> players) {
        this.colMaps = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            colMaps.add(new HashMap<>());
            for (Player player : players) {
                colMaps.get(i).put(player.getSymbol(), 0);
            }
        }
    }
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        Map <Symbol, Integer> colMap = colMaps.get(col);
        colMap.put(symbol, colMap.get(symbol) + 1);

        return colMap.get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        colMaps.get(col).put(symbol, colMaps.get(col).get(symbol) - 1);
    }
}
