package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rares on 30.03.2016.
 */
@XmlRootElement(name = "favorites")
public class FavoritesList {
    //@XmlElementWrapper(name="favorites")

    List<Song> favorites = new ArrayList<>();

    @XmlElement(name = "song")
    public List<Song> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Song> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Song song) {
        this.favorites.add(song);
    }

    public boolean doesntContainSong(Song song) {
        for (Song s : favorites) {
            if (s.getFileName().equals(song.getFileName())) {
                return false;
            }
        }
        return true;
    }

}
