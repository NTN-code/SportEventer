package com.spring.server.dto.user;

import com.spring.server.dto.comment.CommentDTO;
import com.spring.server.dto.event.EventDTO;
import com.spring.server.entity.user.Role;
import com.spring.server.entity.user.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Status status;

    @JsonIgnore
    private List<CommentDTO> comments;
    @JsonIgnore
    private List<EventDTO> events;

    //region Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    //endregion
}
