package cat.dicegame.security.model.Exceptions;


public class NameRepetitiveException extends Exception {

    private String message;



    public NameRepetitiveException(NameRepetitiveException e) {}

    public NameRepetitiveException(String mensaje) {
        super(mensaje);
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return super.getMessage();
    }

}