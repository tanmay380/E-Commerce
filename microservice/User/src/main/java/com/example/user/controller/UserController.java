package com.example.user.controller;

import com.example.user.dto.UserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<List<UserResponse>> createUser(@RequestBody UserRequest user) {
        userService.addUser(user);
        return ResponseEntity.ok(userService.getUsersList());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getUsersList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
//        Optional<User> user = userService.getUser(id);
//        if (user == null){
//            return ResponseEntity.notFound().build();
//        }
        return  userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> udpateUser(@PathVariable String id, @RequestBody UserRequest user){
        boolean upadted = userService.updateUser(id, user);
        if (upadted) {
            return ResponseEntity.ok("updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

}
