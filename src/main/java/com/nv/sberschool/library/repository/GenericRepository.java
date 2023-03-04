package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.GenericModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends GenericModel> extends JpaRepository<T, Long> {
    List<T> findByCreatedBy(String createdBy);
}


