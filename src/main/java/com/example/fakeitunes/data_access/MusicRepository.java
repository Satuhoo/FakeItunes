package com.example.fakeitunes.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MusicRepository {
    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    public ArrayList<String> getFiveRandomArtists() {
        ArrayList<String> randomArtists = new ArrayList<>();
        try {
            //Connect to database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Make SQL query which picks five random artist names from database
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Artist ORDER BY RANDOM() LIMIT 5");
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Adds artist names to the list
            while (resultSet.next()) {
               randomArtists.add(resultSet.getString("Name"));
            }
            System.out.println("Select five random artists successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return randomArtists;
    }

    public ArrayList<String> getFiveRandomSongs() {
        ArrayList<String> randomSongs = new ArrayList<>();
        try {
            //Connect to database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Make SQL query which picks five random songs from database
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Track ORDER BY RANDOM() LIMIT 5");
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Adds songs to the list
            while (resultSet.next()) {
                randomSongs.add(resultSet.getString("Name"));
            }
            System.out.println("Select five random songs successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return randomSongs;
    }

    public ArrayList<String> getFiveRandomGenres() {
        ArrayList<String> randomGenres = new ArrayList<>();
        try {
            //Connect to database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Make SQL query which picks five random genres from database
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Genre ORDER BY RANDOM() LIMIT 5");
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Adds genres to the list
            while (resultSet.next()) {
                randomGenres.add(resultSet.getString("Name"));
            }
            System.out.println("Select five random genres successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return randomGenres;
    }
}
