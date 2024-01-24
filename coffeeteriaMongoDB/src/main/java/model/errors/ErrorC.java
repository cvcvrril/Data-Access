package model.errors;

public abstract class ErrorC {

    /*Atributos*/

    private String message;
    private int numError;

    /*Constructor*/

    public ErrorC(String message, int numError) {
        this.message = message;
        this.numError = numError;
    }
}
