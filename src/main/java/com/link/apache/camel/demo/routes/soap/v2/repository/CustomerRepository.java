package com.link.apache.camel.demo.routes.soap.v2.repository;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class CustomerRepository {
    List<Customer> customers = new ArrayList<>();

    @PostConstruct
    private void init() {
        populateCustomers();
    }

    public List<Customer> getCustomersByName(String name) {
        return getCustomersStreamByName(name)
                .collect(Collectors.toList());
    }

    private Stream<Customer> getCustomersStreamByName(String name) {
        return customers.stream().filter(c -> c.getName().equals(name));
    }

    public void updateCustomer(Customer customer) {
        getCustomersStreamByName(customer.getName())
                .forEach(storedCustomer -> {
                    storedCustomer.setRevenue(customer.getRevenue());
                    storedCustomer.setCustomerId(customer.getCustomerId());
                    storedCustomer.setNumOrders(customer.getNumOrders());
                    storedCustomer.setType(customer.getType());
                    storedCustomer.setTest(customer.getTest());
                    storedCustomer.setBirthDate(customer.getBirthDate());
                });
    }

    private void populateCustomers() {
        Customer restCustomer = new Customer();
        restCustomer.setCustomerId(1);
        restCustomer.setName("Neo-Rest");
        restCustomer.setType(CustomerType.PRIVATE);
        restCustomer.setNumOrders(1);
        restCustomer.setBirthDate(LocalDate.now());

        customers.add(restCustomer);
        Customer soapCustomer = new Customer();
        soapCustomer.setCustomerId(2);
        soapCustomer.setName("Neo-SOAP");
        soapCustomer.setType(CustomerType.PRIVATE);
        soapCustomer.setNumOrders(2);
        soapCustomer.setBirthDate(LocalDate.now());

        customers.add(soapCustomer);
    }
}