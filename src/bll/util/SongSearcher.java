package bll.util;


import be.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongSearcher {
    public List<Song> SearchSongs(List<Song> searchBase, String searchQuery) throws Exception {
        ArrayList<Song> searchedSongs = new ArrayList<>();
        for (Song song : searchBase)
        {
         if(compareToArtistName(searchQuery, song) || compareToSongName(searchQuery, song))
             searchedSongs.add(song);
        }
        return searchedSongs;
    }

    private boolean compareToArtistName(String query, Song song)
    {
        return song.getArtistName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToSongName(String query, Song song)
    {
        return song.getName().toLowerCase().contains(query.toLowerCase());
    }
}
