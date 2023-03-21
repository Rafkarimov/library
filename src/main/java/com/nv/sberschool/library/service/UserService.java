package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.GenericRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {

    private final RoleService roleService;

    protected UserService(GenericRepository<User> repository, RoleService roleService) {
        super(repository);
        this.roleService = roleService;
    }

    @Override
    public User create(User object) {
        object.setRole(roleService.getByTitle("USER"));
        return super.create(object);
    }
}
