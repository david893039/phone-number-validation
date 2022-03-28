package com.jumia.phonenumbers.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.time.format.DateTimeFormatter;

import static com.jumia.phonenumbers.backend.utils.TestConstants.TEST_CUSTOMER_ID_INVALID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    private final TestRestTemplate restTemplate;

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

//    @Test
    public void testing(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/customers",
                String.class);

        assertAll("Customers Assertions",
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK));
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jsonCustomizerTest() {
            return builder -> {
                builder.simpleDateFormat(dateTimeFormat);
                builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
                builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            };
        }

    }

}