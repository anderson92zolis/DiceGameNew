package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Service;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.RankingDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity.Player;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions.NameRepetitiveException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface PlayerInterface {


    PlayerDto createPlayer(PlayerDto diceGameDto);

    PlayerDto updatePlayer(int id, PlayerDto diceGameDto) throws NameRepetitiveException, ResourceNotFoundException;

    void deleteUser(int id);

    List<PlayerDto> getAllUsersInTheGame();

    PlayerDto getPlayerDtoByIdWithOverage(int id) throws ResourceNotFoundException;

    RankingDto getOverageRankingOfAllPlayer();

    RankingDto getPlayerWithTheWorstLossRate();

    RankingDto getPlayerWithTheWorstSuccessRate();


    // METHODS OF VERIFICATION WITH ID AND NAME OF THE PLAYER

    Boolean exitsById(int id);
    Boolean verifyPlayerName(String namePlayerDtoRequest);

    Player getPlayerById(int id) throws ResourceNotFoundException;

}
