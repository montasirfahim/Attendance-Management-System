package com.example.service;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    public void deleteAllByStudentId(Long studentId) {
        attendanceRepository.deleteByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<Attendance> getAttendanceByStudent(Student student) {
        return attendanceRepository.findByStudent(student);
    }

    public void saveAll(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
    }
    public void save(Attendance attendance){
        attendanceRepository.save(attendance);
    }

    public List<LocalDate> getAllClassDates() {
        return attendanceRepository.findAllDistinctDates();
    }

    public Integer getClassCountByDate(LocalDate date) {
        List<Integer> classCounts = attendanceRepository.findClassCountsByDate(date);
        return classCounts.isEmpty() ? null : classCounts.getFirst();
    }

}
