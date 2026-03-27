package com.example.SpringBackend.repository;

import com.example.SpringBackend.model.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
}
