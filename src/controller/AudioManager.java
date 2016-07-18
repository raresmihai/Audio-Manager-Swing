package controller;

import model.Song;

import javax.swing.tree.TreePath;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rares on 30.03.2016.
 */

public class AudioManager {
    static String menuPath;


    static List<Song> favorites = new ArrayList<>();

    public static List<Song> getFavorites() {
        return favorites;
    }

    public static void setFavorites(List<Song> favorites) {
        AudioManager.favorites = favorites;
    }

    public static void addFavorite(Song song) {
        favorites.add(song);
    }

    public static String extractPath(TreePath treePath) {
        String path = treePath.toString();
        path = path.substring(path.lastIndexOf(", ") + 2);
        path = path.substring(0, path.length() - 1);
        return path;
    }

    public static String getMenuPath() {
        return menuPath;
    }

    public static void setMenuPath(String menuPath) {
        AudioManager.menuPath = menuPath;
    }

    public static boolean isAudio(String file) {
        return file.matches("(.*)\\.(mp3|webm)");
    }
}
