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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
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

    private Player testPlayer1;
    private Player testPlayer2;
    private RegisterRequest registerRequest;
    private UsernamePasswordAuthenticationToken userPassAuthToken;
    private AuthenticationRequest authRequest;


    @BeforeEach
    public void setUp() {

        // Create a RegisterRequest object
        registerRequest = RegisterRequest.builder()
                .name("testPlayer1")
                .email("testPlayer1@gmail.com")
                .password("password1")
                .build();

        // Create a Player object
        testPlayer1 = Player.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.USER)
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .build();

        testPlayer2 = Player.builder()
                .name("John")
                .email("john@gmail.com")
                .password("passwordJohn")
                .role(Role.USER)
                .localDateTime(LocalDateTime.now())
                .rollsList(new ArrayList<>())
                .build();

        // Create an UsernamePasswordAuthenticationToken object
        userPassAuthToken = new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword());

        // Create an AuthenticationRequest object
        authRequest = new AuthenticationRequest(registerRequest.getEmail(), registerRequest.getPassword());
    }

    @Test
    @DisplayName("TEST REGISTER A PLAYER")
    public void testRegister() {
        // given

        // Mock the behavior of the dependencies
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        // Mock the playerRepository.save() method

        when(playerRepository.save(any())).thenReturn(testPlayer1);
        when(passwordEncoder.encode(any())).thenReturn(registerRequest.getPassword());

        //when

        // Call the register() method
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // then

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());

        // Verify the expected behavior
        verify(playerRepository).save(any());
        verify(passwordEncoder).encode("password1");
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    @DisplayName("TEST AUTHENTICATE A PLAYER")
    public void authenticateTest() {
        //given
        // Mock the behavior of the dependencies
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        // Mock the playerRepository.findByEmail() method
        when(playerRepository.findByEmail("testPlayer1@gmail.com")).thenReturn(Optional.of(testPlayer1));
        // Call the authenticate() method
        when(authenticationManager.authenticate(any())).thenReturn(userPassAuthToken);

        //when
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        //then
        // Verify the expected behavior
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(jwtService).generateToken(testPlayer1);
        verify(playerRepository).findByEmail("testPlayer1@gmail.com");
    }

    @Test
    @DisplayName("TEST VERIFY NAME OF A PLAYER")
    public void testVerifyPlayerName() {
        // Mock the playerRepository.findAll() method
        List<Player> playerList = new ArrayList<>();

        playerList.add(testPlayer1);
        playerList.add(testPlayer2);

        when(playerRepository.findAll()).thenReturn(playerList);

        // Call the verifyPlayerName() method
        boolean verified = authenticationService.verifyPlayerName("John");

        // Verify the expected behavior
        assertTrue(verified);
        verify(playerRepository).findAll();
    }

    @Test
    @DisplayName("TEST VERIFY EMAIL OF A PLAYER")
    public void testVerifyFindByEmail() {

        // Mock the playerRepository.findAll() method

        List<Player> playerList = new ArrayList<>();

        playerList.add(testPlayer1);
        playerList.add(testPlayer2);

        when(playerRepository.findAll()).thenReturn(playerList);

        // Call the verifyFindByEmail() method
        boolean verified = authenticationService.verifyFindByEmail("john@gmail.com");

        // Verify the expected behavior
        assertTrue(verified);
    }
}