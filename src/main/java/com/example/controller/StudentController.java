package com.example.controller;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.repository.AttendanceRepository;
import com.example.repository.StudentRepository;
import com.example.service.AttendanceService;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/students")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

//    @PostMapping("/students/save")
//    public String saveStudent(@ModelAttribute Student student) {
//        studentService.saveStudent(student);
//        return "redirect:/students";
//    }

    public void updateAttendanceForNewStudent(Student student){
        List<LocalDate> classDates = attendanceService.getAllClassDates();
        if (classDates.isEmpty()) {
            return;
        }
        for (LocalDate date : classDates) {
            Integer classCount = attendanceService.getClassCountByDate(date);
            if (classCount != null) {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setDate(date);
                attendance.setStatus(Attendance.Status.ABSENT);
                attendance.setClassCount(classCount);
                attendanceService.save(attendance);
            }
        }
    }

    @Transactional
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student) {
        System.out.println("saving student " + student);
        Student savedStudent = studentRepository.save(student); // no flush, rely on transaction commit
        System.out.println("saved student " + savedStudent);
        System.out.println("saved student id " + savedStudent.getId());
        int savedStudentId = savedStudent.getId();
        updateAttendanceForNewStudent(savedStudent);

        return "redirect:/students";

    }


    @GetMapping("/attendance")
    public String showAttendanceForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "attendance_form";
    }

    @PostMapping("/attendance/save")
    public String saveAttendance(@RequestParam("studentIds") List<Long> studentIds,
                                 @RequestParam("statuses") List<String> statuses,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @RequestParam("classCount") int classCount) {
        for (int i = 0; i < studentIds.size(); i++) {
            Attendance attendance = new Attendance();
            attendance.setStudent(new Student(Math.toIntExact(studentIds.get(i))));
            attendance.setStatus(Attendance.Status.valueOf(statuses.get(i)));
            attendance.setDate(date);
            attendance.setClassCount(classCount);

            System.out.println("studentIds = " + studentIds);
            System.out.println("statuses = " + statuses);
            System.out.println("date = " + date);
            System.out.println("classCount = " + classCount);
            studentService.saveAttendance(attendance);
        }
        return "redirect:/attendance/list";
    }


//    @GetMapping("/attendance/list")
//    public String viewAttendanceList(Model model) {
//        model.addAttribute("attendances", studentService.getAllAttendances());
//        return "attendance_list";
//    }

//    @GetMapping("/attendance/list")
//    public String viewTodayAttendance(Model model) {
//        LocalDate today = LocalDate.now();
//        List<Attendance> todayAttendance = attendanceRepository.findByDate(today);
//        model.addAttribute("attendances", todayAttendance);
//        return "attendance_list";
//    }
    @GetMapping("/attendance/list")
    public String viewAttendanceList(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<Attendance> attendances = attendanceRepository.findByDate(date);
        model.addAttribute("attendances", attendances);
        model.addAttribute("selectedDate", date); // optional: show current date in view
        return "attendance_list";
    }

    @GetMapping("students/records/{id}")
    public String getStudentAttendance(@PathVariable("id") Integer id, Model model) {

        Optional<Student> studentOpt = studentRepository.findById(id);
        if (!studentOpt.isPresent()) {
            // Handle student not found, redirect or show error
            return "redirect:/students";
        }

        Student student = studentOpt.get();

        Integer totalClasses = attendanceRepository.getTotalClasses(Long.valueOf(id));
        Integer presentClasses = attendanceRepository.getPresentClassesByStudent(Long.valueOf(id));

        if (totalClasses == null) totalClasses = 0;
        if (presentClasses == null) presentClasses = 0;

        Integer absentClasses = totalClasses - presentClasses;
        double attendancePercentage = totalClasses > 0 ? (presentClasses * 100.0) / totalClasses : 0;

        model.addAttribute("student", student);
        model.addAttribute("totalClasses", totalClasses);
        model.addAttribute("presentClasses", presentClasses);
        model.addAttribute("absentClasses", absentClasses);
        model.addAttribute("attendancePercentage", String.format("%.2f", attendancePercentage));

        return "student-attendance";
    }

    @Transactional
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        attendanceService.deleteAllByStudentId(id);
        studentService.deleteById(id);

        return "redirect:/students";
    }

}

