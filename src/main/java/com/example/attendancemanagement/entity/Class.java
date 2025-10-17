package com.example.attendancemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "classes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Class extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "class_id", updatable = false, nullable = false)
    private UUID classId;
    
    @JsonProperty("class_name")
    @Column(name = "class_name", length = 100, nullable = false)
    private String className;
    
    @JsonProperty("generation_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", nullable = false)
    private Generation generation;
    
    @JsonProperty("course_level")
    @Column(name = "course_level", length = 50, nullable = false)
    private String courseLevel;
    
    @JsonProperty("is_opened")
    @Column(name = "is_opened", nullable = false)
    private Boolean isOpened = true;
    
    // Many-to-many relationship with User
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "class_users",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
