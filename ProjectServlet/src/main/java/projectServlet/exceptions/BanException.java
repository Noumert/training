package projectServlet.exceptions;

/**
 * Created by Noumert on 20.08.2021.
 */
public class BanException extends Exception {
    public BanException(String errorMessage) {
        super(errorMessage);
    }
}
