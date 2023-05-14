package cat.dicegame.security.model.Repository;

import cat.dicegame.security.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepositoryUnderTest;

    @Test
    public void randomNumbersTest() {

        //give

        RegisterRequest registerRequest = new RegisterRequest(
            "name", "email", "password"
            );

        playerRepositoryUnderTest.save(registerRequest);

        //when

        boolean exists = playerRepositoryUnderTest.findByEmail("email");

        //then

        assertThat(exists).isTrue();

    }



}