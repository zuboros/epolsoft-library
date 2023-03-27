package com.example.epolsoftbackend.repositories;

import com.example.epolsoftbackend.entities.Book;
import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET name = ?1, topicId = ?2, userId = ?3, description = ?4," +
                   "shortDescription = ?5, file = ?6  WHERE id = ?7", nativeQuery = true)
    void updateById( String name, Topic topicId, User userId, String description, String shortDescription, String file, long id);

    @EntityGraph(attributePaths = {"topic", "user"})
    Page<Book> findAll(Pageable pageable);

    List<Book> findAllByOrderByName();
    List<Book> findAllByOrderByTopic();
    List<Book> findAllByOrderByUser();
    List<Book> findAllByOrderByDescription();
    List<Book> findAllByOrderByShortDescription();
    List<Book> findAllByOrderByUpdatedAt();
    List<Book> findAllByOrderByCreatedAt();

}
