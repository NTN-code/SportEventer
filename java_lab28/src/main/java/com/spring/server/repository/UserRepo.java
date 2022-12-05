package com.spring.server.repository;

import com.spring.server.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
