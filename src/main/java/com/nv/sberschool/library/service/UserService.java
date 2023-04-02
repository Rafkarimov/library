package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {

    private final RoleService roleService;
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    protected UserService(RoleService roleService, UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.roleService = roleService;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User object) {
        object.setRole(roleService.getByTitle("USER"));
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.create(object);
    }

    @Override
    public User update(User object) {
        User foundUser = getOne(object.getId());
        object.setRole(foundUser.getRole());
        object.setPassword(foundUser.getPassword());
        object.setBookRentInfos(foundUser.getBookRentInfos());
        object.setDeleted(foundUser.isDeleted());
        object.setDeletedWhen(foundUser.getDeletedWhen());
        object.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setUpdatedWhen(LocalDateTime.now());
        object.setCreatedBy(foundUser.getCreatedBy());
        object.setCreatedWhen(foundUser.getCreatedWhen());
        return super.update(object);
    }

    public User getUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User createLibrarian(User object) {
        object.setRole(roleService.getByTitle("LIBRARIAN"));
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.create(object);
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }
}

