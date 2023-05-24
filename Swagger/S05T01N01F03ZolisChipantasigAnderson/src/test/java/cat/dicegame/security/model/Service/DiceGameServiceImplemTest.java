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


    @Mock
    private PlayerRepository playerRepository;
    @Autowired
    @InjectMocks
    private PlayerServiceImp playerServiceImp;

    private Player playerA;
    private PlayerDto playerDtoA;
    private Player playerB;
    private PlayerDto playerDtoB;
    List<Player> playersList;
    List<PlayerDto> playersListDto;

    @BeforeEach
    void setUp() {

        ObjectId objectIdA = new ObjectId();

        playerA = Player.builder()
                .id(objectIdA)
                .name("PLAYERA")
                .email("aemail@gmail.com")
                .password("1")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(Role.USER).build();

        ObjectId objectIdB = new ObjectId();

        playerB = Player.builder()
                .id(objectIdB)
                .name("PLAYERB")
                .email("bemail@gmail.com")
                .password("2")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(Role.USER).build();


        playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        playerDtoA = new PlayerDto("PLAYERA");
        //playerDtoA.setEmail("aemail@gmail.com");
        //playerDtoA.setPassword("1");

        playerDtoB = new PlayerDto("PLAYERB");
        //playerDtoB.setEmail("bemail@gmail.com");
        //playerDtoB.setPassword("2");

        playersListDto = new ArrayList<>();
        playersListDto.add(playerDtoA);
        playersListDto.add(playerDtoB);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("TEST CONVERT ENTITY TO DTO")
    @Test
    void convertPlayerEntitytoDTOTest() {
        //given

                // @BeforeEach

         //when

        PlayerDto playerConverted = playerServiceImp.convertPlayerEntitytoDTO(playerA);

        // then

        assertThat(playerConverted).isNotNull();

        //assert playerConverted.getClass().equals(playerDtoA.getClass());

        assertEquals(playerConverted.getClass(), playerDtoA.getClass());

    }

    @Test
    @DisplayName("TEST CONVERT DTO TO ENTITY ")
    void convertPlayerDTOtoEntityTest() {

        //when

        Player playerConverted = playerServiceImp.convertPlayerDTOtoEntity(playerDtoA);

        // then

        assertThat(playerConverted).isNotNull();

        assertEquals(playerConverted.getClass(), playerA.getClass());

    }

    @Test
    @DisplayName("TEST UPDATE A PLAYER")
        //@Disabled
    void updatePlayerTest() throws NameRepetitiveException, ResourceNotFoundException {

        //given
        ObjectId objectId = new ObjectId();

        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("name_Expect")
                .email("name_Expect@gmail.com")
                .password("passwordName_Expect")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(Role.USER).build();

        when(playerRepository.existsById(objectId)).thenReturn(true);
        when(playerRepository.findById(objectId)).thenReturn(Optional.ofNullable(expectedPlayer));
        when(playerRepository.save(any())).thenReturn(expectedPlayer);

        //when

        PlayerDto playertoDto = new PlayerDto("UpdatedPlayer");

        PlayerDto playerAUpdatedDto = playerServiceImp.updatePlayer(expectedPlayer.getId(), playertoDto);

        //then
        assertEquals("UpdatedPlayer", playerAUpdatedDto.getName());

        verify(playerRepository).existsById(objectId);
        verify(playerRepository,times (1)).save(expectedPlayer);
    }

    @Test
        //@Disabled
    @DisplayName("CREATE ROLL FOR PLAYER")
    void createRollTest() throws ResourceNotFoundException {
        //given
        ObjectId objectId = new ObjectId();

        Player expectedPlayerToAddRolls = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("anderso_nemail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .role(Role.USER).build();

        when(playerRepository.findById(objectId)).thenReturn(Optional.ofNullable(expectedPlayerToAddRolls));
        when(playerRepository.save(expectedPlayerToAddRolls)).thenReturn(expectedPlayerToAddRolls);


        //when

        //Adding Rolls
        // 1 to 3

        PlayerDto expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);
        expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);
        expectedPlayerWithRoll = playerServiceImp.createRoll(objectId);

        //then
        assertEquals(3, expectedPlayerWithRoll.getRollsList().size());
        assertThat(expectedPlayerWithRoll.getRollsList().size()).isNotNull();

        // verify with assertion
        verify(playerRepository,times (3)).save(expectedPlayerToAddRolls);

    }

    @Test
    @DisplayName("TEST DELETE THE ROLLS OF A PLAYER")
        //@Disabled
    void deleteRollsOfAPlayerTest() throws ResourceNotFoundException {
        //given

        List<Roll> rollsList = new ArrayList<>();

        // Add RollDto objects to the list
        rollsList.add(new Roll(1, 2, "WIN", new Date()));
        rollsList.add(new Roll(3, 4, "WIN", new Date()));
        rollsList.add(new Roll(5, 6, "LOSS", new Date()));
        rollsList.add(new Roll(2, 3, "WIN", new Date()));
        ObjectId objectId = new ObjectId();

        Player playerWithToDeleteRolls = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("anderso_nemail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(rollsList) // adding rolls
                .role(Role.USER).build();

        when(playerRepository.existsById(objectId)).thenReturn(true);

        when(playerRepository.findById(objectId)).thenReturn(Optional.ofNullable(playerWithToDeleteRolls));

        when(playerRepository.save(any())).thenReturn(playerWithToDeleteRolls);
        //when
            // delete Rolls
        playerServiceImp.deleteRollsofAPlayer(objectId);

        //then
        assertEquals(0 , playerWithToDeleteRolls.getRollsList().size());
        assertThat(playerWithToDeleteRolls.getRollsList().size()).isNotNull();
            // verify with assertion
        verify(playerRepository,times (1)).findById(objectId);
        verify(playerRepository,times (1)).save(playerWithToDeleteRolls);

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
                .role(Role.USER).build();

        willDoNothing().given(playerRepository).deleteById(objectId);

        //when

        playerServiceImp.deleteUser(objectId);

        // then
        verify(playerRepository).deleteById(objectId);
        verify(playerRepository, times(1)).deleteById(objectId); //  # of invocation, this because doesn't return a Player
    }


    @Test
        // @Disabled
    void getAllPlayersFromDB() {
        //given

        playerRepository.save(playerA);
        playerRepository.save(playerB);
        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        List<Player> playerServiceList = playerServiceImp.getAllPlayersFromDB();

        //then

        assertEquals(playerServiceList.get(0).getName(), "PLAYERA");
        assertEquals(playerServiceList.size(), playersListDto.size());
        assertThat(playerServiceList).isNotNull();

        verify(playerRepository, times(1)).save(playerA);
        verify(playerRepository, times(1)).save(playerB);
        verify(playerRepository, times(1)).findAll();


    }
    @Test
        //@Disabled
    void getAllPlayersInTheGameWithOverageTest() throws NoSuchElementException, NoPlayersFoundRepositoryException {
        //given


        // Add RollDto objects to the list
        Roll roll1= new Roll(1, 6, "WIN", new Date());
        Roll roll2= new Roll(3, 0, "LOSS", new Date());

        playerA.addRolls(roll1);
        playerA.addRolls(roll2);

        playerB.addRolls(roll1);
        playerB.addRolls(roll2);

        playerRepository.save(playerA);
        playerRepository.save(playerB);

        //stubbing mock to return specific data

        when(playerRepository.findAll()).thenReturn(playersList);
        //when

        List<PlayerDto> playerServiceListDto = playerServiceImp.getAllPlayersInTheGameWithOverage();

        System.out.println(playerServiceListDto);

        //then

        assertEquals(playerServiceListDto.get(0).getName(), "PLAYERA");
        assertEquals(playerServiceListDto.size(), playersListDto.size());

        assertThat(playerServiceListDto.get(0).getAverageSuccessRate()).isEqualTo("YOUR AVERAGE SUCCESS RATE IS 50.0%");// playerA
        assertThat(playerServiceListDto.get(1).getAverageLoserRateNumber()).isEqualTo(50.0); // playerB
        assertThat(playerServiceListDto.get(0).getAverageSuccessRateNumber()).isEqualTo(50.0); // PlayerA

        verify(playerRepository, times(1)).save(playerA);
        verify(playerRepository, times(1)).save(playerB);
        verify(playerRepository, times(1)).findAll();
    }

    @Test
        //@Disabled
    void getPlayerDtoByIdWithOverageTest() throws ResourceNotFoundException {
        // create a new ObjectId

        ObjectId objectId = new ObjectId();

        Roll roll1= new Roll(1, 6, "WIN", new Date());
        Roll roll2= new Roll(3, 0, "LOSS", new Date());
        List<Roll> rollsList = new ArrayList<>();
        rollsList.add(roll1);
        rollsList.add(roll2);

        Player expectedPlayer = Player.builder()
                .id(objectId)
                .name("Anderson")
                .email("andersonEmail@gmail.com")
                .password("password")
                .localDateTime(LocalDateTime.now())
                .rollsList(rollsList)
                .build();

        when(playerRepository.findById(objectId)).thenReturn(Optional.of(expectedPlayer));

        //  when
        PlayerDto actualPlayerDto = playerServiceImp.getPlayerDtoByIdWithOverage(objectId);
        Player actualPlayerEntity = playerServiceImp.convertPlayerDTOtoEntity(actualPlayerDto);

        // then
        assertThat(actualPlayerDto.getAverageSuccessRate()).isEqualTo("YOUR AVERAGE SUCCESS RATE IS 50.0%");
        assertThat(actualPlayerDto.getAverageLoserRateNumber()).isEqualTo(50.0); // playerA
        assertThat(actualPlayerDto.getAverageSuccessRateNumber()).isEqualTo(50.0); // PlayerA

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
        Assert.assertEquals("YOUR AVERAGE SUCCESS RATE IS 75.0%", playerDto.getAverageSuccessRate());
        Assert.assertEquals( "25.0",  playerDto.getAverageLoserRateNumber().toString());
        Assert.assertEquals( "75.0",  playerDto.getAverageSuccessRateNumber().toString());
    }

    @Test
    //@Disabled
    void getOveragesRankingOfAllPlayerTest() {

        //getting

        playerA.getRollsList().add(new Roll(1, 6, "WIN", new Date()));
        playerA.getRollsList().add(new Roll(3, 4, "WIN", new Date()));

        playerB.getRollsList().add(new Roll(5, 6, "LOSS",new Date()));
        playerB.getRollsList().add(new Roll(2, 5, "WIN", new Date()));


        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        RankingDto rankingDto= playerServiceImp.getOveragesRankingOfAllPlayer();

        // Assert the expected values
        Assert.assertEquals("75.0", rankingDto.getOverageRankingAllPlayers().toString());
        verify(playerRepository).findAll();
    }

    @Test
    void setAverageSuccessRateAllPlayer() {
    }


    @Test

    void calculationOfSuccessAveragesOfAllPlayersforRankingDtoTest() {
        //getting

        playerA.getRollsList().add(new Roll(1, 6, "WIN", new Date()));
        playerA.getRollsList().add(new Roll(3, 4, "WIN", new Date()));

        playerB.getRollsList().add(new Roll(5, 6, "LOSS",new Date()));
        playerB.getRollsList().add(new Roll(2, 5, "WIN", new Date()));

        when(playerRepository.findAll()).thenReturn(playersList);

        //when

        playersListDto= playerServiceImp.convertListOfPlayerToListOfPLayerDTO();

        playersListDto= playerServiceImp.setAverageSuccessRateAllPlayer(playersListDto);

        RankingDto rankingDto= playerServiceImp.calculationOfSuccessAveragesOfAllPlayersforRankingDto(playersListDto);

        // Assert the expected values
        Assert.assertEquals("75.0", rankingDto.getOverageRankingAllPlayers().toString());
        verify(playerRepository).findAll();

    }
    @Test
    //@Disabled
    void getPlayerWithTheWorstLossRate() {
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

        PlayerDto worstLossRatePlayer = playerServiceImp.convertPlayerEntitytoDTO(playerA);  // KNOWN THAT THE WORST LOSS RATE IS PLAYER A
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
    void getPlayerWithTheWorstSuccessRate()  {
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
                .role(Role.USER).build();

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
                .role(Role.USER).build();

        when(playerRepository.findById(objectId)).thenReturn(Optional.of(expectedPlayer));

        //  when
        Player actualPlayerDto = playerServiceImp.getPlayerById(objectId);

        // then

        assertEquals(expectedPlayer, actualPlayerDto);
        verify(playerRepository).findById(objectId);
    }
}