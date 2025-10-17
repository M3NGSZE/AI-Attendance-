package com.example.attendancemanagement.repository;

import com.example.attendancemanagement.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CenterRepository extends JpaRepository<Center, UUID> {
    
    Optional<Center> findByCenterName(String centerName);
    
    List<Center> findByIsActiveTrue();
    
    List<Center> findByIsActiveFalse();
    
    @Query("SELECT c FROM Center c WHERE c.isActive = :isActive ORDER BY c.centerName ASC")
    List<Center> findByIsActiveOrderByCenterNameAsc(@Param("isActive") Boolean isActive);
    
    @Query("SELECT c FROM Center c WHERE c.lat IS NOT NULL AND c.lng IS NOT NULL")
    List<Center> findCentersWithLocation();
    
    @Query("SELECT c FROM Center c WHERE c.centerName LIKE %:name%")
    List<Center> findByCenterNameContaining(@Param("name") String name);
}
