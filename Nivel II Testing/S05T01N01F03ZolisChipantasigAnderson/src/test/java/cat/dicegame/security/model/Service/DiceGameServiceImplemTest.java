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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

//  https://www.youtube.com/watch?v=Geq60OVyBPg
// https://www.baeldung.com/mockito-junit-5-extension
// @ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiceGameServiceImplemTest {

    private PlayerRepository playerRepository;
    private AutoCloseable autoCloseable;
    private DiceGameServiceImplem underTest;



    @BeforeEach
    void setUp() {



        Player player1 = new  Player("PLAYER1");
        player1.setEmail("Email1@gmail.com");
        player1.setPassword("1");

        playerRepository.save(player1);


    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void createPlayer() {

        // given

        PlayerDto playerToCreate = new  PlayerDto("saved");
        playerToCreate.setEmail("Email1@gmail.com");
        playerToCreate.setPassword("1");


        // when

        PlayerDto playerSaved= underTest.createPlayer(playerToCreate);

        // then

        assertThat(playerSaved).isNotNull();
        assertThat(playerSaved.getName()).isEqualTo("saved");

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
    @Disabled
    void getAllUsersInTheGameTest() throws NoSuchElementException {

        // WHEN

        Player player2 = new  Player("PLAYER1");
        player2.setEmail("Email1@gmail.com");
        player2.setPassword("1");

        playerRepository.save(player2);

        // when



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