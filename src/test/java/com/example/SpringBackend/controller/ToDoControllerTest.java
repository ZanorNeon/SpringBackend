package com.example.SpringBackend.controller;

import com.example.SpringBackend.model.ToDo;
import com.example.SpringBackend.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToDoController.class)
@Import(GlobalExceptionHandler.class)
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ToDoService toDoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ToDo toDo;

    @BeforeEach
    void setup() {
        toDo = new ToDo(1, "Test Text");
    }

    @Test
    void findAll_ReturnsToDoList() throws Exception {
        when(toDoService.findAll()).thenReturn(List.of(toDo));
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].text").value("Test Text"));
    }

    @Test
    void getToDo_ValidId_ReturnsToDo() throws Exception {
        when(toDoService.findById(1)).thenReturn(toDo);
        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test Text"));
    }

    @Test
    void getToDo_InvalidId_Returns404() throws Exception {
        when(toDoService.findById(999)).thenReturn(null);
        mockMvc.perform(get("/api/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ToDo id not found - 999"));
    }

    @Test
    void addToDo_ReturnsSavedToDo() throws Exception {
        ToDo newToDo = new ToDo(0, "New Text");
        ToDo savedToDo = new ToDo(5, "New Text");
        when(toDoService.save(any(ToDo.class))).thenReturn(savedToDo);
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newToDo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.text").value("New Text"));
    }

    @Test
    void deleteToDo_ValidId_ReturnsConfirmation() throws Exception {
        when(toDoService.findById(1)).thenReturn(toDo);
        Mockito.doNothing().when(toDoService).deleteById(1);
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted ToDo id - 1"));
    }

    @Test
    void deleteToDo_InvalidId_Returns404() throws Exception {
        doThrow(new RuntimeException()).when(toDoService).deleteById(999);
        mockMvc.perform(delete("/api/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}