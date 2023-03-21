package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.Role;
import com.nv.sberschool.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getByTitle(String title) {
        return repository.getRoleByTitle(title);
    }

}

