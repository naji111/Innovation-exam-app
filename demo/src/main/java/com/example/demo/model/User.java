package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Can be "STUDENT" or "TEACHER"

    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private Set<Exam> createdExams = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_exams",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private Set<Exam> enrolledExams = new HashSet<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Exam> getCreatedExams() {
        return createdExams;
    }

    public void setCreatedExams(Set<Exam> createdExams) {
        this.createdExams = createdExams;
    }

    public Set<Exam> getEnrolledExams() {
        return enrolledExams;
    }

    public void setEnrolledExams(Set<Exam> enrolledExams) {
        this.enrolledExams = enrolledExams;
    }
}
