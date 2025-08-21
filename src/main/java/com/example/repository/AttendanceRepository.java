package com.example.repository;
import com.example.entity.Attendance;
import com.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByStudent(Student student);

    @Modifying
    @Query("DELETE FROM Attendance a WHERE a.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT SUM(a.classCount) FROM Attendance a WHERE a.student.id = :studentId")
    Integer getTotalClasses(@Param("studentId") Long studentId);

    @Query("SELECT SUM(a.classCount) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    Integer getPresentClassesByStudent(@Param("studentId") Long studentId);

    List<Attendance> id(Long id);

    @Query("SELECT DISTINCT a.date FROM Attendance a ORDER BY a.date")
    List<LocalDate> findAllDistinctDates();

    @Query("SELECT a.classCount FROM Attendance a WHERE a.date = :date")
    List<Integer> findClassCountsByDate(@Param("date") LocalDate date);

}
