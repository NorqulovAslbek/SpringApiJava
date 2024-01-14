package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity,Integer> {
       List<CourseEntity> getByName(String name);

       List<CourseEntity> getByPrice(Double price);

       List<CourseEntity> getByDuration(Integer duration);
}
