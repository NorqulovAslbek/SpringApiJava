package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import com.example.demo.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {

    @Query("FROM StudentEntity WHERE name=?1")
    List<StudentEntity> getByName(String name);

    @Query("FROM StudentEntity WHERE surname=?1")
    List<StudentEntity> getBySurname(String surname);

    @Query("FROM StudentEntity WHERE level=?1")
    List<StudentEntity> getByLevel(String level);

    @Query("FROM StudentEntity WHERE age=?1")
    List<StudentEntity> getByAge(String age);

    @Query("FROM StudentEntity WHERE gender=?1")
    List<StudentEntity> getByGender(Gender gender);

    @Query("FROM StudentEntity WHERE createdDate BETWEEN ?1 AND ?2")
    List<StudentEntity> getByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("FROM StudentEntity WHERE level=?1")
    Page<StudentEntity> findByLevel(String level, Pageable pageable);

    @Query("FROM StudentEntity WHERE gender=?1")
    Page<StudentEntity> findByGender(Gender gender, Pageable pageable);

//    Optional<StudentEntity> findFirstByName(String name);
//    //
//
//    Optional<StudentEntity> findFirstByNameOrderByCreatedDateDesc(String name);
//    // select * from student where name = ? order by created_date desc limit 1
//
//    Optional<StudentEntity> findTopByName(String name);
//    // select * from student where name = ? limit 1
//
//    Optional<StudentEntity> findTop3ByName(String name);
//    // select * from student where name = ? limit 3
//
//    Long findCountByName(String name);
//
//    // select count(*) from student where name = ?

//    List<StudentEntity> findAllByName(String name, Sort sort);

    // select * from student where name=? [sort]
    @Query("from StudentEntity where name=:nameInput")
    List<StudentEntity> findByName1(@Param("nameInput") String name);

    @Query("from StudentEntity where name=?1 or surname=?2 order by age desc limit ?3")
    List<StudentEntity> findByName2(String name, String surname, int limit);

    @Query("select count (s.id) from StudentEntity as s where s.name=?1 order by s.age desc ")
    Long countByName(@Param("nameInput") String name);

    @Query("select s.id,s.name,s.surname  from StudentEntity s")
    List<StudentEntity> getShortInfo();

    @Query("from StudentEntity where name=?1 or surname=?2")
    List<StudentEntity> findByNameWithSort(String name, String surname, Sort sort);
}
