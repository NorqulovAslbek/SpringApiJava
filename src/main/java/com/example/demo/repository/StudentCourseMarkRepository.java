package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentCourseMarkEntity;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends JpaRepository<StudentCourseMarkEntity, Integer> {
    @Query("FROM StudentCourseMarkEntity WHERE createdDate BETWEEN ?1 AND ?2")
    List<StudentCourseMarkEntity> findByCreatedDateBetween(LocalDateTime to, LocalDateTime from);

    @Query("FROM StudentCourseMarkEntity WHERE studentId=?1 and createdDate BETWEEN ?2 AND ?3")
    List<StudentCourseMarkEntity> findByStudentIdAndCreatedDateBetween1(StudentEntity studentId, LocalDateTime from, LocalDateTime to);

    @Query("FROM StudentCourseMarkEntity WHERE studentId=?1 ORDER BY createdDate DESC ")
    List<StudentCourseMarkEntity> findByStudentIdOrderByCreatedDateDesc(StudentEntity studentId);

    @Query("FROM StudentCourseMarkEntity WHERE courseId=?1 ORDER BY createdDate DESC ")
    List<StudentCourseMarkEntity> findByCourseIdOrderByCreatedDateDesc(CourseEntity courseId);

    //    Studentni olgan eng katta 3ta baxosi.
    @Query("FROM StudentCourseMarkEntity WHERE studentId=?1 ORDER BY mark DESC LIMIT 3")
    List<StudentCourseMarkEntity> findTop3ByStudentIdOrderByMarkDesc(StudentEntity studentId);

    @Query("FROM StudentCourseMarkEntity WHERE studentId=?1 ORDER BY createdDate DESC ")
    Optional<StudentCourseMarkEntity> findByStudentIdOrderByCreatedDateAsc(StudentEntity studentId);

    @Query("FROM StudentCourseMarkEntity WHERE studentId=?1 ")
    Page<StudentCourseMarkEntity> findByStudentId(StudentEntity studentId, Pageable pageable);


}
