package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions;

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
