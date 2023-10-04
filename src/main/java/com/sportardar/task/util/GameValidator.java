package com.sportardar.task.util;

/**
 * Validator utility class for game-related checks.
 * This class provides methods to validate team names and scores.
 */
public class GameValidator {

    /**
     * Validates a team name. The team name should not be null or empty.
     *
     * @param teamName The name of the team to be validated.
     * @throws IllegalArgumentException If the team name is null or empty.
     */
    public void validateTeamName(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
    }

    /**
     * Validates a game score. The score should not be negative.
     *
     * @param score The score value to be validated.
     * @throws IllegalArgumentException If the score is negative.
     */
    public void validateScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}
