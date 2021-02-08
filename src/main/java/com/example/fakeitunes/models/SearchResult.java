package com.example.fakeitunes.models;

public class SearchResult {
    private String track;
    private String artist;
    private String album;
    private String genre;

    public SearchResult(String track, String artist, String album, String genre) {
        this.track = track;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
