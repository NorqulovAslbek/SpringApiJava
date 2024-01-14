package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import com.example.demo.enums.Gender;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;



public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
    List<StudentEntity> getByName(String name);

    List<StudentEntity> getBySurname(String surname);

    List<StudentEntity> getByLevel(String level);

    List<StudentEntity> getByAge(String age);

    List<StudentEntity> getByGender(Gender gender);

    List<StudentEntity> getByCreatedDateBetween(LocalDateTime from,LocalDateTime to);


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


}
