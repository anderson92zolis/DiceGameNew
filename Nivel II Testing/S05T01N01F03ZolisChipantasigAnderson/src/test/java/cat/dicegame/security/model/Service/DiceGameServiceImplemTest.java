package cat.dicegame.security.model.Service;

import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

//  https://www.youtube.com/watch?v=Geq60OVyBPg
// https://www.baeldung.com/mockito-junit-5-extension
@ExtendWith(MockitoExtension.class)
class DiceGameServiceImplemTest {

    @Mock
    private PlayerRepository playerRepository;
    private ModelMapper mapper;
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
    @Disabled
    void convertPlayerEntitytoDTO() {
    }

    @Test
    @Disabled
    void convertPlayerDTOtoEntity() {
    }

    @Test
    @Disabled
    void createPlayer() {
    }

    @Test
    @Disabled
    void updatePlayer() {
    }

    @Test
    @Disabled
    void createRoll() {
    }

    @Test
    @Disabled
    void deleteRollsofAPlayer() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }

    @Test
    void getAllUsersInTheGameTest() throws NoSuchElementException {
        /*
        // when

        Player player = new Player();
        player.setName("John");
        player.setEmail("john@example.com");

        when(playerRepository.findAll()).thenReturn(Collections.singletonList(player));

        // Execute the method

        List<PlayerDto> result = underTest.getAllUsersInTheGame();

        // Verify the interactions and assertions
        verify(playerRepository).findAll();

        assertEquals(1, result.size());
        PlayerDto playerDto = result.get(0);
        assertEquals("John", playerDto.getName());
        assertEquals("john@example.com", playerDto.getEmail());

*/

        underTest.getAllUsersInTheGame() ;

        // then


        verify(playerRepository).findAll();


    }

    @Test
    @Disabled
    void getPlayerDtoByIdWithOverage() {
    }

    @Test
    @Disabled
    void averageSuccessRateGetONE() {
    }

    @Test
    @Disabled
    void getOveragesRankingOfAllPlayer() {
    }

    @Test
    @Disabled
    void getPlayerWithTheWorstLossRate() {
    }

    @Test
    @Disabled
    void getPlayerWithTheWorstSuccessRate() {
    }

    @Test
    @Disabled
    void convertListOfPlayerToListOfPLayerDTO() {
    }

    @Test
    @Disabled
    void exitsById() {
    }

    @Test
    @Disabled
    void verifyPlayerName() {
    }

    @Test
    @Disabled
    void getPlayerById() {
    }
}