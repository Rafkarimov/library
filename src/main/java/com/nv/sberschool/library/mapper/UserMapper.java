package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.UserDto;
import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.BookRentInfoRepository;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
        extends GenericMapper<User, UserDto> {

    private final BookRentInfoRepository bookRentInfoRepository;

    protected UserMapper(ModelMapper modelMapper,
                         BookRentInfoRepository bookRentInfoRepository) {
        super(modelMapper, User.class, UserDto.class);
        this.bookRentInfoRepository = bookRentInfoRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setBookRentInfosId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setBookRentInfos)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDto source, User destination) {
        if (!Objects.isNull(source.getBookRentInfosId())) {
            destination.setBookRentInfos(new HashSet<>(bookRentInfoRepository.findAllById(source.getBookRentInfosId())));
        }
        else {
            destination.setBookRentInfos(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDto destination) {
        destination.setBookRentInfosId(getIds(source));
    }

    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getBookRentInfos())
                ? null
                : entity.getBookRentInfos().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }

}



