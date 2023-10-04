# Sportradar Scoreboard System

### Introduction

This application/library is designed to manage game scores. Through this system, users can start games, update scores, finish games, and retrieve game summaries.
The application strictly adheres to the OOP principles, offering a modular and extensible system.

## Project Structure:
    Packages and Classes:
    model:
        IGame: An interface representing a game.
        GameImpl: A concrete class implementing the IGame interface.
    service:
        IScoreBoardService: An interface defining all the essential methods for the scoreboard.
        ScoreBoardServiceImpl: A concrete class that implements the IScoreBoardService, managing both ongoing and finished games.
    util:
        GameValidator: A utility class meant to validate game-related data.

# Features:
    Start new games.
    Update scores of ongoing games.
    Finish existing games.
    Retrieve summaries of both ongoing and finished games. These summaries are sorted based on their total scores and their respective timestamps.

# Key Notes:
    Immutability: The application employs an immutability approach. Whenever there's a score update in the GameImpl class, a new instance is created.
    Exception Handling:
    Team names cannot be null or blank.
    Scores cannot be negative.

# Build
    mvn clean install package
This command will package the library and it can be added as dependency as below in the application which is using this library
# Command    
    <dependency>
        <groupId>com.sportradar.sports</groupId>
        <artifactId>scoreboard-library</artifactId>
        <version>0.0.1</version>
    </dependency>