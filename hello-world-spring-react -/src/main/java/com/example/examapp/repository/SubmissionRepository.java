package com.example.examapp.repository;

import com.example.examapp.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByExamId(Long examId);
    Optional<Submission> findByExamIdAndStudentId(Long examId, Long studentId);
}