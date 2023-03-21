package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.BookRepository;
import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper extends GenericMapper<Author, AuthorDto> {

    private final BookRepository bookRepository;

    protected AuthorMapper(ModelMapper modelMapper, BookRepository bookRepository) {
        super(modelMapper, Author.class, AuthorDto.class);
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Author.class, AuthorDto.class)
                .addMappings(m -> m.skip(AuthorDto::setBooksId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AuthorDto.class, Author.class)
                .addMappings(m -> m.skip(Author::setBooks)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(AuthorDto source, Author destination) {
        if (!Objects.isNull(source.getBooksId())) {
            destination.setBooks(new HashSet<>(bookRepository.findAllById(source.getBooksId())));
        }
    }

    @Override
    protected void mapSpecificFields(Author source, AuthorDto destination) {
        destination.setBooksId(getBookIds(source));
    }

    protected Set<Long> getBookIds(Author entity) {
        return Objects.isNull(entity.getBooks())
                ? null
                : entity.getBooks()
                .stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}

