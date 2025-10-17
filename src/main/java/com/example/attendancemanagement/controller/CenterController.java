package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.entity.Center;
import com.example.attendancemanagement.repository.CenterRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/centers")
@RequiredArgsConstructor
@Tag(name = "Center Management", description = "APIs for managing centers and their locations")
public class CenterController {

    private final CenterRepository centerRepository;
    
    // Fixed center ID as requested
    private static final UUID FIXED_CENTER_ID = UUID.fromString("fb9f3899-67d3-4bcf-a683-6629fe19499b");

    @GetMapping("/location")
    @Operation(summary = "Get center location", description = "Get latitude and longitude for the fixed center ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Center not found")
    })
    public ResponseEntity<Map<String, Object>> getCenterLocation() {
        Optional<Center> center = centerRepository.findById(FIXED_CENTER_ID);
        
        if (center.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Center not found");
            errorResponse.put("centerId", FIXED_CENTER_ID);
            return ResponseEntity.notFound().build();
        }
        
        Center centerData = center.get();
        Map<String, Object> response = new HashMap<>();
        response.put("centerId", centerData.getCenterId());
        response.put("centerName", centerData.getCenterName());
        response.put("lat", centerData.getLat());
        response.put("lng", centerData.getLng());
        response.put("centerAddress", centerData.getCenterAddress());
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/location")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update center location", description = "Update latitude and longitude for the fixed center ID (Admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location updated successfully"),
        @ApiResponse(responseCode = "404", description = "Center not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    public ResponseEntity<Map<String, Object>> updateCenterLocation(
            @Valid @RequestBody LocationUpdateRequest request) {
        
        Optional<Center> centerOpt = centerRepository.findById(FIXED_CENTER_ID);
        
        if (centerOpt.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Center not found");
            errorResponse.put("centerId", FIXED_CENTER_ID);
            return ResponseEntity.notFound().build();
        }
        
        Center center = centerOpt.get();
        center.setLat(request.getLat());
        center.setLng(request.getLng());
        
        Center updatedCenter = centerRepository.save(center);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Location updated successfully");
        response.put("centerId", updatedCenter.getCenterId());
        response.put("centerName", updatedCenter.getCenterName());
        response.put("lat", updatedCenter.getLat());
        response.put("lng", updatedCenter.getLng());
        response.put("centerAddress", updatedCenter.getCenterAddress());
        
        return ResponseEntity.ok(response);
    }

    // DTO for location update request
    public static class LocationUpdateRequest {
        private Double lat;
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }
}
