package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.BookWithAuthorsDto;
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
public class BookWithAuthorsMapper extends GenericMapper<Book, BookWithAuthorsDto> {

    private final ModelMapper mapper;
    private final AuthorRepository authorRepository;

    protected BookWithAuthorsMapper(ModelMapper modelMapper, ModelMapper mapper,
                                    AuthorRepository authorRepository) {
        super(modelMapper, Book.class, BookWithAuthorsDto.class);
        this.mapper = mapper;
        this.authorRepository = authorRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Book.class, BookWithAuthorsDto.class)
                .addMappings(m -> m.skip(BookWithAuthorsDto::setAuthorsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(BookWithAuthorsDto.class, Book.class)
                .addMappings(m -> m.skip(Book::setAuthors)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(BookWithAuthorsDto source, Book destination) {
        destination.setAuthors(new HashSet<>(authorRepository.findAllById(source.getAuthorsIds())));
    }

    @Override
    protected void mapSpecificFields(Book source, BookWithAuthorsDto destination) {
        destination.setAuthorsIds(getIds(source));
    }

    protected Set<Long> getIds(Book Book) {
        return Objects.isNull(Book) || Objects.isNull(Book.getId())
                ? null
                : Book.getAuthors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}

