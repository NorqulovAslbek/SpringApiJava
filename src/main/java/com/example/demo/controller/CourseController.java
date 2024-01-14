package com.example.demo.controller;

import com.example.demo.dto.CourseDTO;

import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Bu method student malumotlarini database ga
     * create qilish uchun ishlatiladiagan method.
     *
     * @param dto StudentDTO typedagi parametr qabul qiladi @RequestBody anatatsyasi bizga
     *            json yoki xml formatdagi malumotni StudentDTO ga ozgartrib beradi.
     * @return -> ResponseEntity <StudentDTO>
     */

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.create(dto)); // 200
    }

    /**
     * Barcha studentlar haqidagi nalumotlarni database dan olish uchun ishlatiladigan method
     *
     * @return ResponseEntity
     */

    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> all() {
        return ResponseEntity.ok(courseService.getAll());
    }

    /**
     * id si bo'yicha Studentni olish uchun ishlatiladigan method
     *
     * @param id Integer
     * @return ResponseEntity
     */

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    /**
     * id orqali studentni delete qilish uchun ishlatilgan method.
     *
     * @param id Integer
     * @return ResponseEntity <Boolean>
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.deleteStudentById(id));
    }

    /**
     * id orqali update qiluvchi method.
     *
     * @param id Integer
     * @param studentDTO StudentDTO
     * @return ResponseEntity
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody CourseDTO studentDTO) {
        return ResponseEntity.ok(courseService.updateStudentById(id, studentDTO));
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity.ok(courseService.getByName(name));
    }


    @GetMapping("/price")
    public ResponseEntity<List<CourseDTO>> getByPrice(@RequestParam Double price){
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    @GetMapping("/duration")
    public ResponseEntity<List<CourseDTO>> getByDuration(@RequestParam Integer duration){
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

//    @GetMapping("/price")
//    public ResponseEntity<List<CourseDTO>>getByPrice(@RequestParam Double price1 ,@RequestParam Double price2){
//
//    }


}
