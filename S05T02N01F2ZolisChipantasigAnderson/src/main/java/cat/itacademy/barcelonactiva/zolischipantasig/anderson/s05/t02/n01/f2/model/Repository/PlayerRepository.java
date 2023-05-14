package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Repository;


import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Entity.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, ObjectId> {

}
