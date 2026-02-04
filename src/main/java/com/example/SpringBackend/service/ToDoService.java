package com.example.SpringBackend.service;

import com.example.SpringBackend.model.ToDo;
import com.example.SpringBackend.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> findAll() {
        return ToDoRepository.findAll();
    }

    public ToDo findById(long id) {
        Optional<ToDo> result = ToDoRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Did not find task id - " + id));
    }
}
