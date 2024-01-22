package com.example.demo.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInfoMapper {
    private Integer id;
    private String name;
    private String level;

    public StudentInfoMapper() {

    }

    public StudentInfoMapper(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.level = surname;
    }
}
