package service;

import com.sportardar.task.model.IGame;
import com.sportardar.task.service.IScoreBoardService;
import com.sportardar.task.service.ScoreBoardServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ScoreBoardServiceTest {


    private IScoreBoardService service = new ScoreBoardServiceImpl();

    @Test
    void shouldStartGame() {
        service.startGame("Home", "Away");
        List<IGame> ongoingGamesSummary = service.getOngoingGamesSummary();
        assertThat(ongoingGamesSummary).isNotEmpty();
        assertThat(ongoingGamesSummary.get(0).getHomeTeam()).isEqualTo("Home");
        assertThat(ongoingGamesSummary.get(0).getAwayTeam()).isEqualTo("Away");
    }

    @Test
    void shouldNotStartGame() {
        assertThrows(IllegalArgumentException.class, () ->
                service.startGame("", "Away"));
    }


    @Test
    void shouldFinishGame() {
        service.startGame("Home", "Away");
        service.finishGame("Home", "Away");
        assertThat(service.getOngoingGamesSummary()).isEmpty();
        assertThat(service.getFinishedGamesSummary()).isNotEmpty();
    }

    @Test
    void shouldNotFinishNonExistentGame() {
        service.finishGame("NonExistentHome", "NonExistentAway");
        assertThat(service.getOngoingGamesSummary()).isEmpty();
        assertThat(service.getFinishedGamesSummary()).isEmpty();
    }


    @Test
    void shouldUpdateScore() {
        service.startGame("Home", "Away");
        service.updateScore("Home", "Away", 3, 2);
        List<IGame> ongoingGamesSummary = service.getOngoingGamesSummary();
        assertThat(ongoingGamesSummary.get(0).getHomeScore()).isEqualTo(3);
        assertThat(ongoingGamesSummary.get(0).getAwayScore()).isEqualTo(2);
    }


    @Test
    void shouldGetSummaryInCorrectOrder() {

        service.startGame("Mexico", "Canada");
        service.startGame("Spain", "Brazil");
        service.startGame("Germany", "France");
        service.startGame("Uruguay", "Italy");
        service.startGame("Argentina", "Australia");

        service.updateScore("Mexico", "Canada", 0, 5);
        service.updateScore("Spain", "Brazil", 10, 2);
        service.updateScore("Germany", "France", 2, 2);
        service.updateScore("Uruguay", "Italy", 6, 6);
        service.updateScore("Argentina", "Australia", 3, 1);

        List<IGame> ongoingGamesSummary = service.getOngoingGamesSummary();

        assertThat(ongoingGamesSummary.get(0).toString()).isEqualTo("Uruguay 6 - Italy 6");
        assertThat(ongoingGamesSummary.get(1).toString()).isEqualTo("Spain 10 - Brazil 2");
        assertThat(ongoingGamesSummary.get(2).toString()).isEqualTo("Mexico 0 - Canada 5");
        assertThat(ongoingGamesSummary.get(3).toString()).isEqualTo("Argentina 3 - Australia 1");
        assertThat(ongoingGamesSummary.get(4).toString()).isEqualTo("Germany 2 - France 2");

    }

    @Test
    void shouldMaintainFinishedGames() {
        service.startGame("Home", "Away");
        service.finishGame("Home", "Away");
        assertThat(service.getOngoingGamesSummary()).isEmpty();
        assertThat(service.getFinishedGamesSummary()).hasSize(1);
        assertThat(service.getFinishedGamesSummary().get(0).getHomeTeam()).isEqualTo("Home");
        assertThat(service.getFinishedGamesSummary().get(0).getAwayTeam()).isEqualTo("Away");
    }
}
