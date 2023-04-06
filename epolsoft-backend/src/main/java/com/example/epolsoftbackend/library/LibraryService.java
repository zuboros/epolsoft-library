package com.example.epolsoftbackend.library;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LibraryService {

    List findByCriteria(Pageable pageable);
    List getBooksUser(Long id, Pageable pageable);

}
