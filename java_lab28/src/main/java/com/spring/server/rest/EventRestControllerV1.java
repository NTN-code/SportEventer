package com.spring.server.rest;

import com.spring.server.aop.LogAnnotation;
import com.spring.server.dto.event.CreateEventDTO;
import com.spring.server.dto.event.EventDTO;
import com.spring.server.exception.ResourceNotFoundException;
import com.spring.server.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="События", description = "API для взаимодействия с моделями Event")
@RestController
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Gets all Events", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the Events",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EventDTO.class)))
                    })
    })
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public ResponseEntity<EventDTO> getOneEvent(@PathVariable Long eventId) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.getOne(eventId), HttpStatus.OK);
    }

    @LogAnnotation
    @PostMapping
    public ResponseEntity<EventDTO> addEvent(@Valid @RequestBody CreateEventDTO newEvent) {
        return new ResponseEntity<>(eventService.add(newEvent), HttpStatus.CREATED);
    }

    @PostMapping("sub")
    public ResponseEntity<Void> subEvent(@RequestBody HashMap<String, Long> ids) {
        Long eventId = ids.get("eventId");
        Long userId = ids.get("userId");

        eventService.sub(eventId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("unsub")
    public ResponseEntity<Void> unsubEvent(@RequestBody HashMap<String, Long> ids) {
        Long eventId = ids.get("eventId");
        Long userId = ids.get("userId");

        eventService.unsub(eventId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @LogAnnotation
    @PutMapping("{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @Valid @RequestBody CreateEventDTO updateEvent) throws ResourceNotFoundException {
        return new ResponseEntity<>(eventService.update(eventId, updateEvent), HttpStatus.OK);
    }

    @LogAnnotation
    @DeleteMapping("{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
