package cat.dicegame.security.model.Exceptions;

public class ResourceNotFoundException extends Exception {

    private String message;



    public ResourceNotFoundException(NameRepetitiveException e) {}

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return super.getMessage();
    }

}
