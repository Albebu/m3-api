package com.example.api_vuelingMonlau.controllers;

import com.example.api_vuelingMonlau.services.UserService;
import com.example.api_vuelingMonlau.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // Obtener un usuario por su ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Crear un nuevo usuario
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Actualizar un usuario existente
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
