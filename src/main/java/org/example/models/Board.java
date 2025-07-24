package org.example.models;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> board;

    public Board(int size) {
        this.size = size;
        this.board = new ArrayList<>();
        for (int i = 0; i < this.size; i ++) {
            this.board.add(new ArrayList<>());
            for (int j = 0; j < this.size; j ++) {
                this.board.get(i).add(new Cell(i, j));
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
    public void displayBoard() {
        for (List<Cell> cells : this.board) {
            for (Cell cell : cells) {
                cell.displayCell();
            }
            System.out.println();
        }
    }
}
