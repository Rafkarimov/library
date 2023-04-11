package com.nv.sberschool.library.service;

import static org.junit.jupiter.api.Assertions.*;

import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.GenericRepository;
import com.nv.sberschool.library.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

//TODO: https://habr.com/ru/post/444982/ - Mokito
public abstract class GenericServiceTest<E extends GenericModel> {

    protected GenericService<E> service;
    protected GenericRepository<E> repository;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.builder()
                        .username("USER"),
                null,
                null
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    abstract void listAll();
    abstract void getOne();
    abstract void create();
    abstract void update();
    abstract void delete();
    abstract void softDelete();
    abstract void restore();
    abstract void findByCreatedBy();
    abstract void existsById();
}
