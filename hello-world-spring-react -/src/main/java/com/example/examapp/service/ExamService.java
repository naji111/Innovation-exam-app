package com.example.examapp.service;

import com.example.examapp.dto.ExamRequest;
import com.example.examapp.model.Exam;
import com.example.examapp.model.User;
import com.example.examapp.repository.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;

    public Exam createExam(ExamRequest request) {
        User teacher = userService.getCurrentUser();
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDuration(request.getDuration());
        exam.setTeacher(teacher);
        exam.setStartTime(LocalDateTime.now());
        exam.setEndTime(LocalDateTime.now().plusMinutes(request.getDuration()));
        return examRepository.save(exam);
    }

    public List<Exam> getTeacherExams() {
        User teacher = userService.getCurrentUser();
        return examRepository.findByTeacherId(teacher.getId());
    }

    public List<Exam> getAvailableExams() {
        return examRepository.findByEndTimeAfter(LocalDateTime.now());
    }

    public ResponseEntity<?> submitExam(Long examId, MultipartFile file) {
        // Logique pour sauvegarder le fichier et créer une soumission
        // ...
        return ResponseEntity.ok().build();
    }
}