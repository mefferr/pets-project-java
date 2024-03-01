package com.example.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity(name = "pet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends BaseEntity {
    private String name;
    private Instant birth;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
