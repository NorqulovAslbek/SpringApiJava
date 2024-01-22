package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import com.example.demo.enums.Gender;
import com.example.demo.mapper.StudentInfoMapper;
import com.example.demo.mapper.StudentMapper;
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
    @Query("select s.id, s.name, s.surname from StudentEntity s")
    List<Object[]> getShortInfo1();

    @Query("select new com.example.demo.mapper.StudentInfoMapper (s.id, s.name, s.surname) from StudentEntity s")
    List<StudentInfoMapper> getShortInfo3();


    // select * from student where name=? [sort]
    @Query("from StudentEntity where name=:nameInput")
    List<StudentEntity> findByName1(@Param("nameInput") String name);

    @Query("from StudentEntity where name=?1 or surname=?2 order by age desc limit ?3")
    List<StudentEntity> findByName2(String name, String surname, int limit);

    @Query("select count (s.id) from StudentEntity as s where s.name=?1 order by s.age desc ")
    Long countByName(@Param("nameInput") String name);

    @Query("select new StudentEntity (s.id,s.name,s.surname)  from StudentEntity s")
    List<StudentEntity> getShortInfo();

    @Query("from StudentEntity where name=?1 or surname=?2")
    List<StudentEntity> findByNameWithSort(String name, String surname, Sort sort);

    @Query("FROM StudentEntity s,BookEntity b")
    List<Object[]> joinExamole6();

    @Query("FROM StudentEntity s,BookEntity b WHERE s.id=b.id")
    List<Object[]> joinExample7();

    @Query("FROM StudentEntity s INNER JOIN BookEntity b ON s.id=b.id")
    List<Object[]> joinExample8();

    @Query("SELECT s FROM StudentEntity AS s INNER JOIN StudentCourseMarkEntity AS scm ON scm.id=s.id WHERE scm.id=s.id")
    List<StudentEntity> joinExample7(@Param("scmId") Integer cmId);

    @Query("SELECT s FROM StudentEntity AS s INNER JOIN StudentCourseMarkEntity AS scm WHERE scm.id=s.id")
    List<StudentEntity> joinExample8(@Param("scmId") Integer cmId);

    @Query(value = "SELECT * FROM student s ", nativeQuery = true)
    List<StudentEntity> joinExample9();

    @Query(value = "SELECT s.id,s.name FROM student s ", nativeQuery = true)
    List<Object[]> joinExample10();

    @Query(value = "select s.id,s.name, s.surname, " +
            " (select count(*) from student_course_mark where student_id = s.id) as markCount " +
            "from student s", nativeQuery = true)
    List<StudentMapper> joinExample12();

    @Query("update StudentEntity set name=:name,surname=:surname where id=:id")
    int updateStudent(@Param("name") String name, @Param("surname") String surname, @Param("id") Integer id);


}
