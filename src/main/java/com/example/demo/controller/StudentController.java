package com.example.demo.controller;

import com.example.demo.dto.PaginationResultDTO;
import com.example.demo.dto.StudentDTo;
import com.example.demo.enums.Gender;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * Bu method student malumotlarini database ga
     * create qilish uchun ishlatiladiagan method.
     *
     * @param dto StudentDTO typedagi parametr qabul qiladi @RequestBody anatatsyasi bizga
     *            json yoki xml formatdagi malumotni StudentDTO ga ozgartrib beradi.
     * @return -> ResponseEntity <StudentDTO>
     */

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody StudentDTo dto) {
        return ResponseEntity.ok(studentService.create(dto)); // 200
    }

    /**
     * Barcha studentlar haqidagi nalumotlarni database dan olish uchun ishlatiladigan method
     *
     * @return ResponseEntity
     */

    @GetMapping("")
    public ResponseEntity<List<StudentDTo>> all() {
        return ResponseEntity.ok(studentService.getAll());
    }

    /**
     * id si bo'yicha Studentni olish uchun ishlatiladigan method
     *
     * @param id Integer
     * @return ResponseEntity
     */

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTo> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * id orqali studentni delete qilish uchun ishlatilgan method.
     *
     * @param id Integer
     * @return ResponseEntity <Boolean>
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.deleteStudentById(id));
    }

    /**
     * id orqali update qiluvchi method.
     *
     * @param id         Integer
     * @param studentDTO StudentDTO
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody StudentDTo studentDTO) {
        return ResponseEntity.ok(studentService.updateStudentById(id, studentDTO));
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }

    @GetMapping("/surname")
    public ResponseEntity<?> getBySurname(@RequestParam String surname) {
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }

    @GetMapping("/level")
    public ResponseEntity<?> getByLevel(@RequestParam String level) {
        return ResponseEntity.ok(studentService.getByLevel(level));
    }

    @GetMapping("/age")
    public ResponseEntity<?> getByAge(@RequestParam String age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }

    @GetMapping("/gender")
    public ResponseEntity<?> getByGender(@RequestParam Gender gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }

    @GetMapping("/date")
    public ResponseEntity<?> getByDate(@RequestParam LocalDate createDate) {
        return ResponseEntity.ok(studentService.getByCreatedDate(createDate));
    }

    @GetMapping("/interval")
    public ResponseEntity<?> getByIntervalDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(studentService.getTimeIntervalDate(from, to));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<StudentDTo>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(studentService.pagination(page, size));
    }

    @GetMapping("/paginationById")
    public ResponseEntity<PageImpl<StudentDTo>> paginationById(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(studentService.paginationById(page, size));
    }

}
