package org.example.models;

import java.util.Scanner;

public class Player {
    private Scanner scanner;
    private String name;
    private Symbol symbol;
    private PlayerType playerType; // It is a good practice to have the type attribute in the parent class, so that it will be commonly present at one place for all the child instances too

    // Need for PlayerType parameter in the constructor:
    //  1. While instantiating a Human player, we don't need to specify the player type, we can directly call the constructor of the player class.
    //  2. But if child class is being instantiated, we would chain its constructor to the parent class constructor. And as the type attribute is commonly present in the parent class, we need to add it in parent class parameter as well

    public Player(String name, Symbol symbol, PlayerType playerType) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
    public Cell makeMove(Board board) {
        // We don't need board as a parameter here, as we would get inputs directly from the user.
        // Our goal is to override this makeMove method by the child class 'Bot', so that we can achieve Liskov's Substitution.
        // For the child makeMove method, seems like board parameter is needed. Hence we put the same parameter here too.
        System.out.println("Enter row number (row number starts from 0): ");
        int row = scanner.nextInt();

        System.out.println("Enter column number (column number starts from 0): ");
        int col = scanner.nextInt();

        return new Cell(row, col);
    }
}
