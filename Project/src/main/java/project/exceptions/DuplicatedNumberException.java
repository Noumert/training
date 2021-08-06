package project.exceptions;

public class DuplicatedNumberException extends Exception{
    public DuplicatedNumberException(String errorMessage){
        super(errorMessage);
    }
}