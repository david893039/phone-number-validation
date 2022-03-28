package com.jumia.phonenumbers.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BodyHeader {

    private String requestRefId;

    private Integer responseCode;

    private String responseMessage;

    private String timestamp;

    private BodyHeader(Integer responseCode, String responseMessage) {
        this.requestRefId = UUID.randomUUID().toString();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.timestamp = Instant.now().toString();
    }

    public static BodyHeader header(Integer responseCode, String responseMessage) {
        return new BodyHeader(responseCode, responseMessage);
    }
}
