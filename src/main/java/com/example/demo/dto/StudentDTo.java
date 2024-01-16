package com.example.demo.dto;

import com.example.demo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)// nol keladigan fildlarni frontentga berib yubormaslik uchun.
public class StudentDTo {
    private Integer id;
    private String name;
    private String surname;
    private String level;
    private String age;
    private Gender gender;
    private LocalDateTime createdDate;
}
