package com.example.epolsoftbackend.library;

import org.springframework.data.jpa.domain.Specification;


public class LibrarySpecifications{
    public static Specification<Library> fieldLike(String field, String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), "%"+value+"%"));
    }
    public static Specification<Library> booksFromUser(long id){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user_id"), id));
    }

}
