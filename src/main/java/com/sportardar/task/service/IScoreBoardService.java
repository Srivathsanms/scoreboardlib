package com.sportardar.task.service;



import com.sportardar.task.model.IGame;

import java.util.List;

public interface IScoreBoardService {
    IGame startGame(String homeTeam, String awayTeam);

    void finishGame(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    List<IGame> getOngoingGamesSummary();

    List<IGame> getFinishedGamesSummary();
}
