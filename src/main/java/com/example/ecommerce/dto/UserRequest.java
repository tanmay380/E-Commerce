package com.example.ecommerce.dto;

import com.example.ecommerce.model.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @JsonProperty("address")
    private AddressDTO addressDTO;
}
