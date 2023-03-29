package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Author;
import com.example.epolsoftbackend.repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(String name){
        var authorToSave = new Author();
        authorToSave.setName(name);
        return authorRepository.saveAndFlush(authorToSave);
    }
    public Optional<Author> findById(long id){
        return authorRepository.findById(id);
    }
    public ResponseEntity<HttpStatus> deleteById(long id) {
        try {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
