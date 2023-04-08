package com.nv.sberschool.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel {
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "change_password_token")
    private String changePasswordToken;

    //TODO при создании пользователя с несуществующей ролью, роль добавляется в базу
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<BookRentInfo> bookRentInfos;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", bookRentInfos=" + bookRentInfos +
                '}';
    }
}
