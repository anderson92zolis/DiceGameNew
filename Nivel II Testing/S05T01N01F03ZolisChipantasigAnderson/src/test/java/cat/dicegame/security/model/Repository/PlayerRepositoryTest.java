package cat.dicegame.security.model.Repository;

import cat.dicegame.security.model.Entity.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Evitar reemplazo de la fuente de datos
class PlayerRepositoryTest {

    //@Autowired
    @MockBean
    private PlayerRepository playerRepositoryUnderTest;


    @DisplayName("TEST TO FIND BY EMAIL")
    @Test
    public void findByEmailTest() {


        //give

        Player player1 = new  Player("anderson");

        player1.setEmail("a@gmail.com");
        player1.setPassword("1");

        Player foundPlayer = playerRepositoryUnderTest.save(player1);

        //when

        //Optional<Player> foundPlayer = playerRepositoryUnderTest.save(player1);
        System.out.println(foundPlayer);

        //then
      assertThat(foundPlayer).isNotNull();
      assertThat(foundPlayer.getEmail()).isEqualTo("a@gmail.com");


    }





}