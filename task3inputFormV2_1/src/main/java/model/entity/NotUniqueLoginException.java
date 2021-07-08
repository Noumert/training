package model.entity;

/**
 * Created by Noumert on 08.07.2021.
 */
public class NotUniqueLoginException  extends Exception{
    private String loginData;

    public String getLoginData() {
        return loginData;
    }

    public NotUniqueLoginException(String message, String loginData){
        super(message);
        this.loginData = loginData;
    }
}
