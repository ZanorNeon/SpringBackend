package com.example.SpringBackend.controller;

import com.example.SpringBackend.model.ToDo;
import com.example.SpringBackend.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/todos/{toDoId}")
    public ToDo getToDo(@PathVariable long toDoId) {
        ToDo theToDo = toDoService.findById(toDoId);
        if (theToDo == null) {
            throw new RuntimeException("Task id not found - " + toDoId);
        }
        return theToDo;
    }

}
