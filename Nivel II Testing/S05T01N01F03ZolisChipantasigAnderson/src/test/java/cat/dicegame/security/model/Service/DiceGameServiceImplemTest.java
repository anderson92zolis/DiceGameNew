package cat.dicegame.security.model.Service;

import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Repository.PlayerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


//  https://www.youtube.com/watch?v=Geq60OVyBPg
// https://www.baeldung.com/mockito-junit-5-extension
// @ExtendWith(MockitoExtension.class)

// @SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiceGameServiceImplemTest {

    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private DiceGameServiceImplem diceGameServiceTest;

    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    private Player player;




    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        player = new Player("PLAYER");
        player.setEmail("email@gmail.com");
        player.setPassword("1");

    }


    @AfterEach
    void tearDown()  {

    }


    @DisplayName("TEST TO CREATE A PLAYER")
    @Test
    // Disable
    void createPlayerTest() {


        // given

        given(playerRepository.findById(player.getId())).willReturn(Optional.empty());

        given(playerRepository.save(player)).willReturn(player);


        // when

        PlayerDto playerDto = modelMapper.map(player, PlayerDto.class);

        PlayerDto savedPlayerDto = diceGameServiceTest.createPlayer(playerDto);

        // then

        assertThat(savedPlayerDto).isNotNull();
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

        Player player2 = new Player("PLAYER1");
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


        diceGameServiceTest.getAllUsersInTheGame();

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