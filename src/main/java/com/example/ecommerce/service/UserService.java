package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
//        return usersList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id);
    }

    public boolean updateUser(Long id, User user) {
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
                    eu.setFirstName(user.getFirstName());
                    eu.setLastName(user.getLastName());
                    userRepository.save(eu);
                    return true;
                })
                .orElse(false);
    }
}
