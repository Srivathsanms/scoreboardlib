package com.sportardar.task.service;


import com.sportardar.task.model.GameImpl;
import com.sportardar.task.model.IGame;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardServiceImpl implements IScoreBoardService {

    private final Map<String, IGame> ongoingGames = new LinkedHashMap<>();
    private final Map<String, IGame> finishedGames = new LinkedHashMap<>();

    @Override
    public IGame startGame(String homeTeam, String awayTeam) {
        IGame game = new GameImpl(homeTeam, awayTeam);
        System.out.println("Game :: " + game);
        ongoingGames.put(generateKey(homeTeam, awayTeam), game);
        return game;
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        String key = generateKey(homeTeam, awayTeam);
        IGame game = ongoingGames.remove(key);
        if (game != null) {
            finishedGames.put(key, game);
        }
    }

    /**
    Immutability Consideration: I am  using a new instance creation approach whenever there's an update in the score.
    While this ensures that Game instances are immutable,
    I have to document this behavior, so it's clear to others working with this code.
     **/
    
    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        IGame game = ongoingGames.get(generateKey(homeTeam, awayTeam));
        if (game != null) {
            game = game.updateScores(homeScore, awayScore);
            System.out.println("Game Update :: " + game);
            ongoingGames.put(generateKey(homeTeam, awayTeam), game);
        }
    }


    @Override
    public List<IGame> getOngoingGamesSummary() {
        return getSortedSummary(ongoingGames);
    }

    @Override
    public List<IGame> getFinishedGamesSummary() {
        return getSortedSummary(finishedGames);
    }

    private List<IGame> getSortedSummary(Map<String, IGame> games) {
        List<IGame> list = new ArrayList<>(games.values());

        // Grouping games by their total scores
        Map<Integer, List<IGame>> groupedByScore = list.stream()
                .collect(Collectors.groupingBy(IGame::getTotalScore));

        // Sorting each group by its timestamp in descending order
        for (List<IGame> scoreGroup : groupedByScore.values()) {
            scoreGroup.sort(Comparator.comparing(IGame::getTimestamp).reversed());
        }

        // Sorting the map keys (total scores) in descending order and
        // merging the sorted groups together
        List<IGame> sortedGames = groupedByScore.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .flatMap(score -> groupedByScore.get(score).stream())
                .collect(Collectors.toList());

        sortedGames.forEach(System.out::println);
        return sortedGames;
    }

    private String generateKey(String homeTeam, String awayTeam) {
        return homeTeam + "-" + awayTeam;
    }
}
