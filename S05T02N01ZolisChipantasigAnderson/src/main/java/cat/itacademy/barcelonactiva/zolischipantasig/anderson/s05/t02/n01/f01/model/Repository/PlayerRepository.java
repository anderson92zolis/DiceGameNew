package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Repository;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

}
