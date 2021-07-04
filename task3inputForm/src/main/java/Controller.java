import java.util.Scanner;

public class Controller {
    //Constants
    public static int NUMBER_OF_ENTRIES = 3;

    //Regex
    public static String REGEX_NICKNAME = "^([\\w])+$";
    public static String REGEX_SURNAME = "^([A-Z])+([a-z])*$";
    public static String REGEX_PHONE = "^\\d{10}|\\d{12}$";
    public static String REGEX_HOME_PHONE = "^\\d{7}$";
    public static final String REGEX_MAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public static final String REGEX_SKYPE = "^([a-z])+:(\\.\\w+){2,}$";
    public static final String REGEX_INDEX = "^\\d{5}$";
    public static final String REGEX_CITY = "^([A-Z])+([a-z])*$";

    //Constructor
    private View view;
    private DictionaryModel dictionaryModel;

    public Controller(View view, DictionaryModel dictionaryModel) {
        this.view = view;
        this.dictionaryModel = dictionaryModel;
    }

    // The Work method
    public void processUser() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < NUMBER_OF_ENTRIES; i++) {
            while (!dictionaryModel.addUserEntryToDictionary(inputSurnameScanner(sc), inputNicknameScanner(sc))) ;
        }

        view.printMessage(View.RESULT);
        view.printList(dictionaryModel.toStringList());
    }

    private String inputSurnameScanner(Scanner sc) {
        view.printMessage(View.INPUT_SURNAME);
        String surname = sc.nextLine();
        while (!surname.matches(REGEX_SURNAME)) {
            view.printMessage(View.WRONG_INPUT_DATA);
            surname = sc.nextLine();
        }
        return surname;
    }

    private String inputNicknameScanner(Scanner sc) {
        view.printMessage(View.INPUT_NICKNAME);
        String nickname = sc.nextLine();
        while (!nickname.matches(REGEX_NICKNAME)) {
            view.printMessage(View.WRONG_INPUT_DATA);
            nickname = sc.nextLine();
        }
        return nickname;
    }
}
