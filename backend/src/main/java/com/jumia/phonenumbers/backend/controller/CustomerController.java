package com.jumia.phonenumbers.backend.controller;

import com.jumia.phonenumbers.backend.controller.dto.Resource;
import com.jumia.phonenumbers.backend.entity.Customer;
import com.jumia.phonenumbers.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.jumia.phonenumbers.backend.controller.dto.Resource.ofData;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Resource> getAllCustomers(Pageable pageable,
                                                    @RequestParam(value = "country", required = false) String country,
                                                    @RequestParam(value = "state", required = false) String state){
        return new ResponseEntity<>(ofData(customerService.getAllCustomers(pageable, country, state)), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Resource> getCustomerById(@PathVariable Integer customerId){
        return new ResponseEntity<>(ofData(customerService.getCustomerById(customerId)), HttpStatus.OK);
    }
}
