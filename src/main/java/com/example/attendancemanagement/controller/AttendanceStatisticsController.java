package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.dto.AttendanceStatisticsResponse;
import com.example.attendancemanagement.service.AttendanceStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Statistics", description = "APIs for attendance statistics and reporting")
public class AttendanceStatisticsController {

    private final AttendanceStatisticsService attendanceStatisticsService;

    @GetMapping("/stats/current-month/{userId}")
    @Operation(summary = "Get attendance statistics for current month", 
               description = "Get count of present, absent, late, and total attendance records for the current month for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AttendanceStatisticsResponse> getCurrentMonthAttendanceStats(@PathVariable UUID userId) {
        try {
            AttendanceStatisticsResponse response = attendanceStatisticsService.getCurrentMonthAttendanceStats(userId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            // Return error response - you might want to create an error DTO as well
            return ResponseEntity.internalServerError().build();
        }
    }
}