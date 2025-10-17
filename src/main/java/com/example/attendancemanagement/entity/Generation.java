package com.example.attendancemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "generations")
@Data
@EqualsAndHashCode(callSuper = true)
public class Generation extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "generation_id", updatable = false, nullable = false)
    private UUID generationId;
    
    @JsonProperty("generation_name")
    @Column(name = "generation_name", length = 100, nullable = false)
    private String generationName;
    
    @JsonProperty("is_active")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @JsonProperty("start_date")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @JsonProperty("center_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;
    
    // One-to-many relationship with Class
    @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Class> classes;
}
