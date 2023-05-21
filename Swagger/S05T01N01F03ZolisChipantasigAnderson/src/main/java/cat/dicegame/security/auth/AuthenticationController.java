package cat.dicegame.security.auth;


import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Message.Message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Dice Management System", description = "API operations pertaining to Dice Game MongoDB security")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(summary = "Register a player", description = "register and add a player to the database")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "SUCCESSFULLY REGISTERED",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegisterRequest.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "409", description = "NAME OR A EMAIL OF A PLAYER ALREADY EXISTS",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        if (authenticationService.verifyPlayerName(request.getName()) ) {
            return new ResponseEntity(new Message("PLAYER ALREADY EXISTS WITH NAME: " + request.getName()), HttpStatus.CONFLICT);
        }
        if (authenticationService.verifyFindByEmail(request.getEmail())) {
            return new ResponseEntity(
                    new Message("PLAYER ALREADY EXISTS WITH THIS EMAIL: " + request.getEmail()), HttpStatus.CONFLICT);
        } else {

            return ResponseEntity.ok(authenticationService.register(request));
        }
    }

    @Operation(summary = "Authentication-Login", description = "Login a player in the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TOKEN GENERATED",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "403", description = "ACCESS DENIED",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
