package cat.dicegame.security.model.Exceptions;

public class NoPlayersFoundRepositoryException extends Exception {
    private String message;

    public NoPlayersFoundRepositoryException(NoPlayersFoundRepositoryException e) {}

    public NoPlayersFoundRepositoryException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return super.getMessage();
    }
}