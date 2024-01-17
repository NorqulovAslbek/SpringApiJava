package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

    @Query("FROM CourseEntity WHERE name=?1")
    List<CourseEntity> getByName(String name);

    @Query("FROM CourseEntity WHERE price=?1")
    List<CourseEntity> getByPrice(Double price);

    @Query("FROM CourseEntity WHERE duration=?1")
    List<CourseEntity> getByDuration(Integer duration);

    @Query("FROM CourseEntity WHERE price BETWEEN  ?1 AND ?2")
    List<CourseEntity> getByPriceBetween(Double price1, Double price2);

    @Query("FROM CourseEntity WHERE createdDate BETWEEN ?1 AND ?2")
    List<CourseEntity> getByCreatedDateBetween(LocalDate from, LocalDate to);

    @Query("FROM CourseEntity WHERE price =?1")
    Page<CourseEntity> getByPrice(Double price, Pageable pageable);

    @Query("FROM CourseEntity WHERE price BETWEEN ?1 AND ?2")
    Page<CourseEntity> getByPriceBetween(Double price1, Double price2, Pageable pageable);
}
