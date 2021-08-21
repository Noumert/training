package project.exceptions;

/**
 * Created by Noumert on 12.08.2021.
 */
public class DuplicatedEmailException extends Exception{
    public DuplicatedEmailException(String errorMessage){
        super(errorMessage);
    }
}
