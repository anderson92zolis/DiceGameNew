package cat.dicegame.security.Controller;


import cat.dicegame.security.model.Dto.PlayerDto;
import cat.dicegame.security.model.Dto.RankingDto;
import cat.dicegame.security.model.Exceptions.NameRepetitiveException;
import cat.dicegame.security.model.Exceptions.NoPlayersFoundRepositoryException;
import cat.dicegame.security.model.Exceptions.ResourceNotFoundException;
import cat.dicegame.security.model.Message.Message;
import cat.dicegame.security.model.Service.PlayerServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Dice Game Management System", description = "CRUD operations from  DICEGAMEJWT MONGODB")
public class DiceGameController {

    private final PlayerServiceImp playerServiceImp;


    @Autowired
    public DiceGameController(PlayerServiceImp playerServiceImp) {
        super();
        this.playerServiceImp = playerServiceImp;
    }

    /**
     * Endpoint: PUT /{id}
     * <p>
     * Description:
     * This API endpoint updates an existing player with the provided player ID. It accepts a request body of type PlayerDto, which contains the updated details of the player. The method first attempts to update the player using the diceGameServiceImplem object's updatePlayer() method. If the update is successful, the method returns a ResponseEntity object with the updated PlayerDto and an HTTP status code of 201 (OK). If the update fails due to a NameRepetitiveException or ResourceNotFoundException, the method catches the exception and returns a ResponseEntity object with a Message object containing the exception message and an HTTP status code of 404 (Not Found).
     * <p>
     * Parameters:
     * <p>
     * id (ObjectId): the ID of the player to update, provided as a path variable
     * playerDtoRequest (PlayerDto): the updated details of the player, provided as a request body
     * <p>
     * Returns:
     * <p>
     * ResponseEntity<PlayerDto>: the updated PlayerDto object, along with an HTTP status code. If the update was successful, the status code will be 201 (OK). If the update failed due to a NameRepetitiveException or ResourceNotFoundException, the status code will be 404 (Not Found).
     */
    @Operation(summary = "UPDATE",
            description = "UPDATE A NAME OF A PLAYER FROM THE DATABASE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "THE NAME ALREADY EXISTS IN THE DATABASE", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "201", description = "SUCCESSFULLY UPDATED PLAYER", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "PLAYER WITH ID X NOT FOUND", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error while updating the player", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable ObjectId id, @RequestBody PlayerDto playerDtoRequest) {

        try {
            PlayerDto playerDtoResponse = playerServiceImp.updatePlayer(id, playerDtoRequest);
            return ResponseEntity.status(201).body(playerDtoResponse);
            //return ResponseEntity.ok().body(playerDtoResponse);
        } catch (NameRepetitiveException ex) {
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Endpoint: POST /{id}/games/
     * Endpoint for creating a new roll game for a player with the given ID.
     *
     * @param id The ID of the player to create the roll for.
     * @return A ResponseEntity containing the PlayerDto for the updated player with the new roll.
     * @throws ResourceNotFoundException If the player with the given ID does not exist.
     */
    @Operation(summary = "CREATE ROLLS FOR A PLAYER", description = "CREATING A NEW ROLL GAME FOR A PLAYER WITH THE GIVEN ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "ROLLS CREATED SUCCESSFULLY",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlayerDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "PLAYER NOT FOUND",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error while creating a roll",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))})

    })
    @PostMapping("/{id}/games/")
    public ResponseEntity<PlayerDto> createRolls(@PathVariable(name = "id") ObjectId id) {

        try {
            PlayerDto diceGameDtoResponse = playerServiceImp.createRoll(id);
            return ResponseEntity.status(201).body(diceGameDtoResponse);
            //return ResponseEntity.ok().body(diceGameDtoResponse);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(new Message("THERE IS NOT THE PLAYER WITH ID: " + id), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint: DELETE  /{id}/games
     * Deletes all the rolls of a player with the given ID.
     *
     * @param id the ID of the player whose rolls are to be deleted
     * @return a response entity with a message indicating the success or failure of the operation
     */

    @Operation(summary = "DELETE ROLLS", description = "DELETES ALL THE ROLLS OF A PLAYER WITH THE GIVEN ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ROLLS DELETED SUCCESSFULLY",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "PLAYER NOT FOUND",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error while deleting a roll",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))})
    })
    @DeleteMapping("/{id}/games")
    public ResponseEntity<Message> deleteRollsofAPlayer(@PathVariable(name = "id") ObjectId id) {
        try {
            playerServiceImp.deleteRollsofAPlayer(id);
            return ResponseEntity.ok().body(new Message("ALL ROLLS WERE DELETED FOR PLAYER WITH ID: " + id));
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Endpoint: DELETE /delete/{id}:
     * Deletes a player with the specified ID.
     *
     * @param id the ID of the player to delete
     * @return a response entity indicating the result of the operation
     */
    @Operation(summary = "Delete a player by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "404", description = "Player not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error while deleting a player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable(name = "id") ObjectId id) throws ResourceNotFoundException {

        if (playerServiceImp.exitsById(id)) {
            PlayerDto deletedDiceGameDto = playerServiceImp.getPlayerDtoByIdWithOverage(id);
            playerServiceImp.deleteUser(id);
            return new ResponseEntity(new Message("PLAYER DELETED BY ID: " + id + " WITH NAME " + deletedDiceGameDto.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("THERE IS NOT THE PLAYER " + id), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Endpoint: GET /
     * Retrieves all players in the system with their average success rate.
     *
     * @return a ResponseEntity object containing a list of PlayerDto objects with status code 201 (OK) if the request is successful.
     * If there are no players in the game, returns a ResponseEntity object with a message "THERE IS NOT PLAYER IN THE DICEGAME" with status code 200 (OK).
     * If an error occurs, returns a ResponseEntity object with status code 500 (INTERNAL_SERVER_ERROR).
     * */

    @Operation(summary = "Get all players",
            description = "Retrieve all players in the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESSFULLY RETRIEVED PLAYERS",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class)))}),
            @ApiResponse(responseCode = "200", description = "NO PLAYERS FOUND",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllPlayers() {
        try {
            List<PlayerDto> listPlayerDto = playerServiceImp.getAllPlayersInTheGameWithOverage();
            return new ResponseEntity<>(listPlayerDto, HttpStatus.CREATED);
        } catch (NoPlayersFoundRepositoryException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint: GET /{id}/games
     * Retrieves a player's information by ID along with their roll history and overage.
     *
     * @param id The ID of the player to retrieve.
     * @return A ResponseEntity containing a PlayerDto object representing the retrieved player, along with a HttpStatus of OK (200).
     * @throws ResourceNotFoundException If the player with the given ID does not exist in the database.
     */

    @Operation(summary = "ROLL OF A PLAYER BY ID",
            description = "RETURNS THE LIST OF PLAYS BY A PLAYER.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY RETRIEVED PLAYER",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerDto.class))}),
            @ApiResponse(responseCode = "404", description = "PLAYER NOT FOUND",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/{id}/games")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") ObjectId id) throws ResourceNotFoundException {
        if (playerServiceImp.exitsById(id)) {
            PlayerDto playerDto = playerServiceImp.getPlayerDtoByIdWithOverage(id);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("NOT FOUND ID : " + id), HttpStatus.NOT_FOUND);
        }
    }



    /**
     *
     *  // GET /players/ranking: returns the average ranking of all the players in the system. That is, the average percentage of successes.
     * The code is for a REST API endpoint that returns the overage ranking of all players in a DICE GAME.
     * It uses the @GetMapping annotation to specify the endpoint path.
     * The method calls the getAllUsersInTheGame() and getOverageRankingOfAllPlayer() methods from the diceGameServiceImplem instance to get the necessary data.
     * If there are no players in the game, it returns a NO_CONTENT status code along with an appropriate message.
     * If an exception occurs, it returns an INTERNAL_SERVER_ERROR status code.
     */
    @GetMapping("/ranking")
    public ResponseEntity<?> getOverageRankingOfAllPlayer() {

        try {
            List<PlayerDto> diceGameDtos = playerServiceImp.getAllPlayersInTheGameWithOverage();
            RankingDto rankingDto = playerServiceImp.getOveragesRankingOfAllPlayer();
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
            PlayerDto minPDto = playerServiceImp.getPlayerWithTheWorstLossRate().getPlayerWithTheWorstLossPorcentage();
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
    public ResponseEntity<?> getWorstSuccessRate() throws NoPlayersFoundRepositoryException {

        PlayerDto minPDto = playerServiceImp.getPlayerWithTheWorstSuccessRate().getPlayerWithTheWorstSuccessPorcentage();
        if (minPDto == null) {
            return new ResponseEntity<>(new Message("NO PLAYER FOUND WITH THE WORST SUCCESSFUL RATE."), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(minPDto, HttpStatus.OK);
        }

    }

}
