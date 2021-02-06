package com.example.fakeitunes.models;

public class CustomerSpending {
    private String customerId;
    private String firstName;
    private String lastName;
    private Double total;

    public CustomerSpending(String customerId, String firstName, String lastName, Double total) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.total = total;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
