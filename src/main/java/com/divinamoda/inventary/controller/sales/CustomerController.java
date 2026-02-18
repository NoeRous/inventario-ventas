package com.divinamoda.inventary.controller.sales;
import org.springframework.web.bind.annotation.*;
import com.divinamoda.inventary.dto.sales.CustomerDTO;
import com.divinamoda.inventary.entity.sales.Customer;
import com.divinamoda.inventary.service.CustomerService;

@RestController
@RequestMapping("/api/customers")

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody CustomerDTO customer) {
        Customer customerEntity = new Customer();
        customerEntity.setFullName(customer.getFullName());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setActive(true);
        return this.customerService.saveCustomer(customerEntity);
    }

    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

}