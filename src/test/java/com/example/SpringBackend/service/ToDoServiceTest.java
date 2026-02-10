package com.example.SpringBackend.service;

import com.example.SpringBackend.model.ToDo;
import com.example.SpringBackend.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;
    @InjectMocks
    private ToDoService toDoService;
    private ToDo sampleToDo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleToDo = new ToDo(1, "Test");
    }

    @Test
    void findAll_ReturnsListOfToDos() {
        List<ToDo> toDos = List.of(sampleToDo);
        when(toDoRepository.findAll()).thenReturn(toDos);
        List<ToDo> result = toDoService.findAll();
        assertEquals(1, result.size());
        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void findById_ExistingId_ReturnsToDo() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(sampleToDo));
        ToDo result = toDoService.findById(1);
        assertNotNull(result);
        assertEquals("Test", result.getText());
    }

    @Test
    void findById_NonExistingId_ThrowsException() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> toDoService.findById(1));
    }

    @Test
    void save_ReturnsSavedToDo() {
        when(toDoRepository.save(any(ToDo.class))).thenReturn(sampleToDo);
        ToDo result = toDoService.save(sampleToDo);
        assertNotNull(result);
        verify(toDoRepository).save(sampleToDo);
    }

    @Test
    void deleteById_CallsRepository() {
        when(toDoRepository.existsById(1L)).thenReturn(true);
        toDoService.deleteById(1L);
        verify(toDoRepository).deleteById(1L);

    }
}