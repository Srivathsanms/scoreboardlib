package com.sportardar.task.model;

import com.sportardar.task.util.GameValidator;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implementation of the IGame interface representing a game between two teams.
 */
public class GameImpl implements IGame {

    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final LocalDateTime timestamp;
    private final GameValidator validator = new GameValidator();

    /**
     * Constructor to initialize a game with the specified teams.
     *
     * @param homeTeam The home team name.
     * @param awayTeam The away team name.
     */
    public GameImpl(String homeTeam, String awayTeam) {
        validator.validateTeamName(homeTeam);
        validator.validateTeamName(awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Private constructor used to create a new game state with updated scores.
     */
    private GameImpl(String homeTeam, String awayTeam, int homeScore, int awayScore, LocalDateTime timestamp) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.timestamp = timestamp;
    }

    /**
     * @return The timestamp when the game was initialized.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String getHomeTeam() {
        return homeTeam;
    }

    @Override
    public String getAwayTeam() {
        return awayTeam;
    }

    @Override
    public int getHomeScore() {
        return homeScore;
    }

    @Override
    public int getAwayScore() {
        return awayScore;
    }

    /**
     * Updates the scores of the game and returns a new instance with the updated values.
     *
     * @param homeScore The updated home score.
     * @param awayScore The updated away score.
     * @return A new instance of the GameImpl with the updated scores.
     */
    @Override
    public IGame updateScores(int homeScore, int awayScore) {
        validator.validateScore(homeScore);
        validator.validateScore(awayScore);
        return new GameImpl(this.homeTeam, this.awayTeam, homeScore, awayScore, this.timestamp);
    }

    @Override
    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameImpl game = (GameImpl) o;
        return Objects.equals(homeTeam, game.homeTeam) && Objects.equals(awayTeam, game.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    /**
     * Returns a string representation of the game in the format "homeTeam homeScore - awayTeam awayScore".
     *
     * @return A string representation of the game.
     */
    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}
