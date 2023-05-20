package cat.dicegame.security.auth;


import cat.dicegame.security.model.Message.Message;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Dice Management System", description = "CRUD operations from  DICEGAMEJWT MONGODB")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //Add a new user
    @Operation(summary = "ADD", description = "Add a FLOWER to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESSFULLY ADDED FLOWER"),
            @ApiResponse(responseCode = "400", description = "ERROR. THE NAME IS REQUIRED."),
            @ApiResponse(responseCode = "400", description = "ERROR. THE COUNTRY IS REQUIRED."),
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        if (authenticationService.verifyPlayerName(request.getName()) ) {
            return new ResponseEntity(new Message("PLAYER ALREADY EXISTS WITH NAME: " + request.getName()), HttpStatus.OK);
        }
        if (authenticationService.verifyFindByEmail(request.getEmail())) {
            return new ResponseEntity(
                    new Message("PLAYER ALREADY EXISTS WITH THIS EMAIL: " + request.getEmail()), HttpStatus.OK);
        } else {
            return ResponseEntity.ok(authenticationService.register(request));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
