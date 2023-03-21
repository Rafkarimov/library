package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.AuthorWithBooksDto;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorWithBooksMapper  extends GenericMapper<Author, AuthorWithBooksDto> {

    private final BookRepository bookRepository;

    protected AuthorWithBooksMapper(ModelMapper modelMapper, BookRepository bookRepository) {
        super(modelMapper, Author.class, AuthorWithBooksDto.class);
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Author.class, AuthorWithBooksDto.class)
                .addMappings(m -> m.skip(AuthorWithBooksDto::setBooksId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AuthorWithBooksDto.class, Author.class)
                .addMappings(m -> m.skip(Author::setBooks)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(AuthorWithBooksDto source, Author destination) {
        if(!Objects.isNull(source.getBooksId())) {
            destination.setBooks(new HashSet<>(bookRepository.findAllById(source.getBooksId())));
        }
    }

    @Override
    protected void mapSpecificFields(Author source, AuthorWithBooksDto destination) {
        destination.setBooksId(getBookIds(source));
    }

    protected Set<Long> getBookIds(Author entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getBooks())
                ? null
                : entity.getBooks()
                .stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}

