package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String level;
    private Integer age;
    private LocalDate fromDate;
    private LocalDate toDate;
}
