package com.example.fakeitunes.controllers;

import com.example.fakeitunes.data_access.CustomerRepository;
import com.example.fakeitunes.models.Customer;
import com.example.fakeitunes.models.CustomerSpending;
import com.example.fakeitunes.models.CustomerAndGenre;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class CustomerController {
    CustomerRepository customerRepository = new CustomerRepository();

    @GetMapping("api/customers")
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "api/customers", method = RequestMethod.POST)
    public Boolean addNewCustomer(@RequestBody Customer customer){
        return customerRepository.addCustomer(customer);
    }

    @RequestMapping(value = "api/customers/{CustomerId}", method = RequestMethod.PUT)
    public Boolean updateExistingCustomer(@PathVariable String CustomerId, @RequestBody Customer customer){
        return customerRepository.updateCustomer(customer);
    }

    @RequestMapping(value = "api/customers/countries", method = RequestMethod.GET)
    public Stream<Map.Entry<String, Integer>> getCustomersPerCountry() {
        return customerRepository.countCustomersPerCountry();
    }

    @RequestMapping(value = "api/customers/spenders", method = RequestMethod.GET)
    public ArrayList<CustomerSpending> getCustomersByHighestSpender(){
        return customerRepository.getCustomersByHighestSpender();
    }

    @RequestMapping(value = "api/customers/{id}", method = RequestMethod.GET)
    public CustomerAndGenre getCustomerByPathId(@PathVariable String id){
        return customerRepository.getMostPopularGenreForCustomer(id);
    }


}
