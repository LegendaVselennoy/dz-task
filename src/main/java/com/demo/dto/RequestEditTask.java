package com.demo.dto;

import com.demo.entity.enumerate.Status;
import jakarta.validation.constraints.NotNull;

public record RequestEditTask(
        @NotNull(message = "Status is not null")
        Status status,
        String comment
) {
}
