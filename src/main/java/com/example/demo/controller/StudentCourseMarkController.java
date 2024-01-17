package com.example.demo.controller;

import com.example.demo.dto.StudentCourseMarkDTO;
import com.example.demo.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student_course_mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    @PostMapping("")
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        return ResponseEntity.ok(studentCourseMarkService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCourseMarkDTO> update(@RequestBody StudentCourseMarkDTO dto, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.update(dto, id));
    }

    @GetMapping("/id")
    public ResponseEntity<StudentCourseMarkDTO> getById(@RequestParam Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    @GetMapping("/with/id")
    public ResponseEntity<StudentCourseMarkDTO> getByIdWithDetail(@RequestParam Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getByIdWithDetail(id));
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.delete(id));
    }

    @GetMapping()
    public ResponseEntity<List<StudentCourseMarkDTO>> getAll() {
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }


    @GetMapping("/id/date")
    public ResponseEntity<List<String>> getMark(@RequestParam("id") Integer id, @RequestParam LocalDate localDate) {
        return ResponseEntity.ok(studentCourseMarkService.getMark(id, localDate));
    }

    @GetMapping("/timeInterval")
    public ResponseEntity<List<StudentCourseMarkDTO>> getIntervalMark(@RequestParam("id") Integer id,
                                                                      @RequestParam LocalDate from,
                                                                      @RequestParam LocalDate to) {
        return ResponseEntity.ok(studentCourseMarkService.getIntervalMark(id, from, to));
    }

    @GetMapping("/markSortedCreatedDate")
    public ResponseEntity<List<StudentCourseMarkDTO>> getStudentMarkSortedCreatedDate(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getStudentMarkSortedCreatedDate(id));
    }

    @GetMapping("/gradeByCourseId")
    public ResponseEntity<List<StudentCourseMarkDTO>> getGradeByCourseId(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getGradeByCourseId(id));
    }

    @GetMapping("/theStudentFinalGrade")
    public ResponseEntity<StudentCourseMarkDTO> getTheStudentFinalGrade(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getTheStudentFinalGrade(id));
    }

    @GetMapping("/markStudents")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarkStudents(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkStudents(id));
    }

    @GetMapping("/markStudent")
    public ResponseEntity<StudentCourseMarkDTO> getMarkStudent(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkStudent(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> getStudentMarkPage(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPagination(page, size));
    }

    @GetMapping("/paginationById")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> getStudentMarkPage(@RequestParam Integer id, @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getByIdPagination(id,page, size));
    }


}
