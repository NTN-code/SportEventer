package com.spring.server.rest;

    import com.spring.server.dto.user.UserDTO;
import com.spring.server.exception.ResourceNotFoundException;
import com.spring.server.service.UserService;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="Пользователи", description = "API для взаимодействия с моделями User")
@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required=false) Long eventId) {
        if (eventId != null) {
            return new ResponseEntity<>(userService.getAllOfEvents(eventId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.getOne(userId), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
