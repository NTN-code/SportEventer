package com.spring.server.rest;

import com.spring.server.dto.AuthenticationRequestDTO;
import com.spring.server.dto.user.CreateUserDTO;
import com.spring.server.dto.user.UserDTO;
import com.spring.server.entity.user.User;
import com.spring.server.exception.ResourceNotFoundException;
import com.spring.server.exception.UserAlreadyExistException;
import com.spring.server.repository.UserRepo;
import com.spring.server.security.jwt.JwtTokenProvider;
import com.spring.server.service.MailService;
import com.spring.server.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="Авторизация", description = "API для взаимодействия с Auth")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepo userRepo, UserService userService, MailService mailService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailService = mailService;
    }


    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@Valid @RequestBody CreateUserDTO newUser) throws UserAlreadyExistException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        UserDTO userDTO = userService.add(newUser);

        String message = "<a href=\"http://localhost:8080/api/v1/auth/activate/" + userDTO.getId() + "\">Activate</a>";

        new Thread(() -> {
            try {
                mailService.send(userDTO.getEmail(), "User activation", message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/activate/{userId}")
    public ResponseEntity<Void> activate(@PathVariable Long userId) throws ResourceNotFoundException, URISyntaxException {
        userService.activate(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("http://localhost:3000/activate"));
        return new ResponseEntity<>(headers,HttpStatus.SEE_OTHER);
}

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtTokenProvider.createToken(username, user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
