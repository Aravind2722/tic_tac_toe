package org.example.strategies.bot_playing_strategies;

import org.example.models.Board;
import org.example.models.Cell;

public interface BotPlayingStrategy {
    Cell makeMove(Board board);
}
