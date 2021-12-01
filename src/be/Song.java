package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the Song class, here we specify what a Song is.
 */
public class Song
{
    private StringProperty name;
    private StringProperty artistName;
    private IntegerProperty songLength;
    private IntegerProperty songId;

    public Song(StringProperty name, StringProperty artistName, IntegerProperty songLength)
    {
        this.name = name;
        this.artistName = artistName;
        this.songLength = songLength;
    }

    /*
    Beneath we have getters and setters for the different properties of the class,
    we don't have a setter for ID since we want our database to handle the ID.
     */
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getSongId() {
        return songId.get();
    }

    public String getArtistName() {
        return artistName.get();
    }

    public void setArtistName(String artistName) {
        this.artistName.set(artistName);
    }

    public int getSongLength() {
        return songLength.get();
    }

    public void setSongLength(int songLength) {
        this.songLength.set(songLength);
    }
}
