package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.dto.UserRequest;
import com.example.ecommerce.dto.UserResponse;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getUsersList() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToUserResponse)
                .toList();
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(userRequest, user);
        userRepository.save(user);
    }

    private void updateUserFromRequest(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddressDTO() != null ) {
            Address address = new Address();
            address.setCity(userRequest.getAddressDTO().getCity());
            address.setCountry(userRequest.getAddressDTO().getCountry());
            address.setState(userRequest.getAddressDTO().getState());
            address.setStreet(userRequest.getAddressDTO().getStreet());
            address.setZip(userRequest.getAddressDTO().getZip());
            user.setAddress(address);
        }

    }

    public Optional<UserResponse> getUser(Long id) {
//        return usersList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id)
                .map(this::mapUserToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest user) {
//       return usersList.stream()
//               .filter(user1 -> user1.getId().equals(id))
//               .findFirst()
//               .map(eu -> {
//                           eu.setFirstName(user.getFirstName());
//                           eu.setLastName(user.getLastName());
//                           return true;
//                       }
//               )
//               .orElse(false);
        return userRepository.findById(id)
                .map(eu -> {
                    updateUserFromRequest(user, eu);
                    userRepository.save(eu);
                    return true;
                })
                .orElse(false);
    }

    private UserResponse mapUserToUserResponse(User user) {
        System.out.println(user.getAddress());
        AddressDTO addressDTO = AddressDTO.builder()
                .street(user.getAddress().getStreet())
                .city(user.getAddress().getCity())
                .state(user.getAddress().getState())
                .zip(user.getAddress().getZip())
                .country(user.getAddress().getCountry())
                .build();
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .addressDTO(addressDTO)
                .build();
    }
}
