package com.sportardar.task.model;

import java.time.LocalDateTime;

public interface IGame {
    String getHomeTeam();

    String getAwayTeam();

    int getHomeScore();

    int getAwayScore();

    int getTotalScore();

    IGame updateScores(int homeScore, int awayScore);

    LocalDateTime getTimestamp();
}
