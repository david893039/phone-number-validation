package com.jumia.phonenumbers.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resource<T> {

    @Setter
    private BodyHeader header;

    @JsonIgnore
    private Integer responseCode;

    private T body;

    private Resource(T body) {
        this.body = body;
    }

    public Resource(T body, Integer responseCode) {
        this.body = body;
        this.responseCode = responseCode;
    }

    public Resource(BodyHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public static <T> Resource<T> ofData(T body) {
        return new Resource<>(body);
    }

    public static <T> Resource<T> ofData(T body, Integer responseCode) {
        return new Resource<>(body, responseCode);
    }

}
