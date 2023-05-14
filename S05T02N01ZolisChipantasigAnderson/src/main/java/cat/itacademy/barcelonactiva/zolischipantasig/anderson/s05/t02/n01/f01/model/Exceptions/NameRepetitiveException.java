package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions;


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