package com.example.epolsoftbackend.repositories;

import com.example.epolsoftbackend.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
