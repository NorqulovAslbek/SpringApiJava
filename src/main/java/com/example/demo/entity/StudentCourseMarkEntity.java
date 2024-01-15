package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student_course_mark")
public class StudentCourseMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentId;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseId;
    private  Integer mark;
    private LocalDateTime createdDate;
}
