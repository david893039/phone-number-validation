package com.jumia.phonenumbers.backend.service;

import com.jumia.phonenumbers.backend.entity.Country;
import com.jumia.phonenumbers.backend.entity.Customer;
import com.jumia.phonenumbers.backend.utils.Regex;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategorizationService {

    private final List<Country> countries = new ArrayList<>();

    @PostConstruct
    public void init() {
        countries.add(new Country("(237)", "\\(237\\)\\ ?[2368]\\d{7,8}$", "Cameroon"));
        countries.add(new Country("(251)", "\\(251\\)\\ ?[1-59]\\d{8}$", "Ethiopia"));
        countries.add(new Country("(212)", "\\(212\\)\\ ?[5-9]\\d{8}$", "Morocco"));
        countries.add(new Country("(258)", "\\(258\\)\\ ?[28]\\d{7,8}$", "Mozambique"));
        countries.add(new Country("(256)", "\\(256\\)\\ ?\\d{9}$", "Uganda"));
    }

    public void categorizeNumber(Customer customer) {
        customer.setState("not valid");
        for(Country country : countries) {
            if(Regex.validate(country.getRegex(), customer.getPhone())) {
                customer.setState("valid");
            }

            if(customer.getPhone().contains(country.getCode())) {
                customer.setCountry(country.getName());
                customer.setCountryCode(country.getCode());
            }
        }
    }
}
