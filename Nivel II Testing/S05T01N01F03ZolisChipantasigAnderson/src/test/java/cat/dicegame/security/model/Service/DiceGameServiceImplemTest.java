package cat.dicegame.security.model.Service;

import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Dto.RankingDto;
import cat.dicegame.security.model.Dto.RollDto;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Entity.Role;
import cat.dicegame.security.model.Entity.Roll;
import cat.dicegame.security.model.Exceptions.NameRepetitiveException;
import cat.dicegame.security.model.Exceptions.NoPlayersFoundRepositoryException;
import cat.dicegame.security.model.Exceptions.ResourceNotFoundException;
import cat.dicegame.security.model.Repository.PlayerRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

//  https://www.youtube.com/watch?v=Geq60OVyBPg
// https://www.baeldung.com/mockito-junit-5-extension
// The page that are follow:   https://springframework.guru/testing-spring-boot-restful-services/
// Spy:  https://stackoverflow.com/questions/37095096/how-to-mock-a-call-of-an-inner-method-from-a-junit


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
    }

    @Test
        // @Disabled
    void deleteUser() {
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
    void getAllPlayersFromDB() {
        //given

        playerRepository.save(playerA);
        playerRepository.save(playerB);
        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        List<Player> playerServiceListDto = playerServiceImp.getAllPlayersFromDB();

        //then

        verify(playerRepository, times(1)).save(playerA);
        verify(playerRepository, times(1)).save(playerB);
        verify(playerRepository, times(1)).findAll();
        assertEquals(playerServiceListDto.get(0).getName(), "PLAYERA");
        assertEquals(playerServiceListDto.size(), playersListDto.size());
        assertThat(playerServiceListDto).isNotNull();

    }
    @Test
        //@Disabled
    void getAllUsersInTheGameTest() throws NoSuchElementException, NoPlayersFoundRepositoryException {
        //given

        playerRepository.save(playerA);
        playerRepository.save(playerB);

        //stubbing mock to return specific data

        when(playerRepository.findAll()).thenReturn(playersList);
        //when

        List<PlayerDto> playerServiceListDto = playerServiceImp.getAllPlayersInTheGameWithOverage();

        //then

        verify(playerRepository, times(1)).save(playerA);
        verify(playerRepository, times(1)).save(playerB);
        verify(playerRepository, times(1)).findAll();
        assertEquals(playerServiceListDto.get(0).getName(), "PLAYERA");
        assertEquals(playerServiceListDto.size(), playersListDto.size());
        verify(playerRepository).findAll();
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
    //@Disabled
    void averageSuccessRateGetONETest() {

        // Create a PlayerDto object for testing
        PlayerDto playerDto = new PlayerDto();

        // Create a list of RollDto objects
        List<RollDto> rollsListDto = new ArrayList<>();


        // Add RollDto objects to the list
        rollsListDto.add(new RollDto(1, 2, "WIN", new Date()));
        rollsListDto.add(new RollDto(3, 4, "WIN", new Date()));
        rollsListDto.add(new RollDto(5, 6, "LOSS", new Date()));
        rollsListDto.add(new RollDto(2, 3, "WIN", new Date()));

        // Set the rollsListDto in the playerDto
        playerDto.setRollsList(rollsListDto);

        // Call the method to test
       playerServiceImp.averageSuccessRateGetONE(playerDto);

        // Assert the expected values
        Assert.assertEquals("YOUR AVERAGE SUCCESS RATE IS 75.0 % PERCENTAGE", playerDto.getAverageSuccessRate());
        Assert.assertEquals( "25.0",  playerDto.getAverageLoserRateNumber().toString());
        Assert.assertEquals( "75.0",  playerDto.getAverageSuccessRateNumber().toString());
    }

    @Test
    //@Disabled
    void getOveragesRankingOfAllPlayer() throws NoPlayersFoundRepositoryException {

        //getting

        playerA.getRollsList().add(new Roll(1, 6, "WIN", new Date()));
        playerA.getRollsList().add(new Roll(3, 4, "WIN", new Date()));

        playerB.getRollsList().add(new Roll(5, 6, "LOSS",new Date()));
        playerB.getRollsList().add(new Roll(2, 5, "WIN", new Date()));


        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        RankingDto rankingDto= playerServiceImp.getOveragesRankingOfAllPlayer();

        // Assert the expected values
        Assert.assertEquals("75.0", rankingDto.getOverageRankingAllPlayer().toString());
        verify(playerRepository).findAll();
    }

    @Test
    //@Disabled
    void getPlayerWithTheWorstLossRate() throws NoPlayersFoundRepositoryException {
        //getting

        playerA.getRollsList().add(new Roll(1, 6, "WIN", new Date()));
        playerA.getRollsList().add(new Roll(3, 4, "WIN", new Date()));

        playerB.getRollsList().add(new Roll(5, 6, "LOSS",new Date()));
        playerB.getRollsList().add(new Roll(2, 5, "WIN", new Date()));


        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        // method to test
        RankingDto rankingDto= playerServiceImp.getPlayerWithTheWorstLossRate();
        // method to convert entity to Dto

        PlayerDto worstLossRatePlayer = playerServiceImp.convertPlayerEntitytoDTO(playerA);
        // Knowing that player A is the worst loser Rate number
        worstLossRatePlayer.setAverageSuccessRate("YOUR AVERAGE SUCCESS RATE IS 100.0%");
        worstLossRatePlayer.setAverageSuccessRateNumber(100.0);
        worstLossRatePlayer.setAverageLoserRateNumber(0.00);

        // Assert the expected values
        Assert.assertEquals(worstLossRatePlayer, rankingDto.getPlayerWithTheWorstLossPorcentage());
        verify(playerRepository).findAll();
        assertThat(rankingDto.getPlayerWithTheWorstLossPorcentage()).isNotNull();

    }

    @Test
    //@Disabled
    void getPlayerWithTheWorstSuccessRate() throws NoPlayersFoundRepositoryException {
        //getting

        playerA.getRollsList().add(new Roll(1, 6, "WIN", new Date()));
        playerA.getRollsList().add(new Roll(3, 4, "WIN", new Date()));

        playerB.getRollsList().add(new Roll(5, 6, "LOSS",new Date()));
        playerB.getRollsList().add(new Roll(2, 5, "WIN", new Date()));

        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        // method to test
        RankingDto rankingDto= playerServiceImp.getPlayerWithTheWorstSuccessRate();
        // method to convert entity to Dto

        PlayerDto playerWorstSuccessRate = playerServiceImp.convertPlayerEntitytoDTO(playerB);
        // Knowing that player A is the worst loser Rate number
        playerWorstSuccessRate.setAverageSuccessRate("YOUR AVERAGE SUCCESS RATE IS 50.0%");
        playerWorstSuccessRate.setAverageSuccessRateNumber(50.0);
        playerWorstSuccessRate.setAverageLoserRateNumber(50.0);

        // Assert the expected values
        Assert.assertEquals(playerWorstSuccessRate, rankingDto.getPlayerWithTheWorstSuccessPorcentage());
        verify(playerRepository).findAll();
        assertThat(rankingDto.getPlayerWithTheWorstSuccessPorcentage()).isNotNull();

    }

    @Test
    //@Disabled
    void convertListOfPlayerToListOfPLayerDTO() {
        //given
        when(playerRepository.findAll()).thenReturn(playersList);

        //when
        List<PlayerDto> playerConverted= playerServiceImp.convertListOfPlayerToListOfPLayerDTO();
        // then

        for (PlayerDto playerDto : playerConverted) {
            Assert.assertEquals(PlayerDto.class, playerDto.getClass());
        }
        assertThat(playerConverted).getClass();
        verify(playerRepository).findAll();
    }

    @Test
    //@Disabled
    void exitsById() {
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

        when(playerRepository.existsById(objectId)).thenReturn(true);

        //when

        Boolean playerFound = playerServiceImp.exitsById(objectId);

        //then
        assertEquals(true,playerFound);
        verify(playerRepository).existsById(objectId);

    }

    @Test
    //@Disabled
    void verifyPlayerName() {

        //given
        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        Boolean nameFound = playerServiceImp.verifyPlayerName("PLAYERA");

        //then
        assertEquals(true,nameFound);
        verify(playerRepository).findAll();
    }

    @Test
    //@Disabled
    void getPlayerById() throws ResourceNotFoundException {
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
        Player actualPlayerDto = playerServiceImp.getPlayerById(objectId);

        // then

        assertEquals(expectedPlayer, actualPlayerDto);
        verify(playerRepository).findById(objectId);
    }
}