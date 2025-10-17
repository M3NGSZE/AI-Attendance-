package com.example.attendancemanagement.repository;

import com.example.attendancemanagement.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, UUID> {
    
    Optional<Generation> findByGenerationName(String generationName);
    
    List<Generation> findByIsActiveTrue();
    
    List<Generation> findByIsActiveFalse();
    
    @Query("SELECT g FROM Generation g WHERE g.isActive = :isActive ORDER BY g.startDate DESC")
    List<Generation> findByIsActiveOrderByStartDateDesc(@Param("isActive") Boolean isActive);
    
    @Query("SELECT g FROM Generation g WHERE g.startDate <= :currentDate AND g.endDate >= :currentDate")
    List<Generation> findActiveGenerationsByDate(@Param("currentDate") java.time.LocalDate currentDate);
}
