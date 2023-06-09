package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Service;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.RankingDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.RollDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity.Player;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity.Roll;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions.NameRepetitiveException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions.ResourceNotFoundException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Repository.PlayerRepository;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Repository.RollRepository;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Service.AleatoryDiceMethod.extraMethodsForRoller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.NoSuchElementException;

@Service
public class DiceGameServiceImplem implements PlayerInterface, RollInterface {

    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();

    private final PlayerRepository playerRepository;
    private final RollRepository rollRepository;


    @Autowired
    public DiceGameServiceImplem(PlayerRepository playerRepository, RollRepository rollRepository) {
        super();
        this.playerRepository = playerRepository;
        this.rollRepository = rollRepository;
    }

    // MODEL-MAPPER

    public PlayerDto convertPlayerEntitytoDTO(Player diceGameEntity) {
        PlayerDto dto = modelMapper.map(diceGameEntity, PlayerDto.class);
        return dto;
    }

    public Player convertPlayerDTOtoEntity(PlayerDto diceGameDto) {
        Player entity = modelMapper.map(diceGameDto, Player.class);
        return entity;
    }

    /**
     * Creates a new player with the given playerDtoNameRequest. If the name of the player is null or empty, the method sets
     * the name to "ANONYMOUS". Then, it creates a new Player entity and saves it to the database through the playerRepository.
     * It then converts the Player entity to a PlayerDto using the convertPlayerEntitytoDTO method and returns the newly created
     * PlayerDto object by calling the getPlayerDtoByIdWithOverage method with the id of the newly created player.
     *
     * @param playerDtoNameRequest the PlayerDto object containing the name of the new player to be created
     * @return a PlayerDto object representing the newly created player
     */
    public PlayerDto createPlayer(PlayerDto playerDtoNameRequest) {

        String namePlayerDtoRequest = playerDtoNameRequest.getName();

        if (namePlayerDtoRequest == null || namePlayerDtoRequest.trim().isEmpty()) {
            namePlayerDtoRequest = "ANONYMOUS";
        }

        Player player = new Player(namePlayerDtoRequest);
        PlayerDto playerDtoCreated = convertPlayerEntitytoDTO(playerRepository.save(player));
        averageSuccessRateGetONE(playerDtoCreated);
        return playerDtoCreated;
    }

    /**
     * Updates the information of an existing player with the specified ID. If the player does not exist, a ResourceNotFoundException is thrown.
     * If the player's name is already taken by another player, a NameRepetitiveException is thrown.
     *
     * @param id               the ID of the player to be updated
     * @param playerDtoRequest a PlayerDto object containing the updated information for the player
     * @return a PlayerDto object representing the updated player
     * @throws NameRepetitiveException   if the specified name is already taken by another player
     * @throws ResourceNotFoundException if no player with the specified ID is found
     */
    @Override
    public PlayerDto updatePlayer(int id, PlayerDto playerDtoRequest) throws NameRepetitiveException, ResourceNotFoundException {

        if (!exitsById(id)) {
            throw new ResourceNotFoundException("PLAYER WITH ID " + id + " NOT FOUND");
        }

        if (verifyPlayerName(playerDtoRequest.getName())) {
            throw new NameRepetitiveException("THE NAME ALREADY EXISTS IN THE DATABASE");
        }

        Player player = getPlayerById(id);
        player.setName(playerDtoRequest.getName());
        PlayerDto playerDtoResponse = convertPlayerEntitytoDTO(playerRepository.save(player));
        return getPlayerDtoByIdWithOverage(playerDtoResponse.getId());
    }

    /**
     * Creates a new roll for a player with the specified ID.
     *
     * @param id The ID of the player to create the roll for.
     * @return The DTO representing the updated player object.
     * @throws ResourceNotFoundException if no player with the specified ID exists.
     */
    @Override
    public PlayerDto createRoll(int id) throws ResourceNotFoundException {

        Player player = getPlayerById(id);

        Roll roll = new Roll();
        roll.setDice1(extraMethodsForRoller.randomNumbers());
        roll.setDice2(extraMethodsForRoller.randomNumbers());
        roll.setResult(extraMethodsForRoller.returnWinnerLost(roll.getDice1(), roll.getDice2()));

        roll.setPlayer(player);
        player.addRolls(roll);

        playerRepository.save(player);
        rollRepository.save(roll);


        PlayerDto savedPlayer = getPlayerDtoByIdWithOverage(id);

        return savedPlayer;

    }


    /**
     * Deletes all the rolls of a player with the given ID.
     *
     * @param id the ID of the player whose rolls are to be deleted
     * @throws ResourceNotFoundException if no player with the given ID exists
     */
    @Override
    public void deleteRollsofAPlayer(int id) throws ResourceNotFoundException {

        if (!exitsById(id)) {
            throw new ResourceNotFoundException("PLAYER WITH ID " + id + " NOT FOUND");
        }

        PlayerDto diceGameDto = getPlayerDtoByIdWithOverage(id);
        Player player = convertPlayerDTOtoEntity(diceGameDto);
        player.deleteRolls();
        playerRepository.save(player);

    }

    /**
     * Deletes a player with the specified ID.
     *
     * @param id the ID of the player to delete
     */

    @Override
    public void deleteUser(int id) {
        playerRepository.deleteById(id);
    }

    /**
     * Returns a list of all playersDto in the game with their respective averages of success rates
     *
     * @return a List<PlayerDto> containing all the players and their success rates
     * @throws NoSuchElementException if the list of players is empty
     */
    @Override
    public List<PlayerDto> getAllUsersInTheGame() throws NoSuchElementException {

        List<PlayerDto> listPlayerDto = convertListOfPlayerToListOfPLayerDTO();

        averageSuccessRate(listPlayerDto); // put the Overages of the players
        return listPlayerDto;

    }

    /**
     * Calculates the average success rate for a list of players.
     *
     * @param playerDtoList the list of players for which the average success rate is to be calculated
     * @return the updated list of players with their average success rate and other related fields set
     */

    private List<PlayerDto> averageSuccessRate(List<PlayerDto> playerDtoList) {

        for (PlayerDto playerDto : playerDtoList) {
            List<RollDto> listRollDto = playerDto.getRollsList();

            if (listRollDto.isEmpty()) {
                playerDto.setAverageSuccessRate("YOU HAVEN'T PLAYED ANY GAME");
                playerDto.setAverageLoserRateNumber(0.0);
                playerDto.setAverageSuccessRateNumber(0.0);
            } else {
                double countSuccess = listRollDto.stream().filter(roll -> roll.getResult().equalsIgnoreCase("WIN")).count();
                double resultNumberSuccess = countSuccess / listRollDto.size() * 100;
                double resultNumberLoser = 100 - resultNumberSuccess;
                playerDto.setAverageLoserRateNumber(resultNumberLoser);
                playerDto.setAverageSuccessRateNumber(resultNumberSuccess);
                playerDto.setAverageSuccessRate("YOUR AVERAGE SUCCESS RATE IS " + resultNumberSuccess + "%");
            }
        }
        return playerDtoList;

    }

    /**
     * @param id the ID of the player to retrieve.
     * @return a {@link PlayerDto} object with the player's information and average success rate.
     */
    @Override
    public PlayerDto getPlayerDtoByIdWithOverage(int id) throws ResourceNotFoundException {
        PlayerDto playerDto = convertPlayerEntitytoDTO(getPlayerById(id));
        averageSuccessRateGetONE(playerDto);
        return playerDto;
    }

    /**
     * Calculates the average success rate for a single player and updates the corresponding fields in the given PlayerDto object.
     * If the player hasn't played any games, sets the success rate fields to 0 and sets the corresponding message.
     *
     * @param playerDto the PlayerDto object to update with the calculated success rate and corresponding fields
     */
    public static void averageSuccessRateGetONE(PlayerDto playerDto) {

        List<RollDto> rollsListDto = playerDto.getRollsList();

        if (rollsListDto.isEmpty()) {
            playerDto.setAverageSuccessRate("YOU HAVEN'T PLAYED ANY GAME");
            playerDto.setAverageLoserRateNumber(0.0);
            playerDto.setAverageSuccessRateNumber(0.0);
        } else {
            double countSucess = rollsListDto.stream()
                    .filter(roll -> roll.getResult().equalsIgnoreCase("WIN"))
                    .count();
            double successRate = countSucess / rollsListDto.size() * 100;
            double loserRate = 100 - successRate;
            playerDto.setAverageSuccessRate("YOUR AVERAGE SUCCESS RATE IS " + successRate + " % PERCENTAGE");
            playerDto.setAverageLoserRateNumber(loserRate);
            playerDto.setAverageSuccessRateNumber(successRate);
        }

    }

    /**
     * This is an overridden method named getOverageRankingOfAllPlayer().
     * It returns a RankingDto object.
     * The method calls three functions sequentially: convertListOfPlayerToListOfPLayerDTO(), averageSuccessRate(), and averageSuccessRanking(), passing the result of the previous function to the next one as input.
     * The purpose of the method is to calculate the average success rate of all players, rank them based on their success rate, and return a RankingDto object containing the ranked list of players.
     */
    @Override
    public RankingDto getOverageRankingOfAllPlayer() {

        return averageSuccessRanking(
                averageSuccessRate(
                        convertListOfPlayerToListOfPLayerDTO(

                        )
                )
        );

    }

    /**
     * Calculates the average success ranking of all players based on their average success rate.
     * @param playerDtoList list of player data transfer objects to calculate the ranking for
     * @return ranking data transfer object containing the average success ranking
     */
    private RankingDto averageSuccessRanking(List<PlayerDto> playerDtoList) {

        Double sumOfPlayerThatHavePlayedSuccessRanking = 0.0;
        Double averageSuccessRanking = 0.0;

        RankingDto rankingDto = new RankingDto();

        for (PlayerDto playerDto : playerDtoList) {

            List<RollDto> listRollDto = playerDto.getRollsList();

            if (!listRollDto.isEmpty()) {
                averageSuccessRanking += playerDto.getAverageSuccessRateNumber();
                sumOfPlayerThatHavePlayedSuccessRanking++;
            }
            rankingDto.setOverageRankingAllPlayer(averageSuccessRanking / sumOfPlayerThatHavePlayedSuccessRanking);
        }

        return rankingDto;


    }

    /**
     * Retrieves the player with the worst loss rate from the list of all players and returns it as a RankingDto.
     *
     * @return a RankingDto containing the player with the worst loss rate.
     */
    @Override
    public RankingDto getPlayerWithTheWorstLossRate() {


        return worstLoserRateMethod(
                averageSuccessRate(
                        getAllUsersInTheGame()
                )
        );

    }


    /**
     Finds the player with the worst success rate from a list of players
     @param playerDtoList List of PlayerDto objects containing player information
     @return RankingDto object with information of the player with the worst success rate
     */
    private RankingDto worstLoserRateMethod(List<PlayerDto> playerDtoList) {

        PlayerDto minPlayerWithTheWorstSuccessRate = null;
        RankingDto rankingDto = new RankingDto();

        List<PlayerDto> filteredPlayers = playerDtoList.stream()
                .filter(playerDto -> !playerDto.getAverageSuccessRate().equalsIgnoreCase("YOU HAVEN'T PLAYED ANY GAME"))
                .collect(Collectors.toList());

        if (!filteredPlayers.isEmpty()) {
            minPlayerWithTheWorstSuccessRate = filteredPlayers
                    .stream()
                    .min(Comparator.comparing(PlayerDto::getAverageLoserRateNumber))
                    .orElseThrow(NoSuchElementException::new);
        }

        rankingDto.setPlayerWithTheWorstLossPorcentage(minPlayerWithTheWorstSuccessRate);

        return rankingDto;

    }

    /**
     Returns the player with the worst success rate in the game.
     @return A {@link RankingDto} object containing the player with the worst success rate.
     */

    @Override
    public RankingDto getPlayerWithTheWorstSuccessRate() {

        return worstSuccessRateMethod(
                averageSuccessRate(
                        getAllUsersInTheGame(
                        )
                )
        );

    }

    /**
     * Method Description: This method is a private method that takes a list of PlayerDto objects as input and returns a RankingDto object. It is used to find the player with the worst success rate among the players who have played the game.
     * Parameters:
     *     playerDtoList: A List of PlayerDto objects which is used to calculate the worst success rate among the players who have played the game.
     * Return Value:
     *     rankingDto: A RankingDto object that contains the player with the worst success rate.
     * Behavior:
     * The method first initializes the minPlayerWithTheWorstSuccessRate variable to null and creates a new RankingDto object. It then uses the Java 8 Stream API to filter the list of players who have played the game and removes the ones who have not played any game. It then calculates the minimum averageSuccessRateNumber of the remaining players using the min function from the Stream API. If no such player is found, it throws a NoSuchElementException. Otherwise, it sets the player with the worst success rate in the RankingDto object and returns it.
     * Example:
     * Suppose we have a list of PlayerDto objects with the following averageSuccessRate values:
     *
     *     "10%"
     *     "50%"
     *     "30%"
     *     "YOU HAVEN'T PLAYED ANY GAME"
     *
     * Then, the method would filter out the last object and find that the player with the worst success rate is the first object with an averageSuccessRateNumber of 10. It would then return a RankingDto object containing this player.
     */
    private RankingDto worstSuccessRateMethod(List<PlayerDto> playerDtoList) {

        PlayerDto minPlayerWithTheWorstSuccessRate = null;
        RankingDto rankingDto = new RankingDto();

        List<PlayerDto> filteredPlayers = playerDtoList.stream()
                .filter(playerDto -> !playerDto.getAverageSuccessRate().equalsIgnoreCase("YOU HAVEN'T PLAYED ANY GAME"))
                .collect(Collectors.toList());

        minPlayerWithTheWorstSuccessRate = filteredPlayers
                .stream()
                .min(Comparator.comparing(PlayerDto::getAverageSuccessRateNumber))
                .orElseThrow(NoSuchElementException::new);

        rankingDto.setPlayerWithTheWorstSuccessPorcentage(minPlayerWithTheWorstSuccessRate);
        return rankingDto;

    }



    /**
     * This method converts all the Player entities retrieved from the database into PlayerDto objects
     * and returns them as a List.
     *@return A List of PlayerDto objects representing all the Player entities in the database.
     * */
    public List<PlayerDto> convertListOfPlayerToListOfPLayerDTO() {

        List<Player> listPlayer = playerRepository
                .findAll();

        if (listPlayer.isEmpty()) {
            throw new NoSuchElementException("THERE ARE NOT PLAYERS IN THE GAME");
        }

        List<PlayerDto> listPlayerDto = listPlayer.stream()
                .map(this::convertPlayerEntitytoDTO)
                .collect(Collectors.toList());

        return listPlayerDto;

    }

    // Check is there is in the database:

    @Override
    public Boolean exitsById(int id) {
        return playerRepository.existsById(id);
    }

    @Override
    public Boolean verifyPlayerName(String namePlayerDtoRequest) {

        boolean verified = false;

        List<Player> playerList = playerRepository.findAll(); // adding overages numbers

        List<Player> filteredPlayers = playerList.stream()
                .filter(player -> player.getName().equalsIgnoreCase(namePlayerDtoRequest))
                .collect(Collectors.toList());

        if (filteredPlayers.size() != 0) {
            verified = true;
        }
        return verified;
    }

    @Override
    public Player getPlayerById(int id) throws ResourceNotFoundException {

        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isPresent()) {
            return optionalPlayer.get();
        } else {
            throw new ResourceNotFoundException("ID NOT EXISTS");
        }
    }

}
