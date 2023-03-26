package com.example.epolsoftbackend.repositories;

import com.example.epolsoftbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
