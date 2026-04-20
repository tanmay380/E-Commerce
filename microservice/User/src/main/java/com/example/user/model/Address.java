package com.example.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Address {
    @Id
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    public String toString(){
        return street + " " + city + " " + state + " " + zip + " " + country;
    }
}
