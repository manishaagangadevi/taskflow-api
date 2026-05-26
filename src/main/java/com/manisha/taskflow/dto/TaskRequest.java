package com.manisha.taskflow.dto;

import com.manisha.taskflow.enums.Priority;
import com.manisha.taskflow.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
}