package cat.dicegame.security.model.Repository;


import cat.dicegame.security.model.Entity.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, ObjectId> {

    Optional<Player> findByEmail(String email);


}