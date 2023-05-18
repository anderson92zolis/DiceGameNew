package cat.dicegame.security.model.Service;



import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;

public interface GamesInterfaceServ {
    void deleteRollsofAPlayer(ObjectId id) throws ResourceNotFoundException;
    PlayerDto createRoll(ObjectId id) throws ResourceNotFoundException;
}
