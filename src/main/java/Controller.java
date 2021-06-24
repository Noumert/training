import java.util.Scanner;

public class Controller {
    // The Constants
    public static final String HELLO = "Hello";
    public static final String WORLD = "world!";

    // Constructor
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    // The Work method
    public void processUser() {
        Scanner sc = new Scanner(System.in);

        view.printMessage(View.INPUT_HELLO_WORD);

        model.setGreeting(inputTextWithScanner(sc, HELLO));
        model.addPartToGreeting(inputTextWithScanner(sc, WORLD));

        view.printMessageAndResult(View.OUR_STRING, model.getGreeting());
    }

    // The Utility methods
    public String inputTextWithScanner(Scanner sc, String expectedInput) {
        String word = sc.nextLine();
        while (!word.equals(expectedInput)) {
            view.printMessage(View.WRONG_INPUT_STRING);
            word = sc.nextLine();
        }
        return word;
    }
}
