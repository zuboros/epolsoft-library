package com.example.epolsoftbackend.author;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    public AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    public AuthorDTO authorToAuthorDTO(Author author);
    public Author authorDTOToAuthor(AuthorDTO authorDTO);
    public List<AuthorDTO> listAuthorToListAuthorDTO(List<Author> authorList);
}
