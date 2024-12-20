package com.example.demo.service;

import com.example.demo.model.Submission;
import com.example.demo.payload.SubmissionRequest;
import com.example.demo.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    public Submission submitExam(SubmissionRequest submissionRequest) {
        Submission submission = new Submission();
        submission.setExam(examService.getExamById(submissionRequest.getExamId()));
        submission.setStudent(userService.getUserById(submissionRequest.getStudentId()));
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setFilePath(submissionRequest.getFilePath());
        submission.setLate(submissionRequest.isLate());

        return submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByExam(Long examId) {
        return submissionRepository.findByExamId(examId);
    }

    public List<Submission> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }

    public void saveSubmission(Submission submission) {
        submissionRepository.save(submission);
    }
}
