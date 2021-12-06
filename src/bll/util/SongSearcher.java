package bll.util;


import be.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles searching for songs through a list of songs - mainly for All Songs.
 */
public class SongSearcher {
    public List<Song> SearchSongs(List<Song> searchBase, String searchQuery) throws Exception {
        ArrayList<Song> searchedSongs = new ArrayList<>();
        for (Song song : searchBase) {
            if (compareToArtistName(searchQuery, song)
                    || compareToSongName(searchQuery, song)){
                searchedSongs.add(song);
            }
        }
        return searchedSongs;
    }

    /**
     * Compares input to artist names
     * @return true if a match is found
     */
    private boolean compareToArtistName(String query, Song song)
    {
        return song.getArtistName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Compares input to song titles
     * @return true if a match is found
     */
    private boolean compareToSongName(String query, Song song)
    {
        return song.getName().toLowerCase().contains(query.toLowerCase());
    }
}
