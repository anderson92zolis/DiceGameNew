package cat.dicegame.security.model.Repository;

import cat.dicegame.security.auth.RegisterRequest;
import cat.dicegame.security.model.Entity.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import cat.dicegame.security.model.Repository.PlayerRepository;


import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepositoryUnderTest;

    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void findByEmailTest() {

        //give

        RegisterRequest registerRequest = new RegisterRequest(
            "name", "b@gmail.com", "password"
            );

        playerRepositoryUnderTest.save(modelMapper.map(registerRequest, Player.class));

        //when

        Optional<Player> foundPlayer = playerRepositoryUnderTest.findByEmail("b@gmail.com");

        //then
        Assertions.assertThat(foundPlayer.isPresent());

    }



}