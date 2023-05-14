package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Service;


import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto.RankingDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Entity.Player;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions.NameRepetitiveException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

public interface PlayerInterface {


    PlayerDto createPlayer(PlayerDto diceGameDto);

    PlayerDto updatePlayer(ObjectId id, PlayerDto diceGameDto) throws NameRepetitiveException, ResourceNotFoundException;

    void deleteUser(ObjectId id);

    List<PlayerDto> getAllUsersInTheGame();

    PlayerDto getPlayerDtoByIdWithOverage(ObjectId id) throws ResourceNotFoundException;

    RankingDto getOveragesRankingOfAllPlayer();

    RankingDto getPlayerWithTheWorstLossRate();

    RankingDto getPlayerWithTheWorstSuccessRate();


    // METHODS OF VERIFICATION WITH ID AND NAME OF THE PLAYER

    Boolean exitsById(ObjectId id);
    Boolean verifyPlayerName(String namePlayerDtoRequest);
    Player getPlayerById(ObjectId id) throws ResourceNotFoundException;


}
