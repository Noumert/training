package project.exceptions;

/**
 * Created by Noumert on 15.08.2021.
 */
public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String errorMessage){
        super(errorMessage);
    }
}
