package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
}
