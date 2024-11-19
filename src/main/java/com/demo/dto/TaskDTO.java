package com.demo.dto;

import com.demo.entity.enumerate.Priority;
import com.demo.entity.enumerate.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TaskDTO(
        @NotEmpty(message = "Heading must not be empty")
        String heading,
        @Size(min = 10, max = 25, message = "Description must be between 10 and 25 characters")
        String description,
        @NotNull(message = "Status must not be empty")
        Status status,
        @NotNull(message = "Priority must not be empty")
        Priority priority,
        String comment,
        String author,
        @NotEmpty(message = "Executor must not be empty")
        String executor
) {
}