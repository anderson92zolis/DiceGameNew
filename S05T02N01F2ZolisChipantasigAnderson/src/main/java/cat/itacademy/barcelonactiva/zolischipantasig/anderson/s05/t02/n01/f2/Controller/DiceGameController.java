package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.Controller;


import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto.RankingDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions.NameRepetitiveException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions.ResourceNotFoundException;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Message.Message;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Service.DiceGameServiceImplem;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/players")
public class DiceGameController {

    private final DiceGameServiceImplem diceGameServiceImplem;


    @Autowired
    public DiceGameController(DiceGameServiceImplem diceGameServiceImplem) {
        super();
        this.diceGameServiceImplem = diceGameServiceImplem;
    }

    //POST: /players: create a player.

    /**
     * This is a POST API endpoint for creating a new player in a DiceGame.
     * It checks if a player with the same name already exists using the verifyPlayerName() method.
     * If a player with the same name exists, it sets an appropriate message and values for the response.
     * If not, it creates a new player and sets the appropriate values for the response.
     * The method returns a ResponseEntity object containing the PlayerDto object and the appropriate HTTP status code.
     */

    @PostMapping()
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDtoRequest) {

        PlayerDto diceGameDtoResponse = null;
        HttpStatus httpStatus;
        if (diceGameServiceImplem.verifyPlayerName(playerDtoRequest.getName())) {
            return new ResponseEntity(new Message("PLAYER ALREADY EXISTS WITH NAME: " + playerDtoRequest.getName()), HttpStatus.OK);
        } else {
            diceGameDtoResponse = diceGameServiceImplem.createPlayer(playerDtoRequest);
            httpStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(diceGameDtoResponse, httpStatus);
    }

    // PUT players: modifies the player's name.

    /**
     * Endpoint: PUT /players/{id}
     * <p>
     * Description:
     * This API endpoint updates an existing player with the provided player ID. It accepts a request body of type PlayerDto, which contains the updated details of the player. The method first attempts to update the player using the diceGameServiceImplem object's updatePlayer() method. If the update is successful, the method returns a ResponseEntity object with the updated PlayerDto and an HTTP status code of 200 (OK). If the update fails due to a NameRepetitiveException or ResourceNotFoundException, the method catches the exception and returns a ResponseEntity object with a Message object containing the exception message and an HTTP status code of 404 (Not Found).
     * <p>
     * Parameters:
     * <p>
     * id (integer): the ID of the player to update, provided as a path variable
     * playerDtoRequest (PlayerDto): the updated details of the player, provided as a request body
     * <p>
     * Returns:
     * <p>
     * ResponseEntity<PlayerDto>: the updated PlayerDto object, along with an HTTP status code. If the update was successful, the status code will be 200 (OK). If the update failed due to a NameRepetitiveException or ResourceNotFoundException, the status code will be 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable ObjectId id, @RequestBody PlayerDto playerDtoRequest) {

        try {
            PlayerDto playerDtoResponse = diceGameServiceImplem.updatePlayer(id, playerDtoRequest);
            return ResponseEntity.ok().body(playerDtoResponse);
        } catch (NameRepetitiveException ex) {
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }


    }


    // POST /players/{id}/games/ : a specific player makes a roll of the dice.

    /**
     * Endpoint for creating a new roll for a player with the given ID.
     *
     * @param id The ID of the player to create the roll for.
     * @return A ResponseEntity containing the PlayerDto for the updated player with the new roll.
     * @throws ResourceNotFoundException If the player with the given ID does not exist.
     */

    @PostMapping("/{id}/games/")
    public ResponseEntity<PlayerDto> createRolls(@PathVariable(name = "id") ObjectId id) {

        try {
            PlayerDto diceGameDtoResponse = diceGameServiceImplem.createRoll(id);
            return ResponseEntity.ok().body(diceGameDtoResponse);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(new Message("THERE IS NOT THE PLAYER " + id), HttpStatus.NOT_FOUND);
        }

    }

    // DELETE /players/{id}/games: deletes the player's rolls.

    /**
     * Deletes all the rolls of a player with the given ID.
     *
     * @param id the ID of the player whose rolls are to be deleted
     * @return a response entity with a message indicating the success or failure of the operation
     */
    @DeleteMapping("/{id}/games")
    public ResponseEntity<Message> deleteRollsofAPlayer(@PathVariable(name = "id") ObjectId id) {

        try {
            diceGameServiceImplem.deleteRollsofAPlayer(id);
            return ResponseEntity.ok().body(new Message("ALL ROLLS WERE DELETED FOR PLAYER WITH ID: " + id));
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.NOT_FOUND);
        }


    }

    // DELETE /players/delete/{id}: deletes the player.

    /**
     * Deletes a player with the specified ID.
     *
     * @param id the ID of the player to delete
     * @return a response entity indicating the result of the operation
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlayerDto> deletePost(@PathVariable(name = "id") ObjectId id) throws ResourceNotFoundException {

        if (diceGameServiceImplem.exitsById(id)) {
            PlayerDto deletedDiceGameDto = diceGameServiceImplem.getPlayerDtoByIdWithOverage(id);
            diceGameServiceImplem.deleteUser(id);
            return new ResponseEntity(new Message("PLAYER DELETED BY ID: " + id + " WITH NAME " + deletedDiceGameDto.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("THERE IS NOT THE PLAYER " + id), HttpStatus.NOT_FOUND);
        }
    }

    // GET /players/: returns the list of all players in the system with their average success rate.

    /**
     * Retrieves all players currently in the game.
     *
     * @return a ResponseEntity object containing a list of PlayerDto objects with status code 200 (OK) if the request is successful.
     * If there are no players in the game, returns a ResponseEntity object with a custom header "Custom-Header" and a message "THERE IS NOT PLAYER IN THE DICEGAME" with status code 200 (OK).
     * If an error occurs, returns a ResponseEntity object with status code 500 (INTERNAL_SERVER_ERROR).
     * @throws NoSuchElementException if the list of players returned by the getAllUsersInTheGame method is empty.
     */

    @GetMapping("/")
    public ResponseEntity<?> getAllPlayers() {

        try {
            List<PlayerDto> listPlayerDto = diceGameServiceImplem.getAllUsersInTheGame();

            return new ResponseEntity<>(listPlayerDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok().header("Custom-Header", "DICEGAME").body("THERE IS NOT PLAYER IN THE **DICEGAME**");
            //return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // GET /players/{id}/games: returns the list of games played by a player.

    /**
     * Retrieves a player's information by ID along with their roll history and overage.
     * @param id The ID of the player to retrieve.
     * @return A ResponseEntity containing a PlayerDto object representing the retrieved player, along with a HttpStatus of OK (200).
     * @throws ResourceNotFoundException If the player with the given ID does not exist in the database.
     */
    @GetMapping("/{id}/games")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") ObjectId id) throws ResourceNotFoundException {
        if (diceGameServiceImplem.exitsById(id)) {
            PlayerDto playerDto = diceGameServiceImplem.getPlayerDtoByIdWithOverage(id);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("NOT FOUND ID : " + id), HttpStatus.NOT_FOUND);
        }
    }

    // GET /players/ranking: returns the average ranking of all the players in the system. That is, the average percentage of successes.

    /**
     * The code is for a REST API endpoint that returns the overage ranking of all players in a DICE GAME.
     * It uses the @GetMapping annotation to specify the endpoint path.
     * The method calls the getAllUsersInTheGame() and getOverageRankingOfAllPlayer() methods from the diceGameServiceImplem instance to get the necessary data.
     * If there are no players in the game, it returns a NO_CONTENT status code along with an appropriate message.
     * If an exception occurs, it returns an INTERNAL_SERVER_ERROR status code.
     */
    @GetMapping("/ranking")
    public ResponseEntity<?> getOverageRankingOfAllPlayer() {

        try {
            List<PlayerDto> diceGameDtos = diceGameServiceImplem.getAllUsersInTheGame();
            RankingDto rankingDto = diceGameServiceImplem.getOveragesRankingOfAllPlayer();
            if (diceGameDtos.isEmpty()) {
                return new ResponseEntity<>(new Message("THE IS NOT PLAYERS IN DE GAME"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rankingDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // GET /players/ranking/loser: returns the player with the highest loser rate.

    /**
     * Retrieves the player with the worst loss rate and returns their information as a ResponseEntity.
     * If no player is found, returns a "no content" response with an error message.
     * If an error occurs during the retrieval process, returns an "internal server error" response.
     *
     * @return a ResponseEntity containing the PlayerDto of the player with the worst loss rate, or an error message.
     */
    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getWorstLoserRate() {

        try {
            PlayerDto minPDto = diceGameServiceImplem.getPlayerWithTheWorstLossRate().getPlayerWithTheWorstLossPorcentage();
            if (minPDto == null) {
                return new ResponseEntity<>(new Message("NO PLAYER FOUND WITH THE WORST LOSS RATE."), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(minPDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // GET /players/ranking/winner: returns the player with the highest success rat

    /**
     * This endpoint returns the player with the worst success rate in the dice game. It retrieves the information from the diceGameServiceImplem
     * and returns a ResponseEntity with the corresponding HTTP status code and the response body.
     *
     * @return ResponseEntity with a PlayerDto object containing the information of the player with the worst success rate and HttpStatus.OK if it exists,
     * or a Message object with a corresponding message and HttpStatus.NO_CONTENT if it does not exist.
     */

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWorstSuccessRate() {

        PlayerDto minPDto = diceGameServiceImplem.getPlayerWithTheWorstSuccessRate().getPlayerWithTheWorstSuccessPorcentage();
        if (minPDto == null) {
            return new ResponseEntity<>(new Message("NO PLAYER FOUND WITH THE WORST SUCCESSFUL RATE."), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(minPDto, HttpStatus.OK);
        }

    }

}
