package com.example.project.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Id
    protected String id = UUID.randomUUID().toString();
}
