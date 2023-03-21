package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.GenericRepository;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public abstract class GenericService<T extends GenericModel> {

    private final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> listAll() {
        return repository.findAll();
    }

    public T getOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Запись с таким id не найдена"));
    }

    public T create(T object) {
        object.setCreatedBy("ADMIN");
        object.setCreatedWhen(LocalDateTime.now());
        return repository.save(object);
    }

    public T update(T object) {
        return repository.save(object);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<T> findByCreatedBy(String createdBy) {
        return repository.findByCreatedBy(createdBy);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
