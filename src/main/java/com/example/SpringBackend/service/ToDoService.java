package com.example.SpringBackend.service;

import com.example.SpringBackend.model.ToDoEntity;
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

    public List<ToDoEntity> findAll() {
        return toDoRepository.findAll();
    }

    public ToDoEntity findById(long id) {
        Optional<ToDoEntity> result = toDoRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Did not find ToDoEntity id - " + id));
    }

    public ToDoEntity save(ToDoEntity theToDosEntity) {
        return toDoRepository.save(theToDosEntity);
    }

    public void deleteById(long id) {
        if (!toDoRepository.existsById(id)) {
            throw new RuntimeException("ToDoEntity id not found - " + id);
        }
        toDoRepository.deleteById(id);
    }
}
