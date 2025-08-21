package com.example.service;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.repository.AttendanceRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Autowired
    private AttendanceRepository attendanceRepo;

    public void saveAttendance(Attendance attendance) {
        attendanceRepo.save(attendance);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepo.findAll();
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(Math.toIntExact(id));
    }
}
