package project.exceptions;

public class DublicatedEmailException extends Exception{
    public DublicatedEmailException(String errorMessage){
        super(errorMessage);
    }
}
