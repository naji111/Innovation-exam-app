package com.example.examapp.controller;

import com.example.examapp.dto.ExamRequest;
import com.example.examapp.model.Exam;
import com.example.examapp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody ExamRequest examRequest) {
        return ResponseEntity.ok(examService.createExam(examRequest));
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getTeacherExams() {
        return ResponseEntity.ok(examService.getTeacherExams());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Exam>> getAvailableExams() {
        return ResponseEntity.ok(examService.getAvailableExams());
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<?> submitExam(
            @PathVariable Long examId,
            @RequestParam("file") MultipartFile file) {
        return examService.submitExam(examId, file);
    }
}