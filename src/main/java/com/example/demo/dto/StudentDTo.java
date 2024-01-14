package com.example.demo.dto;

import com.example.demo.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class StudentDTo {
    private Integer id;
    private String name;
    private String surname;
    private String level;
    private String age;
    private Gender gender;
    private LocalDateTime createdDate;
}
