package org.example.factories;

import org.example.models.BotDifficultyLevel;
import org.example.strategies.bot_playing_strategies.BotPlayingStrategy;
import org.example.strategies.bot_playing_strategies.EasyBotPlayingStrategy;
import org.example.strategies.bot_playing_strategies.HardBotPlayingStrategy;
import org.example.strategies.bot_playing_strategies.MediumBotPlayingStrategy;

import java.rmi.MarshalException;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategyForDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        return switch (botDifficultyLevel) {
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
        };
    }
}
