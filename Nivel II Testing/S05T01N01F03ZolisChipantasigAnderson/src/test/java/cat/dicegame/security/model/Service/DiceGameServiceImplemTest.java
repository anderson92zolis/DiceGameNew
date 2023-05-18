package cat.dicegame.security.model.Service;

import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Entity.Role;
import cat.dicegame.security.model.Exceptions.NameRepetitiveException;
import cat.dicegame.security.model.Exceptions.NoPlayersFoundRepositoryException;
import cat.dicegame.security.model.Exceptions.ResourceNotFoundException;
import cat.dicegame.security.model.Repository.PlayerRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

//  https://www.youtube.com/watch?v=Geq60OVyBPg
// https://www.baeldung.com/mockito-junit-5-extension
// The page that are follow:   https://springframework.guru/testing-spring-boot-restful-services/


//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DiceGameServiceImplemTest {

    List<Player> playersList;
    List<PlayerDto> playersListDto;
    @Mock
    private PlayerRepository playerRepository;
    @Autowired
    @InjectMocks
    private PlayerServiceImp playerServiceImp;
    private PlayerDto playerDtoA;
    private Player playerA;
    private PlayerDto playerDtoB;
    private Player playerB;
    private Role role;

    @BeforeEach
    void setUp() {


        playerA = new Player("PLAYERA");
        playerA.setEmail("aemail@gmail.com");
        playerA.setPassword("1");

        playerB = new Player("PLAYERB");
        playerB.setEmail("bemail@gmail.com");
        playerB.setPassword("2");


        playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        playerDtoA = new PlayerDto("PLAYERA");
        playerDtoA.setEmail("aemail@gmail.com");
        playerDtoA.setPassword("1");

        playerDtoB = new PlayerDto("PLAYERB");
        playerDtoB.setEmail("bemail@gmail.com");
        playerDtoB.setPassword("2");

        playersListDto = new ArrayList<>();
        playersListDto.add(playerDtoA);
        playersListDto.add(playerDtoB);


    }

    @AfterEach
    void tearDown() {

    }


    @DisplayName("TEST TO CREATE A PLAYER")
    @Test
        //@Disabled
    void createPlayerTest() {
        //given
        when(playerRepository.save(any())).thenReturn(playerA);
        //when
        PlayerDto playerDtoSaved = playerServiceImp.createPlayer(playerDtoA);
        //then
        verify(playerRepository, times(1)).save(any());
        // System.out.println(playerDtoSaved);
        assertThat(playerDtoSaved).isNotNull();
    }

    @Test
        //@Disabled
    void updatePlayerTest() throws NameRepetitiveException, ResourceNotFoundException {

        //given
        ObjectId objectId = new ObjectId();

        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("anderso_nemail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(role).build();


        //playerRepository.save(expectedPlayer);

        when(playerRepository.existsById(objectId)).thenReturn(true);
        when(playerRepository.findById(objectId)).thenReturn(Optional.ofNullable(expectedPlayer));
        when(playerRepository.save(any())).thenReturn(expectedPlayer);
        //when(playerRepository.findById(objectId)).thenReturn(Optional.of(expectedPlayer));

        //expectedPlayer.setName("UpdatedPlayer");

        //when
        //PlayerDto playertoDto = playerServiceImp.convertPlayerEntitytoDTO(expectedPlayer);
        PlayerDto playertoDto = new PlayerDto("UpdatedPlayer");

        PlayerDto playerAUpdatedDto = playerServiceImp.updatePlayer(expectedPlayer.getId(), playertoDto);

        //then
        assertEquals("UpdatedPlayer", playerAUpdatedDto.getName());
        //verify(playerRepository).findById(objectId);
    }

    @Test
        //@Disabled
    void createRollTest() throws ResourceNotFoundException {
        //given
        ObjectId objectId = new ObjectId();

        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("anderso_nemail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(role).build();

        when(playerRepository.save(expectedPlayer)).thenReturn(expectedPlayer);
        when(playerRepository.findById(objectId)).thenReturn(Optional.ofNullable(expectedPlayer));

        //when

        //Adding Rolls
        // 1 to 3
        PlayerDto expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);
        expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);
        expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);

        //then
        assertEquals(3, expectedPlayerWithRoll.getRollsList().size());
        assertThat(expectedPlayerWithRoll.getRollsList().size()).isNotNull();

    }

    @Test
        //@Disabled
    void deleteRollsofAPlayerTest() {

        //given


        ObjectId objectId = new ObjectId();


        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("andersonEmail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(role).build();

        willDoNothing().given(playerRepository).deleteById(objectId);

        //when

        playerServiceImp.deleteUser(objectId);

        // then
        verify(playerRepository, times(1)).deleteById(objectId);

    }

    @Test
        // @Disabled
    void deleteUser() {

    }

    @Test
        //@Disabled

    void getAllUsersInTheGameTest() throws NoSuchElementException, NoPlayersFoundRepositoryException {
        //given


        playerRepository.save(playerA);
        playerRepository.save(playerB);


        //stubbing mock to return specific data


        when(playerRepository.findAll()).thenReturn(playersList);

        //PlayerServiceImp mock = Mockito.mock(PlayerServiceImp.class);

        //when(mock.convertListOfPlayerToListOfPLayerDTO()).thenReturn(playersListDto);
        //when(mock.averageSuccessRate(playersListDto)).thenReturn(playersListDto);

        // Spy:  https://stackoverflow.com/questions/37095096/how-to-mock-a-call-of-an-inner-method-from-a-junit

        //PlayerServiceImp spy = spy(playerServiceImp);

        //create a spy for class-under-test
        //when(spy.convertListOfPlayerToListOfPLayerDTO()).thenReturn(playersListDto); //partially override behavior of the spy

        //spy = spy(playerServiceImp); //create a spy for class-under-test
        //when(spy.averageSuccessRate(playersListDto)).thenReturn(playersListDto);
        //when
        List<PlayerDto> playerServiceListDto = playerServiceImp.getAllPlayersInTheGameWithOverage();
        //then

        verify(playerRepository, times(1)).save(playerA);
        verify(playerRepository, times(1)).save(playerB);
        verify(playerRepository, times(1)).findAll();
        //assertEquals(playerServiceListDto, playersListDto);
        assertEquals(playerServiceListDto.get(0).getName(), "PLAYERA");
        assertEquals(playerServiceListDto.size(), playersListDto.size());
        assertThat(playerServiceListDto).isNotNull();
    }

    @Test
        //@Disabled
    void getPlayerDtoByIdWithOverage() throws ResourceNotFoundException {
        // create a new ObjectId

        ObjectId objectId = new ObjectId();

        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("andersonEmail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(role).build();

        when(playerRepository.findById(objectId)).thenReturn(Optional.of(expectedPlayer));

        //  when
        PlayerDto actualPlayerDto = playerServiceImp.getPlayerDtoByIdWithOverage(objectId);
        Player actualPlayerEntity = playerServiceImp.convertPlayerDTOtoEntity(actualPlayerDto);

        // then

        assertEquals(expectedPlayer, actualPlayerEntity);
        verify(playerRepository).findById(objectId);
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