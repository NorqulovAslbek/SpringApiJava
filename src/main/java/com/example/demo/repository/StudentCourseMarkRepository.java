package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentCourseMarkEntity;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity> findByCreatedDateBetween(LocalDateTime to, LocalDateTime from);

    List<StudentCourseMarkEntity> findByStudentIdAndCreatedDateBetween(StudentEntity studentId, LocalDateTime from, LocalDateTime to);

    List<StudentCourseMarkEntity> findByStudentIdOrderByCreatedDateDesc(StudentEntity studentId);

    List<StudentCourseMarkEntity> findByCourseIdOrderByCreatedDateDesc(CourseEntity courseId);

    //    Studentni olgan eng katta 3ta baxosi.
    List<StudentCourseMarkEntity> findTop3ByStudentIdOrderByMarkDesc(StudentEntity studentId);
    Optional<StudentCourseMarkEntity> findByStudentIdOrderByCreatedDateAsc(StudentEntity studentId);


}
