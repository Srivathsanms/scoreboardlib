package com.sportardar.task.service;

import com.sportardar.task.model.GameImpl;
import com.sportardar.task.model.IGame;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the IScoreBoardService interface to manage ongoing and finished games.
 */
public class ScoreBoardServiceImpl implements IScoreBoardService {

    private final Map<String, IGame> ongoingGames = new LinkedHashMap<>();
    private final Map<String, IGame> finishedGames = new LinkedHashMap<>();

    /**
     * Starts a new game with the specified home and away teams.
     *
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     * @return The newly created game instance.
     */
    @Override
    public IGame startGame(String homeTeam, String awayTeam) {
        IGame game = new GameImpl(homeTeam, awayTeam);
        ongoingGames.put(generateKey(homeTeam, awayTeam), game);
        return game;
    }

    /**
     * Marks a game as finished between the specified teams.
     *
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     */
    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        String key = generateKey(homeTeam, awayTeam);
        IGame game = ongoingGames.remove(key);
        if (game != null) {
            finishedGames.put(key, game);
        }
    }

    /**
     * Updates the scores for the ongoing game between the specified teams.
     * This method respects immutability by creating a new game instance with updated scores.
     *
     * @param homeTeam  Name of the home team.
     * @param awayTeam  Name of the away team.
     * @param homeScore Updated score for the home team.
     * @param awayScore Updated score for the away team.
     */
    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        IGame game = ongoingGames.get(generateKey(homeTeam, awayTeam));
        if (game != null) {
            game = game.updateScores(homeScore, awayScore);
            ongoingGames.put(generateKey(homeTeam, awayTeam), game);
        }
    }

    /**
     * Returns a summary of ongoing games, sorted by their total scores in descending order.
     *
     * @return A list of ongoing games.
     */
    @Override
    public List<IGame> getOngoingGamesSummary() {
        return getSortedSummary(ongoingGames);
    }

    /**
     * Returns a summary of finished games, sorted by their total scores in descending order.
     *
     * @return A list of finished games.
     */
    @Override
    public List<IGame> getFinishedGamesSummary() {
        return getSortedSummary(finishedGames);
    }

    /**
     * Returns a sorted summary of games based on their total scores in descending order.
     *
     * @param games A map containing games to sort.
     * @return A list of sorted games.
     */
    private List<IGame> getSortedSummary(Map<String, IGame> games) {
        List<IGame> list = new ArrayList<>(games.values());
        Map<Integer, List<IGame>> groupedByScore = list.stream()
                .collect(Collectors.groupingBy(IGame::getTotalScore));

        for (List<IGame> scoreGroup : groupedByScore.values()) {
            scoreGroup.sort(Comparator.comparing(IGame::getTimestamp).reversed());
        }

        List<IGame> sortedGames = groupedByScore.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .flatMap(score -> groupedByScore.get(score).stream())
                .collect(Collectors.toList());

        return sortedGames;
    }

    /**
     * Generates a unique key for a game based on home and away team names.
     *
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     * @return A unique key for the game.
     */
    private String generateKey(String homeTeam, String awayTeam) {
        return homeTeam + "-" + awayTeam;
    }
}
