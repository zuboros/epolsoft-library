package com.example.epolsoftbackend.repositories;

import com.example.epolsoftbackend.entities.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LibraryRepository extends CrudRepository<Library, Long>, JpaSpecificationExecutor<Library> {
    Page findAll(Specification<Library> librarySpecification, Pageable pageable);
}
