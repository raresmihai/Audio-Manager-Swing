package model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rares on 19.03.2016.
 */

//@XmlRootElement(name = "Song")
public class Song implements Serializable {
    String songName;
    String artistName;
    String album;
    String duration;
    String genre;
    String fileName;

    public Song(String songName, String artistName, String album, String duration, String genre, String fileName) {
        this.songName = songName;
        this.artistName = artistName;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.fileName = fileName;
    }

    public Song() {
    }

    @XmlElement(name = "songName")
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @XmlElement(name = "artist")
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @XmlElement(name = "album")
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @XmlElement(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @XmlElement(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.audiomanager.data.Song name: ").append(songName);
        sb.append("\nArtist: ").append(artistName);
        sb.append("\nDuration: ").append(duration);
        sb.append("\nGenre: ").append(genre);
        sb.append("\nAlbum: ").append(album);
        return String.valueOf(sb);
    }
}
