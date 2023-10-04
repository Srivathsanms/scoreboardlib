package com.sportardar.task.model;



import com.sportardar.task.util.GameValidator;

import java.time.LocalDateTime;
import java.util.Objects;

public class GameImpl implements IGame {

    //Encapsulation: I have kept the
    // variables homeTeam, awayTeam, homeScore, and awayScore private
    //and provided public methods to access and modify them, thus encapsulating the behavior.
    /*
    Abstraction: By introducing the IGame interface, I have abstracted away the details of the game and
    provided only the necessary methods that are required to interact with the game.
    Inheritance: The Game class is implementing the IGame interface, leveraging inheritance.
    Polymorphism: With the IGame interface, I can now have different types of games
    (e.g., FootballGame, BasketballGame, etc.) implementing this interface. This way, I can interact with all of them polymorphically.
     */
    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final LocalDateTime timestamp;
    private final GameValidator validator = new GameValidator();


    public GameImpl(String homeTeam, String awayTeam) {
        validator.validateTeamName(homeTeam);
        validator.validateTeamName(awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.timestamp = LocalDateTime.now();
    }

    private GameImpl(String homeTeam, String awayTeam, int homeScore, int awayScore, LocalDateTime timestamp) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.timestamp = timestamp;
    }

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

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}
