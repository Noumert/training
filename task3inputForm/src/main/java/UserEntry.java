public class UserEntry {
    private String surname;
    private String nickname;

    public UserEntry(String surname, String nickname) {
        this.surname = surname;
        this.nickname = nickname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserEntry{" +
                "surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
