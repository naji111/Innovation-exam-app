package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubmissionRequest {

    @NotNull
    private Long examId;

    @NotBlank
    private String filePath;

    @NotNull
    private Long studentId;

    private boolean late;

    // Getters and Setters

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public boolean isLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }
}
