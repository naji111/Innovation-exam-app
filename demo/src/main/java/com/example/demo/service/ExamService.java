package com.example.demo.service;

import com.example.demo.model.Exam;
import com.example.demo.model.Submission;
import com.example.demo.model.User;
import com.example.demo.payload.ExamRequest;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public Exam createExam(ExamRequest examRequest, User teacher) {
        Exam exam = new Exam();
        exam.setTitle(examRequest.getTitle());
        exam.setStartTime(examRequest.getStartTime());
        exam.setEndTime(examRequest.getEndTime());
        exam.setTeacher(teacher);
        return examRepository.save(exam);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam save(Exam exam) { return examRepository.save(exam); }




        @Autowired
        private SubmissionRepository submissionRepository;

        public boolean deleteExamById(Long id) {
            if (examRepository.existsById(id)) {
                // Delete associated submissions
                List<Submission> submissions = submissionRepository.findByExamId(id);
                submissionRepository.deleteAll(submissions);
                // Delete the exam
                examRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        }











}
