package com.example.attendancemanagement.repository;

import com.example.attendancemanagement.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, UUID> {
    
    List<Class> findByGenerationGenerationId(UUID generationId);
    
    List<Class> findByIsOpenedTrue();
    
    List<Class> findByIsOpenedFalse();
    
    List<Class> findByCourseLevel(String courseLevel);
    
    @Query("SELECT c FROM Class c WHERE c.generation.generationId = :generationId AND c.isOpened = :isOpened")
    List<Class> findByGenerationIdAndIsOpened(@Param("generationId") UUID generationId, @Param("isOpened") Boolean isOpened);
    
    @Query("SELECT c FROM Class c JOIN c.users u WHERE u.userId = :userId")
    List<Class> findByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT c FROM Class c WHERE c.generation.generationId = :generationId AND c.courseLevel = :courseLevel")
    List<Class> findByGenerationIdAndCourseLevel(@Param("generationId") UUID generationId, @Param("courseLevel") String courseLevel);
}
