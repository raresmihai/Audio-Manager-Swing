package model;

/**
 * Created by rares on 30.03.2016.
 */

import org.apache.tika.metadata.Metadata;
import view.AudioTree;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


import java.util.List;

/**
 * Created by rares on 20.03.2016.
 */
public class Favorite extends AbstractCommand {
    public void execute() {
        if (!arg.matches(getAudioFormat())) {
            System.out.println("Invalid file!");
        } else {
            MetadataExtractor metadataExtractor = new MetadataExtractor();
            Metadata metadata = metadataExtractor.extract(arg);
            String title = metadata.get("title");
            String author = metadata.get("Author");
            String album = metadata.get("xmpDM:album");
            String duration = metadata.get("xmpDM:duration");
            String genre = metadata.get("xmpDM:genre");
            Song song = new Song(title, author, album, duration, genre, arg);

            FavoritesList favoritesList = new FavoritesList();

            JAXBContext context = null;
            try {
                context = JAXBContext.newInstance(FavoritesList.class);
                File file = new File("favorites.xml");
                if (file.exists()) {
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    favoritesList = (FavoritesList) unmarshaller.unmarshal(new File("favorites.xml"));
                    if (favoritesList.doesntContainSong(song)) {
                        favoritesList.addFavorite(song);
                        List<Song> favs = favoritesList.getFavorites();

                        FileWriter favWriter = new FileWriter("favorites.xml");

                        Marshaller marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        marshaller.marshal(favoritesList, favWriter);
                        AudioTree.updateFavorites(song.getFileName());
                    }
                } else {
                    favoritesList.addFavorite(song);
                    FileWriter favWriter = new FileWriter("favorites.xml");
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(favoritesList, favWriter);
                    AudioTree.updateFavorites(song.getFileName());
                }
            } catch (JAXBException | IOException e) {
                e.printStackTrace();
            }


        }
    }

}




