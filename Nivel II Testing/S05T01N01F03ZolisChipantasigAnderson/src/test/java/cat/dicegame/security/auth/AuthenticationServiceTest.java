package cat.dicegame.security.auth;

import cat.dicegame.security.config.JwtService;
import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Entity.Role;
import cat.dicegame.security.model.Repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private AuthenticationService authenticationService;

    private Player player;
    private RegisterRequest regRequest;
    private UsernamePasswordAuthenticationToken userPassAuthToken;
    private AuthenticationRequest authRequest;


    @BeforeEach
    public void setUp() {

         player = Player.builder()
                .name("A")
                .email("a@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();

        // Create a RegisterRequest object
        regRequest = RegisterRequest.builder()
                .name("A")
                .email("a@gmail.com")
                .password("password")
                .build();

        userPassAuthToken = new UsernamePasswordAuthenticationToken(regRequest.getEmail(), regRequest.getPassword());

        authRequest = new AuthenticationRequest(regRequest.getEmail(),regRequest.getPassword());
    }

    @Test
    @DisplayName("TEST CONVERT ENTITY TO DTO")
    public void testRegister() {
        // given
        // Mock the behavior of the dependencies
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        // Mock the playerRepository.save() method
        when(playerRepository.save(any())).thenReturn(player);

        when(passwordEncoder.encode(any())).thenReturn(regRequest.getPassword());

        //when
        // Call the register() method
        AuthenticationResponse response = authenticationService.register(regRequest);
        // then

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());


        // Verify the expected behavior
        verify(playerRepository).save(any());
        verify(passwordEncoder).encode("password");
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    public void authenticateTest() {
        // Create an AuthenticationRequest object
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        // Mock the behavior of the dependencies
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        // Mock the playerRepository.findByEmail() method
        Player user = new Player();
        when(playerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        // Call the authenticate() method
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Verify the expected behavior
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(jwtService).generateToken(user);
        verify(playerRepository).findByEmail("john@example.com");
    }

    @Test
    public void testVerifyPlayerName() {
        // Mock the playerRepository.findAll() method
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player();
        player1.setName("John");
        playerList.add(player1);
        when(playerRepository.findAll()).thenReturn(playerList);

        // Call the verifyPlayerName() method
        boolean verified = authenticationService.verifyPlayerName("John");

        // Verify the expected behavior
        assertTrue(verified);
        verify(playerRepository).findAll();
    }

    @Test
    public void testVerifyFindByEmail() {
        // Mock the playerRepository.findAll() method
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player();
        player1.setEmail("john@example.com");
        playerList.add(player1);
        when(playerRepository.findAll()).thenReturn(playerList);

        // Call the verifyFindByEmail() method
        boolean verified = authenticationService.verifyFindByEmail("john@example.com");

        // Verify the expected behavior
        assertTrue(verified);

    }
}