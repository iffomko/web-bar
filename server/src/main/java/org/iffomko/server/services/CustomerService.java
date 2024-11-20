package org.iffomko.server.services;

import org.iffomko.server.domain.Customer;
import org.iffomko.server.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> loadByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }
}
