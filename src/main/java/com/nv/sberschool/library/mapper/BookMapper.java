package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.BookDto;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.AuthorRepository;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper extends GenericMapper<Book, BookDto> {

    private final AuthorRepository authorRepository;

    protected BookMapper(ModelMapper modelMapper, AuthorRepository authorRepository) {
        super(modelMapper, Book.class, BookDto.class);
        this.authorRepository = authorRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Book.class, BookDto.class)
                .addMappings(m -> m.skip(BookDto::setAuthorsIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(BookDto.class, Book.class)
                .addMappings(m -> m.skip(Book::setAuthors)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(BookDto source, Book destination) {
        if(!Objects.isNull(source.getAuthorsIds())) {
            destination.setAuthors(new HashSet<>(authorRepository.findAllById(source.getAuthorsIds())));
        }
    }

    @Override
    protected void mapSpecificFields(Book source, BookDto destination) {
        destination.setAuthorsIds(getAuthorIds(source));
    }

    protected Set<Long> getAuthorIds(Book entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getAuthors())
                ? null
                : entity.getAuthors()
                .stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}

