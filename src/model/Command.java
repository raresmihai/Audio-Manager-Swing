package model;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by rares on 19.03.2016.
 */
public interface Command {
    void execute() throws JAXBException, IOException;

    void setArgument(String arg);

    String getArgument();
}
