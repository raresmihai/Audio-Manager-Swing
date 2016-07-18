package model;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by rares on 19.03.2016.
 */
public abstract class AbstractCommand implements Command {
    String arg;

    public abstract void execute();

    public void setArgument(String arg) {
        this.arg = arg;
    }

    public String getArgument() {
        return arg;
    }

    public String getAudioFormat() {
        return "(.*)\\.(mp3|webm)";
    }

}
