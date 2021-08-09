package project.exceptions;

public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String errorMessage){
        super(errorMessage);
    }
}
