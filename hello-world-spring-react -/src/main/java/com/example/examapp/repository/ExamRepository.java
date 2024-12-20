package com.example.examapp.repository;

import com.example.examapp.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByTeacherId(Long teacherId);
    List<Exam> findByEndTimeAfter(LocalDateTime now);
}

