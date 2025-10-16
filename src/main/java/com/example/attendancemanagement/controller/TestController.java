package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.dto.ApiResponse;
import com.example.attendancemanagement.dto.AuthDtos.CheckInResponse;
import com.example.attendancemanagement.dto.AuthDtos.CheckOutResponse;
import com.example.attendancemanagement.service.AttendanceService;
import com.example.attendancemanagement.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final AttendanceService attendanceService;
    private final TokenUtil tokenUtil;

    /**
     * Test Check-in (No Time Validation)
     */
    @PostMapping("/checkin")
    @Operation(summary = "Test Check-in (No Time Validation)", 
               description = "Record user check-in for testing purposes without time restriction validation. Before 8:01 AM = on time, after 8:01 AM = late. Date status: weekday, overtime, weekend.")
    public ResponseEntity<ApiResponse<CheckInResponse>> testCheckIn(
            HttpServletRequest httpRequest) {
        
        // Get user ID from JWT token
        UUID userIdFromToken = tokenUtil.getUserIdFromToken(httpRequest);

        CheckInResponse checkInResponse = attendanceService.testCheckIn(userIdFromToken);
        
        ApiResponse<CheckInResponse> response = ApiResponse.<CheckInResponse>builder()
                .success(true)
                .message("Test check-in recorded successfully (no time validation)")
                .payload(checkInResponse)
                .status(HttpStatus.CREATED)
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Test Check-out (No Time Validation)
     */
    @PostMapping("/checkout")
    @Operation(summary = "Test Check-out (No Time Validation)", 
               description = "Record user check-out for testing purposes without time restriction validation. Date status: weekday, overtime, weekend.")
    public ResponseEntity<ApiResponse<CheckOutResponse>> testCheckOut(
            HttpServletRequest httpRequest) {
        
        // Get user ID from JWT token
        UUID userIdFromToken = tokenUtil.getUserIdFromToken(httpRequest);
        
        CheckOutResponse checkOutResponse = attendanceService.testCheckOut(userIdFromToken);
        
        ApiResponse<CheckOutResponse> response = ApiResponse.<CheckOutResponse>builder()
                .success(true)
                .message("Test check-out recorded successfully (no time validation)")
                .payload(checkOutResponse)
                .status(HttpStatus.OK)
                .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
