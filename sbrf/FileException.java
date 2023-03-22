package sbrf;

import java.io.IOException;

public class FileException extends Exception {

    public FileException() {
        super();
    }

    public FileException(IOException message) {
        super(message);
    }


}