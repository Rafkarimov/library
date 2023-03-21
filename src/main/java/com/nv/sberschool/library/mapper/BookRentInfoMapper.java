package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.BookRentInfoDto;
import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.repository.BookRepository;
import com.nv.sberschool.library.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
public class BookRentInfoMapper extends GenericMapper<BookRentInfo, BookRentInfoDto> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    protected BookRentInfoMapper(ModelMapper modelMapper, BookRepository bookRepository, UserRepository userRepository) {
        super(modelMapper, BookRentInfo.class, BookRentInfoDto.class);
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(BookRentInfo.class, BookRentInfoDto.class)
                .addMappings(m -> m.skip(BookRentInfoDto::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(BookRentInfoDto::setBookId)).setPostConverter(toDtoConverter());

        super.modelMapper.createTypeMap(BookRentInfoDto.class, BookRentInfo.class)
                .addMappings(m -> m.skip(BookRentInfo::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(BookRentInfo::setBook)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(BookRentInfoDto source, BookRentInfo destination) {
        destination.setBook(bookRepository.findById(source.getBookId()).orElseThrow(() -> new NotFoundException("Книги не найдено")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователя не найдено")));
    }

    @Override
    protected void mapSpecificFields(BookRentInfo source, BookRentInfoDto destination) {
        destination.setUserId(source.getUser().getId());
        destination.setBookId(source.getBook().getId());
    }

}


