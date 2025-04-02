package com.example.api_vuelingMonlau.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_vuelingMonlau.models.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}