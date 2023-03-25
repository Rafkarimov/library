package com.nv.sberschool.library.service;

import com.nv.sberschool.library.mapper.GenericMapper;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.GenericRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.webjars.NotFoundException;

public abstract class GenericService<T extends GenericModel> {

    private final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> listAll() {
        return repository.findAll();
    }

    public Page<T> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

// ЕСЛИ МАППЕРЫ В СЕРВИСАХ
//  public Page<N> listAll(Pageable pageable) {
//    Page<T> objects = repository.findAll(pageable);
//    List<N> result = mapper.toDTOs(objects.getContent());
//    return new PageImpl<>(result, pageable, objects.getTotalElements());
//  }

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

    public void softDelete(Long id) {
        T object = getOne(id);
        object.setDeletedBy("ADMIN"); //TODO переделать с секурити
        object.setDeleted(true);
        object.setDeletedWhen(LocalDateTime.now());
        update(object);
    }

    public void restore(Long id) {
        T object = getOne(id);
        object.setDeletedBy("ADMIN"); //TODO переделать с секурити
        object.setDeleted(false);
        object.setDeletedWhen(null);
        update(object);
    }

    public List<T> findByCreatedBy(String createdBy) {
        return repository.findByCreatedBy(createdBy);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}

