package com.jumia.phonenumbers.backend.repository;

import com.jumia.phonenumbers.backend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Page<Customer> findByCountryIgnoreCase(String country, Pageable pageable);

    Page<Customer> findByStateIgnoreCase(String country, Pageable pageable);
}
