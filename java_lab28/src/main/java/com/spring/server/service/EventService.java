package com.spring.server.service;

import com.spring.server.dto.event.CreateEventDTO;
import com.spring.server.dto.event.EventDTO;
import com.spring.server.entity.Event;
import com.spring.server.entity.user.User;
import com.spring.server.exception.ResourceNotFoundException;
import com.spring.server.repository.EventRepo;
import com.spring.server.repository.UserRepo;
import com.spring.server.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    public List<EventDTO> getAll() {
        List<Event> events = eventRepo.findAll();
        return Mapper.mapAll(events, EventDTO.class);
    }

    public EventDTO getOne(Long id) throws ResourceNotFoundException {
        Optional<Event> event = eventRepo.findById(id);

        if (event.isEmpty()) {
            throw new ResourceNotFoundException("Мероприятие не было найдено");
        } else {
            return Mapper.map(event.get(), EventDTO.class);
        }
    }

    public EventDTO add(CreateEventDTO newParamEvent) {
        Event event = new Event();
        event.setImage(newParamEvent.getImage());
        event.setTitle(newParamEvent.getTitle());
        event.setDescription(newParamEvent.getDescription());

        return Mapper.map(eventRepo.save(event), EventDTO.class);
    }

    public EventDTO update(Long id, CreateEventDTO updateEvent) throws ResourceNotFoundException {
        Optional<Event> event = eventRepo.findById(id);

        if (event.isEmpty()) {
            throw new ResourceNotFoundException("Мероприятие не было найдено");
        } else {
            event.get().setTitle(updateEvent.getTitle());
            event.get().setImage(updateEvent.getImage());
            event.get().setDescription(updateEvent.getDescription());

            return Mapper.map(eventRepo.save(event.get()), EventDTO.class);
        }
    }

    public void delete(Long id) {
        eventRepo.deleteById(id);
    }

    public void sub(Long eventId, Long userId) {
        Event event = eventRepo.findById(eventId).get();
        User user = userRepo.findById(userId).get();

        event.getUsers().add(user);
        eventRepo.save(event);
    }

    public void unsub(Long eventId, Long userId) {
        Event event = eventRepo.findById(eventId).get();
        User user = userRepo.findById(userId).get();

        event.getUsers().remove(user);
        eventRepo.save(event);
    }
}
