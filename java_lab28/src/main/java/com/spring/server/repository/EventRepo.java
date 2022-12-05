package com.spring.server.repository;

import com.spring.server.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepo extends CrudRepository<Event, Long> {
    List<Event> findAll();
}
