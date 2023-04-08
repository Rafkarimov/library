package com.nv.sberschool.library.dto;

import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.model.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends GenericDto {

    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private String changePasswordToken;
    private Role role;
    private Set<Long> bookRentInfosId;
}
