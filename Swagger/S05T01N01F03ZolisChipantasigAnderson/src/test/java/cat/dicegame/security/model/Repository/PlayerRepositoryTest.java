package cat.dicegame.security.model.Repository;

import cat.dicegame.security.model.Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepositoryUnderTest;

    private Player testPlayer1;
    private Player testPlayer2;

    @BeforeEach
    void setUp() {

        testPlayer1 = new Player("TESTPLAYER1");
        testPlayer1.setEmail("test1@gmail.com");
        testPlayer1.setPassword("1");

        testPlayer2 = new  Player("TESTPLAYER2");
        testPlayer2.setEmail("test2@gmail.com");
        testPlayer2.setPassword("2");

    }

    @DisplayName("TEST TO SAVE THE PLAYER")
    @Test
    //@Disabled
    public void savePlayerTest() {

        //give
            // crete @BeforeEach

        //when
        Player foundPlayer1 = playerRepositoryUnderTest.save(testPlayer1);

        //then
      assertThat(foundPlayer1).isNotNull();
      assertThat(foundPlayer1.getEmail()).isEqualTo("test1@gmail.com");
    }

    @DisplayName("TEST TO GET ALL PLAYERS: Verify the numbers of player in database")
    @Test
    //@Disabled
    public void getAllPlayerTest() {


        //give
        playerRepositoryUnderTest.save(testPlayer1);
        playerRepositoryUnderTest.save(testPlayer2);

        //when

        List<Player> listPlayers = playerRepositoryUnderTest.findAll(); // verify that the MongoDB is empty

        //then
        assertThat(listPlayers).isNotNull();
        assertThat(listPlayers.size()).isGreaterThan(0);
    }

    @DisplayName("TEST TO GET A PLAYER BY ID")
    @Test
    //@Disabled
    public void getAPlayerByIdTest() {

        //give
        Player playerToSaved1=  playerRepositoryUnderTest.save(testPlayer1);

        //when
        Player playerFound1 = playerRepositoryUnderTest.findById(playerToSaved1.getId()).get();

        //then
        assertThat(playerFound1).isNotNull();
    }

    @DisplayName("TEST TO UPDATE A PLAYER BY ID")
    @Test
    //@Disabled
    public void updatePlayerByIdTest() {

        //give
        Player playerToSaved1=  playerRepositoryUnderTest.save(testPlayer1);

        //when
        Player playerFound1 = playerRepositoryUnderTest.findById(playerToSaved1.getId()).get();
        playerFound1.setName("updatedName1");
        playerFound1.setEmail("updated1@gmail.com");
        Player updatedPlayer= playerRepositoryUnderTest.save(playerFound1);

        //then
        assertThat(updatedPlayer.getName()).isEqualTo("updatedName1");
        assertThat(updatedPlayer.getEmail()).isEqualTo("updated1@gmail.com");
    }

    @DisplayName("TEST TO DELETE A PLAYER BY ID")
    @Test
    //@Disabled
    public void deletePlayerByIdTest() {

        //give
        Player playerToSave1=  playerRepositoryUnderTest.save(testPlayer1);

        //when
        playerRepositoryUnderTest.deleteById(playerToSave1.getId());
        Optional<Player> empleadoOptional = playerRepositoryUnderTest.findById(playerToSave1.getId());

        //then

        assertThat(empleadoOptional).isEmpty();
    }

}