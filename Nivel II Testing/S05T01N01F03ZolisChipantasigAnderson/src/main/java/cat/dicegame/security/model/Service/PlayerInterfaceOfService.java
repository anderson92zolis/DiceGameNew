package cat.dicegame.security.model.Service;



import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Dto.RankingDto;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Exceptions.NameRepetitiveException;
import cat.dicegame.security.model.Exceptions.NoPlayersFoundRepositoryException;
import cat.dicegame.security.model.Exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

public interface PlayerInterfaceOfService {


    PlayerDto createPlayer(PlayerDto diceGameDto);

    PlayerDto updatePlayer(ObjectId id, PlayerDto diceGameDto) throws NameRepetitiveException, ResourceNotFoundException;

    void deleteUser(ObjectId id);

    List<Player> getAllPlayersFromDB() throws NoPlayersFoundRepositoryException;


    List<PlayerDto> getAllPlayerInTheGameWithOverage() throws NoPlayersFoundRepositoryException;

    PlayerDto getPlayerDtoByIdWithOverage(ObjectId id) throws ResourceNotFoundException;

    RankingDto getOveragesRankingOfAllPlayer() throws NoPlayersFoundRepositoryException;

    RankingDto getPlayerWithTheWorstLossRate() throws NoPlayersFoundRepositoryException;

    RankingDto getPlayerWithTheWorstSuccessRate() throws NoPlayersFoundRepositoryException;


    // METHODS OF VERIFICATION WITH ID AND NAME OF THE PLAYER

    Boolean exitsById(ObjectId id);
    Boolean verifyPlayerName(String namePlayerDtoRequest);
    Player getPlayerById(ObjectId id) throws ResourceNotFoundException;


}
