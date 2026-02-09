package com.example.SpringBackend.controller;

import com.example.SpringBackend.model.ToDo;
import com.example.SpringBackend.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/api/todos")
    public List<ToDo> findAll() {
        return toDoService.findAll();
    }

    @GetMapping("/api/todos/{Id}")
    public ToDo getToDo(@PathVariable long toDoId) {
        ToDo theToDo = toDoService.findById(toDoId);
        if (theToDo == null) {
            throw new RuntimeException("Todo id not found - " + toDoId);
        }
        return theToDo;
    }

    @PostMapping("/api/todos")
    public ToDo addToDo(@RequestBody ToDo toDo) {
        return toDoService.save(toDo);
    }

    @DeleteMapping("/api/todos/{Id}")
    public String deleteToDo(@PathVariable long toDoId) {
        toDoService.deleteById(toDoId);
        return "Deleted Todo id - " + toDoId;

    }
}
