package com.demo.mapper;

import com.demo.dto.TaskDTO;
import com.demo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    Task taskDtoToTask(TaskDTO taskDto);
    TaskDTO taskToTaskDTO(Task task);
}