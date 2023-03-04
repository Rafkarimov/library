package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.GenericModel;
import com.nv.sberschool.library.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public abstract class GenericController<T extends GenericModel> {

    private final GenericRepository<T> genericRepository;

    public GenericController(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getById(@RequestParam(value = "id") Long id) {
        return genericRepository.findById(id)
                .map(t -> ResponseEntity.status(HttpStatus.OK).body(t))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @RequestMapping(value = "/getByCreator", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> getByCreator(@RequestParam(value = "creator") String creator) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericRepository.findByCreatedBy(creator));
    }
}

