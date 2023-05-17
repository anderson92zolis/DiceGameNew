package cat.dicegame.security.auth;

import cat.dicegame.security.config.JwtService;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Entity.Role;
import cat.dicegame.security.model.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationInterface{

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {


        String namePlayerDtoRequest = request.getName();
        if (namePlayerDtoRequest == null || namePlayerDtoRequest.trim().isEmpty()) {
            namePlayerDtoRequest = "ANONYMOUS";
        }

        var userPlayer = Player.builder()
                .name(namePlayerDtoRequest)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .build();

        playerRepository.save(userPlayer);
        var jwtToken = jwtService.generateToken(userPlayer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var user = playerRepository.findByEmail(request.getEmail())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Boolean verifyPlayerName(String namePlayerDtoRequest) {

        boolean verified = false;

        List<Player> playerList = playerRepository.findAll();

        List<Player> filteredPlayers = playerList.stream().filter(player -> player.getName().equalsIgnoreCase(namePlayerDtoRequest)).collect(Collectors.toList());

        if (filteredPlayers.size() != 0) {
            verified = true;
        }
        return verified;
    }

    @Override
    public Boolean verifyFindByEmail(String email) {

        boolean verified = false;

        List<Player> playerList = playerRepository.findAll();

        List<Player> filteredPlayers = playerList.stream().filter(player -> player.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());

        if (filteredPlayers.size() != 0) {
            verified = true;
        }
        return verified;

    }


}
