package com.divinamoda.inventary.service;

import com.divinamoda.inventary.dto.sales.CustomerDTO;
import com.divinamoda.inventary.entity.sales.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Iterable<Customer> getAllCustomers();
    
}
