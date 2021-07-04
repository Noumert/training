public class Main {
    public static void main(String[] args) {
        // Initialization
        View view = new View();
        DictionaryModel dictionaryModel = new DictionaryModel();
        Controller controller = new Controller(view, dictionaryModel);
        // Run
        controller.processUser();
    }
}
