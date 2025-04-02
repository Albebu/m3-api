package com.example.api_vuelingMonlau.services;

import com.example.api_vuelingMonlau.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.api_vuelingMonlau.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public ResponseEntity<?> createUser(User user)
    {
        if (
                isNullOrEmpty(user.getEmail()) ||
                isNullOrEmpty(user.getPassword()) ||
                isNullOrEmpty(user.getName()) ||
                isNullOrEmpty(user.getSurname()) ||
                isNullOrEmpty(user.getSecondSurname()) ||
                isNullOrEmpty(user.getPhone())
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "\"message\": \"Insufficient data\""
            );
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "\"message\": \"Email already exists\""
            );
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                "\"message\": \"User created successfully\""
        );
    }

    public User updateUser(Integer id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Solo actualiza si el campo no es null
            if (userDetails.getName() != null) user.setName(userDetails.getName());
            if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
            if (userDetails.getSurname() != null) user.setSurname(userDetails.getSurname());
            if (userDetails.getSecondSurname() != null) user.setSecondSurname(userDetails.getSecondSurname());
            if (userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
            if (userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());

            return userRepository.save(user);  // Guarda solo los campos actualizados
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public ResponseEntity<?> getUserById(Integer id)
    {
        try {
            User user = userRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            String error = "User not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"message\": \"User not found\"}"
            );
        }
    }

    public ResponseEntity<?> deleteUser(Integer id)
    {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"message\": \"User does not exist.\"}"
            );
        }
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    "{\"message\": \"User successfully deleted\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "{\"message\": \"Internal Error\"}" + e.getMessage()
            );
        }
    }
}
