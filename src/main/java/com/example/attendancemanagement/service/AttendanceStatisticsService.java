package com.example.attendancemanagement.service;

import com.example.attendancemanagement.dto.AttendanceStatisticsResponse;
import com.example.attendancemanagement.enums.CheckInStatus;
import com.example.attendancemanagement.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceStatisticsService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceStatisticsResponse getCurrentMonthAttendanceStats(UUID userId) {
        // Get current month date range
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        
        // Calculate total days in the month
        int totalDaysInMonth = currentMonth.lengthOfMonth();

        // Count different attendance statuses for the specific user
        long absentCount = attendanceRepository.countDistinctDaysByUserAndCheckinStatusAndDateRange(
            userId, CheckInStatus.MISSED_CHECKIN, startDate, endDate);
        
        long lateCount = attendanceRepository.countDistinctDaysByUserAndCheckinStatusAndDateRange(
            userId, CheckInStatus.CHECKIN_LATE, startDate, endDate);
        
        // Present = Total days - Absent days
        long presentCount = totalDaysInMonth - absentCount;

        // Build and return response
        return AttendanceStatisticsResponse.builder()
                .userId(userId)
                .month(currentMonth.toString())
                .startDate(startDate.toString())
                .endDate(endDate.toString())
                .totalDaysInMonth(totalDaysInMonth)
                .present(presentCount)
                .absent(absentCount)
                .late(lateCount)
                .total(totalDaysInMonth)
                .build();
    }
}
