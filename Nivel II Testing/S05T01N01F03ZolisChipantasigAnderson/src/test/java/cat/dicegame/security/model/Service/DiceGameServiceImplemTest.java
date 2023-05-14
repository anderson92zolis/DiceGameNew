package cat.dicegame.security.model.Service;

import cat.dicegame.security.model.Repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

//  https://www.youtube.com/watch?v=Geq60OVyBPg
class DiceGameServiceImplemTest {

    @Mock
    private PlayerRepository playerRepository;
    private AutoCloseable autoCloseable;
    private DiceGameServiceImplem underTest;

    @BeforeEach
    void setUp() {
        autoCloseable=MockitoAnnotations.openMocks(this);
        underTest = new DiceGameServiceImplem(playerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void convertPlayerEntitytoDTO() {
    }

    @Test
    void convertPlayerDTOtoEntity() {
    }

    @Test
    void createPlayer() {
    }

    @Test
    void updatePlayer() {
    }

    @Test
    void createRoll() {
    }

    @Test
    void deleteRollsofAPlayer() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUsersInTheGame() {
        // when
        underTest.getAllUsersInTheGame();
        // then
        verify(playerRepository).findAll();
    }

    @Test
    void getPlayerDtoByIdWithOverage() {
    }

    @Test
    void averageSuccessRateGetONE() {
    }

    @Test
    void getOveragesRankingOfAllPlayer() {
    }

    @Test
    void getPlayerWithTheWorstLossRate() {
    }

    @Test
    void getPlayerWithTheWorstSuccessRate() {
    }

    @Test
    void convertListOfPlayerToListOfPLayerDTO() {
    }

    @Test
    void exitsById() {
    }

    @Test
    void verifyPlayerName() {
    }

    @Test
    void getPlayerById() {
    }
}