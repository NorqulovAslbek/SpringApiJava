package com.example.demo.entity;

import com.example.demo.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String level;
    private String age;
    private Gender gender;
    private LocalDateTime createdDate;
//    @OneToMany(mappedBy = "student")
//    private List<StudentEntity> scmList;
    public StudentEntity() {
    }

    public StudentEntity(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}

