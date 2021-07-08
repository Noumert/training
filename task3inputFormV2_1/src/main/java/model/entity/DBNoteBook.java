package model.entity;

/**
 * Created by Noumert on 08.07.2021.
 */
public enum DBNoteBook {
    NOTE_ONE("Vova","Vova1234"),
    NOTE_TWO("Dima","FluffyIce"),
    NOTE_THREE("Ivan","Zebra123");

    private final String firstName;
    private final String login;

    DBNoteBook(String firstName, String login) {
        this.firstName = firstName;
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLogin() {
        return login;
    }

    public static boolean checkLogin (String loginData){
        for(DBNoteBook note : DBNoteBook.values()){
            if(note.getLogin().equals(loginData)){
                return false;
            }
        }
        return true;
    }
}
