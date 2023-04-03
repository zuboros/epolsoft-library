package com.example.epolsoftbackend.library;

import com.example.epolsoftbackend.library.Library;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class LibrarySpecifications{
    public static Specification<Library> fieldLike(String field, String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), "%"+value+"%"));
    }

}
