package com.spring.server;

import com.spring.server.dto.event.CreateEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class IntegrationTests {
    @Autowired
    private MockMvc mvc;

    public MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return  post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getEvent() throws Exception {
        this.mvc.perform(get("/api/v1/events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createEvent() throws Exception {
        CreateEventDTO createEventDTO = new CreateEventDTO("test.jpg", "Test", "test");

        this.mvc.perform(postJson("/api/v1/events", createEventDTO))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
