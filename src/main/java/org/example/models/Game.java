package org.example.models;

import org.example.exceptions.InvalidGameParamsException;
import org.example.strategies.winning_strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private List<Move> moves;

    private Game(int size, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(size);
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.moves = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public static Builder getBuilder() {
        return new Builder();
    }
    public static class Builder {
        private int size;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public int getSize() {
            return size;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public List<WinningStrategy> getWinningStrategies() {
            return winningStrategies;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Game build() throws InvalidGameParamsException {
            if (!valid()) throw new InvalidGameParamsException("Invalid parameters for game!");

            return new Game(this.size, this.players, this.winningStrategies);
        }
        private boolean valid() {
            if (size < 3) return false;
            if (this.players.size() != size - 1) return false;

            int bots = 0;
            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) bots ++;
            }
            if (bots > 1) return false;

            Set<Character> symbols = new HashSet <>();
            for (Player player : players) {
                if (symbols.contains(player.getSymbol().getSymbol())) return false;
                symbols.add(player.getSymbol().getSymbol());
            }

            return true;
        }

    }
    public void displayBoard() {
        this.board.displayBoard();
    }

    public void displayResult() {
        if (this.getGameStatus().equals(GameStatus.ENDED)) {
            System.out.println("Game Ended!");
            System.out.println("Winner is: " + this.getWinner().getName() + "!");
        }else if (this.getGameStatus().equals(GameStatus.DRAW)) {
            System.out.println("Game Ended in a DRAW!");
        }else {
            System.out.println("Game is still in progress...");
        }
    }


    public void makeMove() {
        Player currentPlayer = this.getPlayers().get(this.getCurrentPlayerIndex());
        System.out.println("It is "+ currentPlayer.getName() +"'s turn.");

        Cell proposedCell = currentPlayer.makeMove(this.getBoard());

        if (!isMoveValid(proposedCell)) {
            System.out.println("Invalid move. Please try again!");
            return;
        }

        Cell cellInBoard = this.getBoard().getBoard().get(proposedCell.getRow()).get(proposedCell.getCol());
        cellInBoard.setCellStatus(CellStatus.FILLED);
        cellInBoard.setPlayer(currentPlayer);

        Move move = new Move(cellInBoard, currentPlayer);
        this.getMoves().add(move);

        if (checkIfGameWon(move)) return;
        if (checkIfGameDraw()) return;

        this.setCurrentPlayerIndex(this.getCurrentPlayerIndex()+1);
        this.setCurrentPlayerIndex(this.getCurrentPlayerIndex() % this.getPlayers().size());
//        System.out.println("current player index: "+ this.getCurrentPlayerIndex());

    }
    private boolean checkIfGameWon(Move currentMove) {
        for (WinningStrategy winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(this.getBoard(), currentMove)) {
                this.setGameStatus(GameStatus.ENDED);
                this.setWinner(currentMove.getPlayer());
                return true;
            }
        }

        return false;
    }
    private boolean checkIfGameDraw() {
         if (this.getMoves().size() == this.getBoard().getSize() * this.getBoard().getSize()) {
             this.setGameStatus(GameStatus.DRAW);
             return true;
         }
         return false;
    }


    public boolean isMoveValid(Cell proposedCell) {
        int row = proposedCell.getRow(), col = proposedCell.getCol();
        if ((row < 0) || (col < 0) || (row >= this.getBoard().getSize()) || (col >= this.getBoard().getSize())) return false;
        return this.getBoard().getBoard().get(row).get(col).getCellStatus().equals(CellStatus.EMPTY);
    }

    public void undo() {
        if (this.getMoves().isEmpty()) {
            System.out.println("No moves to undo. Please make a move!");
            return;
        }
        Move lastMove = this.getMoves().getLast();
        for (WinningStrategy winningStrategy : this.getWinningStrategies()) {
            winningStrategy.handleUndo(this.getBoard(), lastMove);
        }

        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellStatus(CellStatus.EMPTY);

        this.getMoves().removeLast();

        this.setCurrentPlayerIndex(this.getCurrentPlayerIndex() - 1);
        this.setCurrentPlayerIndex(this.getCurrentPlayerIndex() + this.getPlayers().size());
        this.setCurrentPlayerIndex(this.getCurrentPlayerIndex() % this.getPlayers().size());

        System.out.println("Last Move undone!");
//        System.out.println("Current player index: "+this.getCurrentPlayerIndex());
    }

}
