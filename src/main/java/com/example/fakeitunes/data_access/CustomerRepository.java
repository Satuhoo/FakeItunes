package com.example.fakeitunes.data_access;

import com.example.fakeitunes.models.Customer;
import com.example.fakeitunes.models.CustomerSpending;
import com.example.fakeitunes.models.CustomerAndGenre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Stream;

public class CustomerRepository {
    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            //Connect to database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Make SQL query
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId,FirstName,LastName, Country, PostalCode, Phone, Email FROM Customer");
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();
            //Creates customer objects of all customers from database and adds them to list

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        ));
            }
            System.out.println("Select all customers successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return customers;
    }

    public Boolean addCustomer(Customer customer) {
        Boolean success = false;

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO customer(CustomerId,FirstName,LastName, Country, PostalCode, Phone, Email) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getCountry());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhone());
            preparedStatement.setString(7, customer.getEmail());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            System.out.println("Add customer successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return success;
    }

    public Boolean updateCustomer(Customer customer) {
        Boolean success = false;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("UPDATE customer SET FirstName = ?, LastName = ?, Country = ?, PostalCode = ?, Phone = ?, Email = ? WHERE CustomerId=?");
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCountry());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getEmail());
            preparedStatement.setString(7, customer.getCustomerId());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            System.out.println("Update customer successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return success;
    }

    public Stream<Map.Entry<String, Integer>> countCustomersPerCountry() {
        HashMap<String, Integer> customersPerCountry = new HashMap();
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            //Make SQL query which counts customers per country
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT COUNT(CustomerID), Country FROM Customer GROUP BY Country");
            ResultSet resultSet = preparedStatement.executeQuery();

            //Adds all countries and amount of customers to hashmap
            while (resultSet.next()) {
                customersPerCountry.put(resultSet.getString("Country"), resultSet.getInt("COUNT(CustomerId)"));
            }
            System.out.println("Count all customers per country successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        //Sorts list of countries by amount of customers
        Stream<Map.Entry<String, Integer>> sortedListOfCountries =
                customersPerCountry.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sortedListOfCountries;
    }

    public ArrayList<CustomerSpending> getCustomersByHighestSpender() {
        ArrayList<CustomerSpending> customersBySpending = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            //Make SQL query which counts total of all customer's spending
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT SUM(Total) AS Total, Customer.CustomerId, Invoice.CustomerId, Customer.FirstName AS FirstName, Customer.LastName AS LastName FROM Invoice\n" +
                            "INNER JOIN Customer WHERE Customer.CustomerId = Invoice.CustomerId\n" +
                            "GROUP BY Invoice.CustomerId ORDER BY SUM(Total) DESC");
            ResultSet resultSet = preparedStatement.executeQuery();

            //Creates objects which contains customer id, name and their spending total and adds them
            //to list on descending order
            while (resultSet.next()) {
                customersBySpending.add(
                        new CustomerSpending(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getDouble("Total")
                        ));
            }
            System.out.println("Count all customers by their spending successful");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return customersBySpending;
    }

    public CustomerAndGenre getMostPopularGenreForCustomer(String id) {
        HashMap<String, Integer> genres = new HashMap<>();
        CustomerAndGenre genre = null;
        try{
            conn = DriverManager.getConnection(URL);

            //Make SQL query which counts amount of the spending about genres by customer id
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Customer.CustomerId, Customer.FirstName, Customer.LastName, " +
                            "Genre.Name, COUNT(Genre.GenreId) AS Genre FROM Genre\n" +
                            "INNER JOIN Customer, Invoice, InvoiceLine, Track\n" +
                            "WHERE Customer.CustomerId == Invoice.CustomerId\n" +
                            "AND Invoice.InvoiceId == InvoiceLine.InvoiceId\n" +
                            "AND InvoiceLine.TrackId == Track.TrackId\n" +
                            "AND Track.GenreId == Genre.GenreId\n" +
                            "AND Customer.CustomerId == ? GROUP BY Genre.Name");
            preparedStatement.setString(1,id);
            ResultSet set = preparedStatement.executeQuery();
            //Creates new object without genre
            genre = new CustomerAndGenre(
                    set.getString("CustomerId"),
                    set.getString("FirstName"),
                    set.getString("LastName")
            );
            //Adds all genres and values of the customer spending by genre to hashmap
            while(set.next()){
                genres.put(set.getString("Name"),
                        set.getInt("Genre"));

            }
            System.out.println("Get most popular genres for customer successful");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        //Checks most popular genre by it's value
        int maxValue = Collections.max(genres.values());

        ArrayList<String> mostPopularGenres = new ArrayList<>();
        //Adds all genres with max value to the new list
        for (Map.Entry<String, Integer> entry : genres.entrySet()) {
            if (entry.getValue()==maxValue) {
                mostPopularGenres.add(entry.getKey());
            }
        }
        //Sets the list of the most popular genres to the customer object
        genre.setMostPopularGenres(mostPopularGenres);
        return genre;
    }
}
