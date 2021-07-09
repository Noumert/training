package model.entity;

/**
 * Created by Noumert on 08.07.2021.
 */
public class NoteBook {
    private String firstName;
    private String login;

    public NoteBook(String firstName, String login) throws NotUniqueLoginException {
        this.firstName = firstName;
        if(DBNoteBook.checkLogin(login)){
            this.login = login;
        }
        else {
            throw new NotUniqueLoginException("Not unique login",login);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "NoteBook{" +
                "firstName='" + firstName + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
