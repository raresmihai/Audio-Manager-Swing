package model;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by rares on 30.03.2016.
 */
public class SearchWeb extends AbstractCommand {
    @Override
    public void execute() {
        arg = arg.replace(" ", "+");
        String url = "http://www.google.com/search?q=" + arg;
        URL obj;
        try {
            obj = new URL(url);
            Desktop.getDesktop().browse(obj.toURI());
        } catch (URISyntaxException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
