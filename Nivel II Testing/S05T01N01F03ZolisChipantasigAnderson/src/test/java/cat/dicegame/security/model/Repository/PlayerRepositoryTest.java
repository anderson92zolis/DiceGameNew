package cat.dicegame.security.model.Repository;

import cat.dicegame.security.model.Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Evitar reemplazo de la fuente de datos



@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerRepositoryTest {


    //@MockBean
    @Autowired
    private PlayerRepository playerRepositoryUnderTest;

    private Player player;

    @BeforeEach
    void setUp() {


        player = new Player("PLAYER");
        player.setEmail("email@gmail.com");
        player.setPassword("1");

    }



    @DisplayName("TEST TO SAVE THE PLAYER")
    @Test
    //@Disabled
    public void savePlayerTest() {


        //give

        Player player1 = new  Player("TEST");

        player1.setEmail("testEmail@gmail.com");
        player1.setPassword("1");

        //when

        Player foundPlayer = playerRepositoryUnderTest.save(player1);


        //then
      assertThat(foundPlayer).isNotNull();
      assertThat(foundPlayer.getEmail()).isEqualTo("testEmail@gmail.com");
    }

    @DisplayName("TEST TO GET ALL PLAYERS, Verify the numbers of player in database")
    @Test
    //@Disabled
    public void getAllPlayerTest() {


        //give

        Player player2 = new  Player("player2");

        player2.setEmail("email@gmail.com");
        player2.setPassword("2");

        playerRepositoryUnderTest.save(player);
        playerRepositoryUnderTest.save(player2);
        //when

        List<Player> listPlayers = playerRepositoryUnderTest.findAll();

        //then
        assertThat(listPlayers).isNotNull();
        assertThat(listPlayers.size()).isGreaterThan(0);
    }

    @DisplayName("TEST TO GET A PLAYER BY ID")
    @Test
    //@Disabled
    public void getAPlayerByIdTest() {

        //give
        Player playerSaved=  playerRepositoryUnderTest.save(player);

        //when
        Player playerFound = playerRepositoryUnderTest.findById(playerSaved.getId()).get();

        //then
        assertThat(playerFound).isNotNull();
    }

    @DisplayName("TEST TO UPDATE A PLAYER BY ID")
    @Test
    //@Disabled
    public void updatePlayerByIdTest() {

        //give
        Player playerSaved=  playerRepositoryUnderTest.save(player);

        //when
        Player playerFound = playerRepositoryUnderTest.findById(playerSaved.getId()).get();
        playerFound.setName("updatedName");
        playerFound.setEmail("updatedEmail@");

        Player updatedPlayer= playerRepositoryUnderTest.save(playerFound);

        //then
        assertThat(updatedPlayer.getName()).isEqualTo("updatedName");
        assertThat(updatedPlayer.getEmail()).isEqualTo("updatedEmail@");
    }

    @DisplayName("TEST TO DELETE A PLAYER BY ID")
    @Test
    //@Disabled
    public void deletePlayerByIdTest() {

        //give
        Player playerSaved=  playerRepositoryUnderTest.save(player);

        //when
        playerRepositoryUnderTest.deleteById(playerSaved.getId());
        Optional<Player> empleadoOptional = playerRepositoryUnderTest.findById(playerSaved.getId());

        //then
        assertThat(empleadoOptional).isEmpty();
    }

}