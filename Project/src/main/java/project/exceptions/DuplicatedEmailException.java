package project.exceptions;

public class DuplicatedEmailException extends Exception{
    public DuplicatedEmailException(String errorMessage){
        super(errorMessage);
    }
}
