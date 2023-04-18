package com.example.epolsoftbackend.library;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List findByCriteria(Pageable pageable, LibrarySearchModel searchModel) {
        Page page;
        if(!Objects.equals(searchModel.getFieldValue(), "") && !Objects.equals(searchModel.getFieldName(), "") &&
        !Objects.equals(searchModel.getFieldName(),"id")) {
            page = libraryRepository.findAll(Specification.where(
                    LibrarySpecifications.fieldLike(searchModel.getFieldName(), searchModel.getFieldValue())
                    .and(LibrarySpecifications.getAvailable())), pageable);
        }
        else page = libraryRepository.findAll(Specification.where(LibrarySpecifications.getAvailable()), pageable);
        return List.of(page.getContent(), page.getTotalElements());
    }

    public List getBooksForModerator (Pageable pageable) {
        Page page = libraryRepository.findAll(Specification.where(LibrarySpecifications.onApproving()), pageable);
        return List.of(page.getContent(), page.getTotalElements());
    }

    public List getBooksUser(Long id, Pageable pageable){
        Page page = libraryRepository.findAll(Specification.where(LibrarySpecifications.booksFromUser(id)), pageable);
        return List.of(page.getContent(), page.getTotalElements());
    }

}