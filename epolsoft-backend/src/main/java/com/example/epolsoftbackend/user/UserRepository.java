package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.library.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>,
        PagingAndSortingRepository<User, Long> {

    Optional<User> findByMail(String mail);
    Page findAll(Pageable pageable);

    @Query(value = "SELECT * FROM isPasswordExpired(CAST(?1 AS TIMESTAMP WITHOUT TIME ZONE))", nativeQuery = true)
    boolean isPasswordExpired(LocalDateTime passwordUpdatedAt);

}
