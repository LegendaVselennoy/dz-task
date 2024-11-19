package com.demo.entity;

import com.demo.entity.enumerate.Priority;
import com.demo.entity.enumerate.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(catalog = "test", schema = "micro", name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heading;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Enumerated(value = EnumType.STRING)
    private Priority priority;
    private String comment;
    @CreatedBy
    private String author;
    private String executor;
}