package com.example.fakeitunes.models;

import java.util.ArrayList;

public class CustomerAndGenre {
    private String customerId;
    private String firstName;
    private String lastName;
    private ArrayList<String> mostPopularGenres;

    public CustomerAndGenre(String customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getMostPopularGenres() {
        return mostPopularGenres;
    }

    public void setMostPopularGenres(ArrayList<String> mostPopularGenres) {
        this.mostPopularGenres = mostPopularGenres;
    }
}
