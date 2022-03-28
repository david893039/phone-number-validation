package com.jumia.phonenumbers.backend;

import com.jumia.phonenumbers.backend.controller.CustomerController;
import com.jumia.phonenumbers.backend.entity.Country;
import com.jumia.phonenumbers.backend.entity.Customer;
import com.jumia.phonenumbers.backend.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jumia.phonenumbers.backend.utils.TestConstants.TEST_CUSTOMER_ID_INVALID;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    Customer customer;
    Country country;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        customer = new Customer(1, "Customer 1", "(258) 84330678235");
    }

    @After
    public void tearDown() {
        customer = null;
        country = null;
    }

    @Test
    public void shouldReturnAllCustomers() throws Exception {
        this.mockMvc.perform(get("/v1/customers")
                        .param("country","uganda")
                        .param("state", "not valid"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CustomerController.class))
                .andExpect(handler().methodName("getAllCustomers"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnCustomerUsingId() throws Exception {
        this.mockMvc.perform(get("/v1/customers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CustomerController.class))
                .andExpect(handler().methodName("getCustomerById"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("return a 404")
    public void shouldReturnErrorIfCustomerIdNotFound() throws Exception {
        this.mockMvc.perform(get("/v1/customers/"+TEST_CUSTOMER_ID_INVALID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(CustomerController.class))
                .andExpect(handler().methodName("getCustomerById"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }
}
