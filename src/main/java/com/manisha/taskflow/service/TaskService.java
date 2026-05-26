package com.manisha.taskflow.service;

import com.manisha.taskflow.dto.TaskRequest;
import com.manisha.taskflow.entity.Task;
import com.manisha.taskflow.entity.User;
import com.manisha.taskflow.enums.Priority;
import com.manisha.taskflow.enums.TaskStatus;
import com.manisha.taskflow.exception.ResourceNotFoundException;
import com.manisha.taskflow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(TaskRequest request, User currentUser) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM)
                .dueDate(request.getDueDate())
                .user(currentUser)
                .build();
        return taskRepository.save(task);
    }

    public Page<Task> getUserTasks(Long userId, TaskStatus status, Priority priority, Pageable pageable) {
        if (status != null) {
            return taskRepository.findByUserIdAndStatus(userId, status, pageable);
        }
        if (priority != null) {
            return taskRepository.findByUserIdAndPriority(userId, priority, pageable);
        }
        return taskRepository.findByUserId(userId, pageable);
    }

    public Task getTaskById(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Not authorized");
        }
        return task;
    }

    public Task updateTask(Long taskId, TaskRequest request, User currentUser) {
        Task task = getTaskById(taskId, currentUser);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, User currentUser) {
        Task task = getTaskById(taskId, currentUser);
        taskRepository.delete(task);
    }
}