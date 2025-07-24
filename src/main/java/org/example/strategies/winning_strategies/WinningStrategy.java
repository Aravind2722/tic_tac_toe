package org.example.strategies.winning_strategies;

import org.example.models.Board;
import org.example.models.Cell;
import org.example.models.Move;

public interface WinningStrategy {

    boolean checkWinner(Board board, Move move);
    void handleUndo(Board board, Move move);
    // We dont need board as a parameter for row and col strategy... But in diagonal strategy, the right diagonal will be found only if row + col == board.size() - 1
    // Hence we are forced to have this parameter in all the other classes' methods as well
    // BTW, in diagonal strategy, the maps are of size of total number of players... And the number of players are board.size()-1.
    // We did not choose to use maps's size to check for right diagonal as it is not a consistent way.. So we prefer to share board as a parameter everywhere

}
