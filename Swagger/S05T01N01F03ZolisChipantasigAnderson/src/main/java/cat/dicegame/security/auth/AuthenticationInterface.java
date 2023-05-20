package cat.dicegame.security.auth;

public interface AuthenticationInterface {
    Boolean verifyPlayerName(String namePlayerDtoRequest);
    Boolean verifyFindByEmail(String email);
}

