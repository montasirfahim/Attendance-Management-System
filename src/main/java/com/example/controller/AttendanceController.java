package com.example.controller;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.service.AttendanceService;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;
//
//    @GetMapping("/attendance")
//    public String showForm(Model model) {
//        model.addAttribute("students", studentService.getAllStudents());
//        return "attendance_form";
//    }
//
//    @PostMapping("/attendance/submit")
//    public String submitAttendance(@RequestParam("date") String dateStr,
//                                   @RequestParam("status") List<String> statuses,
//                                   @RequestParam("studentIds") List<Integer> studentIds) {
//        LocalDate date = LocalDate.parse(dateStr);
//        List<Attendance> attendanceList = new ArrayList<>();
//
//        for (int i = 0; i < studentIds.size(); i++) {
//            Attendance attendance = new Attendance();
//            attendance.setDate(date);
//            attendance.setStatus(Attendance.Status.valueOf(statuses.get(i)));
//            attendance.setStudent(studentService.getStudentById(studentIds.get(i)));
//            attendanceList.add(attendance);
//        }
//
//        attendanceService.saveAll(attendanceList);
//        return "redirect:/attendance/list";
//    }
//
//    @GetMapping("/attendance/list")
//    public String viewAttendance(Model model) {
//        model.addAttribute("attendanceList", attendanceService.getAttendanceByDate(LocalDate.now()));
//        return "attendance_list";
//    }
}

