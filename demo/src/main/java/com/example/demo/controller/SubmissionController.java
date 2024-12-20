package com.example.demo.controller;

import com.example.demo.model.Exam;
import com.example.demo.model.Submission;
import com.example.demo.model.User;
import com.example.demo.payload.SubmissionRequest;
import com.example.demo.service.ExamService;
import com.example.demo.service.FileService;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;



    @GetMapping("/exam/{examId}")
        public ResponseEntity<?> getSubmissionsByExam(@PathVariable Long examId) {
            List<Submission> submissions = submissionService.getSubmissionsByExam(examId);
            if (submissions.isEmpty()) {
                return ResponseEntity.status(404).body("No submissions found for this exam");
            }
            return ResponseEntity.ok(submissions);
        }





        @PostMapping("/submit")
        public ResponseEntity<?> submitExam(@RequestParam("examId") Long examId, @RequestParam("file") MultipartFile file, @RequestParam("studentId") Long studentId) {
            try {
                Exam exam = examService.getExamById(examId);
                if (exam == null) {
                    return ResponseEntity.status(404).body("Exam not found");
                }

                User student = userService.getUserById(studentId);
                if (student == null) {
                    return ResponseEntity.status(404).body("Student not found");
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime startTime = exam.getStartTime();
                LocalDateTime endTime = exam.getEndTime();

                if (now.isBefore(startTime)) {
                    return ResponseEntity.status(403).body("Exam is not accessible at this time");
                }

                String filePath = fileService.saveFile(file);

                SubmissionRequest submissionRequest = new SubmissionRequest();
                submissionRequest.setExamId(examId);
                submissionRequest.setStudentId(studentId);
                submissionRequest.setFilePath(filePath);

                Submission submission = submissionService.submitExam(submissionRequest);
                if (now.isAfter(endTime.plusMinutes(1))) {
                    submission.setLate(true);
                } else {
                    submission.setLate(false);
                }
                submissionService.saveSubmission(submission);

                return ResponseEntity.ok(submission);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
            }
        }

















}
