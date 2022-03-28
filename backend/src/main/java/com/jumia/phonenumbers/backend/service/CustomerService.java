package com.jumia.phonenumbers.backend.service;

import com.jumia.phonenumbers.backend.entity.Country;
import com.jumia.phonenumbers.backend.entity.Customer;
import com.jumia.phonenumbers.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CategorizationService customersCategorizationService;

    public Page<Customer> getAllCustomers(Pageable pageable, String country, String state){
        Page<Customer> customers = customerRepository.findAll(pageable);
        for (Customer customer : customers) {
            customersCategorizationService.categorizeNumber(customer);
        }

        if(hasText(state) && hasText(country)){
            return new PageImpl<>(customers.stream().filter(customer -> customer.getState().equalsIgnoreCase(state))
                    .filter(customer -> customer.getCountry()
                            .toLowerCase()
                            .contains(country.toLowerCase())).collect(Collectors.toList()));
        }
        if(hasText(country)){
            List<Customer> x = customers.stream()
                    .filter(customer -> customer.getCountry().toLowerCase()
                            .contains(country.toLowerCase())).collect(Collectors.toList());
            return new PageImpl<>(x);
        }
        if(hasText(state)){
            return new PageImpl<>(customers.stream()
                    .filter(customer -> customer.getState().equalsIgnoreCase(state)).collect(Collectors.toList()));
        }

        return customers;
    }

    public Customer getCustomerById(Integer customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for ID: "+customerId));
        customersCategorizationService.categorizeNumber(customer);
        return customer;
    }
}
