package com.example.attendancemanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatisticsResponse {
    
    @JsonProperty("userId")
    private UUID userId;
    
    @JsonProperty("month")
    private String month;
    
    @JsonProperty("startDate")
    private String startDate;
    
    @JsonProperty("endDate")
    private String endDate;
    
    @JsonProperty("totalDaysInMonth")
    private Integer totalDaysInMonth;
    
    @JsonProperty("present")
    private Long present;
    
    @JsonProperty("absent")
    private Long absent;
    
    @JsonProperty("late")
    private Long late;
    
    @JsonProperty("total")
    private Integer total;
}

