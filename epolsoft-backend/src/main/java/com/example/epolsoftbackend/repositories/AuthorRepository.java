package com.example.epolsoftbackend.repositories;

import com.example.epolsoftbackend.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
