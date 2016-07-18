package model;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by rares on 20.03.2016.
 */
public class Play extends AbstractCommand {
    public void execute() {
        if (!arg.matches(getAudioFormat())) {
            System.out.println("The specified file is not audio.");
        } else {
            File file = new File(arg);
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println("Can't open the file.");
            }
        }
    }
}