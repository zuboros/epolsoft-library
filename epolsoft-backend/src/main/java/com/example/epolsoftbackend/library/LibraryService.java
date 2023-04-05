package com.example.epolsoftbackend.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List findByCriteria(Pageable pageable){
        Page page = libraryRepository.findAll(pageable);
        List result = List.of(page.getContent(), page.getTotalElements());
        return result;
    }

}
/*new Specification<Library>() {
            @Override
            public Predicate toPredicate(Root<Library> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, */