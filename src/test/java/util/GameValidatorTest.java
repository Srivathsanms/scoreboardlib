package util;


import com.sportardar.task.util.GameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GameValidatorTest {

    private GameValidator gameValidator;

    @BeforeEach
    public void setUp() {
        gameValidator = new GameValidator();
    }

    @Test
    public void testValidateTeamName_validName() {
        String validTeamName = "TeamA";
        assertDoesNotThrow(() -> gameValidator.validateTeamName(validTeamName));
    }

    @Test
    public void testValidateTeamName_emptyName() {
        String emptyName = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameValidator.validateTeamName(emptyName);
        });
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateTeamName_nullName() {
        String nullName = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameValidator.validateTeamName(nullName);
        });
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateTeamName_onlySpaces() {
        String spacesName = "   ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameValidator.validateTeamName(spacesName);
        });
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateScore_validPositiveScore() {
        int validScore = 5;
        assertDoesNotThrow(() -> gameValidator.validateScore(validScore));
    }

    @Test
    public void testValidateScore_validZeroScore() {
        int zeroScore = 0;
        assertDoesNotThrow(() -> gameValidator.validateScore(zeroScore));
    }

    @Test
    public void testValidateScore_negativeScore() {
        int negativeScore = -1;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameValidator.validateScore(negativeScore);
        });
        assertEquals("Score cannot be negative", exception.getMessage());
    }
}
