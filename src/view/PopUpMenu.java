package view;

import model.*;
import org.apache.tika.metadata.Metadata;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rares on 30.03.2016.
 */
public class PopUpMenu extends JPopupMenu {
    JMenuItem addFav;
    JMenuItem play;
    JMenuItem search;

    public PopUpMenu() {
        addFav = new JMenuItem("Add to fav");
        addFav.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         AbstractCommand fav = new Favorite();
                                         fav.setArgument(controller.AudioManager.getMenuPath());
                                         fav.execute();
                                     }
                                 }
        );
        add(addFav);
        play = new JMenuItem("Play song");
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AbstractCommand play = new Play();
                play.setArgument(controller.AudioManager.getMenuPath());
                play.execute();
            }
        });
        add(play);
        search = new JMenuItem("Search the web");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AbstractCommand searchWeb = new SearchWeb();
                String fileSelected = controller.AudioManager.getMenuPath();
                MetadataExtractor metadataExtractor = new MetadataExtractor();
                Metadata metadata = metadataExtractor.extract(fileSelected);
                String title = metadata.get("title");
                searchWeb.setArgument(title);
                searchWeb.execute();
            }
        });
        add(search);
    }
}