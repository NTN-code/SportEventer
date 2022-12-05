package com.spring.server.service;

import com.spring.server.dto.user.CreateUserDTO;
import com.spring.server.entity.user.Status;
import com.spring.server.entity.user.User;
import com.spring.server.exception.UserAlreadyExistException;
import com.spring.server.exception.ResourceNotFoundException;
import com.spring.server.dto.user.UserDTO;
import com.spring.server.repository.EventRepo;
import com.spring.server.repository.UserRepo;
import com.spring.server.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return Mapper.mapAll(users, UserDTO.class);
    }

    public List<UserDTO> getAllOfEvents(Long eventId) {
        Set<User> usersOfEvent = eventRepo.findById(eventId).get().getUsers();
        return Mapper.mapAll(usersOfEvent, UserDTO.class);
    }

    public UserDTO getOne(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Пользователь не был найден");
        } else {
            return Mapper.map(user.get(), UserDTO.class);
        }
    }

    public UserDTO add(CreateUserDTO newParamUser) throws UserAlreadyExistException {
        if (userRepo.findByUsername(newParamUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с таким логином уже существует");
        }

        User newUser = new User();
        newUser.setUsername(newParamUser.getUsername());
        newUser.setPassword(newParamUser.getPassword());
        newUser.setEmail(newParamUser.getEmail());

        return Mapper.map(userRepo.save(newUser), UserDTO.class);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public void activate(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Пользователь не был найден");
        } else {
            user.get().setStatus(Status.ACTIVE);
            userRepo.save(user.get());
        }
    }
}
