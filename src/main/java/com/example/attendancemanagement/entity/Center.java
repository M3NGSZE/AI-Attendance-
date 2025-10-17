package com.example.attendancemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "centers")
@Data
@EqualsAndHashCode(callSuper = true)
public class Center extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "center_id", updatable = false, nullable = false)
    private UUID centerId;
    
    @JsonProperty("center_name")
    @Column(name = "center_name", length = 100, nullable = false)
    private String centerName;
    
    @JsonProperty("center_address")
    @Column(name = "center_address", length = 255)
    private String centerAddress;
    
    @JsonProperty("lat")
    @Column(name = "lat")
    private Double lat;
    
    @JsonProperty("lng")
    @Column(name = "lng")
    private Double lng;
    
    @JsonProperty("is_active")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    // One-to-many relationship with Generation
    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Generation> generations;
}
