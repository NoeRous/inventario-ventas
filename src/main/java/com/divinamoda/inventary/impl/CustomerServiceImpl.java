package com.divinamoda.inventary.impl;

import org.springframework.stereotype.Service;

import com.divinamoda.inventary.entity.products.Product;
import com.divinamoda.inventary.entity.sales.Customer;
import com.divinamoda.inventary.repository.CustomerRepository;
import com.divinamoda.inventary.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    
}