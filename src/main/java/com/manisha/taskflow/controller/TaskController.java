package com.manisha.taskflow.controller;

import com.manisha.taskflow.dto.TaskRequest;
import com.manisha.taskflow.entity.Task;
import com.manisha.taskflow.entity.User;
import com.manisha.taskflow.enums.Priority;
import com.manisha.taskflow.enums.TaskStatus;
import com.manisha.taskflow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request,
                                           @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request, currentUser));
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @AuthenticationPrincipal User currentUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return ResponseEntity.ok(taskService.getUserTasks(currentUser.getId(), status, priority, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id,
                                        @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.getTaskById(id, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody TaskRequest request,
                                           @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.updateTask(id, request, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                           @AuthenticationPrincipal User currentUser) {
        taskService.deleteTask(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}