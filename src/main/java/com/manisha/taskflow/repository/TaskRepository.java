package com.manisha.taskflow.repository;

import com.manisha.taskflow.entity.Task;
import com.manisha.taskflow.enums.Priority;
import com.manisha.taskflow.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUserId(Long userId, Pageable pageable);
    Page<Task> findByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);
    Page<Task> findByUserIdAndPriority(Long userId, Priority priority, Pageable pageable);
}
