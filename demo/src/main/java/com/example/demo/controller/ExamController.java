package com.example.demo.controller;

import com.example.demo.model.Exam;
import com.example.demo.model.User;
import com.example.demo.service.ExamService;
import com.example.demo.service.UserService;
import com.example.demo.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;



        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteExam(@PathVariable Long id) {
            boolean isDeleted = examService.deleteExamById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Exam deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Exam not found");
            }
        }









    @PostMapping("/create")
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        User teacher = userService.getUserById(exam.getTeacher().getId());
        if (teacher == null) {
            return ResponseEntity.status(404).body("Teacher not found");
        }
        exam.setTeacher(teacher);

        examRepository.save(exam);
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/all")
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);
        if (exam == null) {
            return ResponseEntity.notFound().build();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startTime = exam.getStartTime();
        LocalDateTime endTime = exam.getEndTime();

        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            return ResponseEntity.status(403).body("Exam is not accessible at this time");
        }

        return ResponseEntity.ok(exam);
    }
}
