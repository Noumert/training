import java.util.List;

public class View {
    // text constants
    public static final String INPUT_SURNAME = "Input surname: ";
    public static final String INPUT_NICKNAME = "Input nickname: ";
    public static final String WRONG_INPUT_DATA = "Wrong input! Repeat please! ";
    public static final String RESULT = "ALL ENTRIES = ";

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printList(List<String> dictionary) {
        for (String userEntry: dictionary
             ) {
            System.out.println(userEntry);
        }
    }
}
