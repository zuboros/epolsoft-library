package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Library;
import com.example.epolsoftbackend.repositories.LibraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }


    public List<Library> findByCriteria(String bookName, String sortingOrder, String fieldName, Pageable pageable){
            Page page = libraryRepository.findAll(new Specification<Library>() {
            @Override
            public Predicate toPredicate(Root<Library> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(bookName != null ) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), bookName)));
                }

                switch (sortingOrder) {
                    case "desc":
                        query.orderBy(criteriaBuilder.desc(root.get(fieldName)));
                        break;
                    default:
                        query.orderBy(criteriaBuilder.asc(root.get(fieldName)));
                        break;
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
            return (List<Library>) page.getContent();
    }

}
