package com.example.demo.service;

import com.example.demo.dto.PaginationResultDTO;
import com.example.demo.dto.StudentDTo;
import com.example.demo.dto.StudentFilterDTO;
import com.example.demo.entity.StudentEntity;
import com.example.demo.enums.Gender;
import com.example.demo.exp.AppBadException;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.repository.StudentCustomRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentCustomRepository studentCustomRepository;

    /**
     * Bu method bizga student malumotlarini data base ga
     * create qilish uchun ishlatilyapdi.
     *
     * @param dto StudentDTO typedagi parametr qabul qiladi @RequestBody anatatsyasi bizga
     *            json yoki xml formatdagi malumotni StudentDTO ga ozgartrib beradi.
     * @return -> ResponseEntity <StudentDTO>
     */
    public StudentDTo create(StudentDTo dto) {
        StudentEntity entity = getDtoToEntity(dto);
        dto.setCreatedDate(LocalDateTime.now());
        studentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    /**
     * Barcha studentlar haqidagi nalumotlarni database dan olib StudentController ga qaytarish ishlatilyapdi
     *
     * @return ResponseEntity
     */
    public List<StudentDTo> getAll() {
        Iterable<StudentEntity> entityList = studentRepository.findAll();
        List<StudentDTo> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            StudentDTo dto = getEntityToDTO(entity); //bu entity ni dto ga ozgartirb beradi.
            dtoList.add(dto);
        }
        return dtoList;
    }


    /**
     * id si bo'yicha Studentni olish uchun ishlatilyapti
     *
     * @param id Integer
     * @return ResponseEntity
     */
    public StudentDTo getStudentById(Integer id) {
        Optional<StudentEntity> studentEntityById = studentRepository.findById(id);
        if (studentEntityById.isPresent()) {
            StudentEntity studentEntity = studentEntityById.get();
            return getEntityToDTO(studentEntity);
        } else {
            throw new AppBadException("Bundat student mavjud emas!!");
        }
    }

    /**
     * id orqali studentni delete qilish uchun ishlatilgan method.
     *
     * @param id Integer
     * @return ResponseEntity
     */
    public boolean deleteStudentById(Integer id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        } else {
            throw new AppBadException("Bunday id li student topilmadi!");
        }
    }

    /**
     * id orqali update qiluvchi method.
     *
     * @param id  Integer
     * @param dto StudentDTO
     * @return ResponseEntity
     */
    public boolean updateStudentById(Integer id, StudentDTo dto) {
        if (studentRepository.existsById(id)) {
            StudentEntity entity = getDtoToEntity(dto);
            studentRepository.save(entity);
            return true;
        } else {
            throw new AppBadException("Bunday id li student topilmadi");
        }
    }


    public List<StudentDTo> getByName(String name) {
        List<StudentEntity> byName = studentRepository.getByName(name);
        List<StudentDTo> list = new LinkedList<>();
        for (StudentEntity studentEntity : byName) {
            StudentDTo dto = getEntityToDTO(studentEntity);
            list.add(dto);
        }
        return list;
    }

    public List<StudentDTo> getBySurname(String surname) {
        List<StudentEntity> bySurname = studentRepository.getBySurname(surname);
        List<StudentDTo> list = new LinkedList<>();
        for (StudentEntity studentEntity : bySurname) {
            StudentDTo dto = getEntityToDTO(studentEntity);
            list.add(dto);
        }
        return list;
    }

    public List<StudentDTo> getByLevel(String level) {
        List<StudentEntity> byLevel = studentRepository.getByLevel(level);
        List<StudentDTo> list = new LinkedList<>();
        for (StudentEntity studentEntity : byLevel) {
            StudentDTo dto = getEntityToDTO(studentEntity);
            list.add(dto);
        }
        return list;
    }

    public List<StudentDTo> getByAge(String age) {
        List<StudentEntity> byAge = studentRepository.getByAge(age);
        List<StudentDTo> list = new LinkedList<>();
        for (StudentEntity studentEntity : byAge) {
            StudentDTo dto = getEntityToDTO(studentEntity);
            list.add(dto);
        }
        return list;
    }

    public List<StudentDTo> getByGender(Gender gender) {
        List<StudentEntity> byGender = studentRepository.getByGender(gender);
        List<StudentDTo> list = new LinkedList<>();
        for (StudentEntity studentEntity : byGender) {
            StudentDTo dto = getEntityToDTO(studentEntity);
            list.add(dto);
        }
        return list;
    }


    public List<StudentDTo> getByCreatedDate(LocalDate localDate) {
        LocalDateTime from = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(localDate, LocalTime.MAX);
        List<StudentEntity> byDate = studentRepository.getByCreatedDateBetween(from, to);
        List<StudentDTo> studentDTos = new LinkedList<>();
        for (StudentEntity studentEntity : byDate) {
            studentDTos.add(getEntityToDTO(studentEntity));
        }
        return studentDTos;
    }

    public List<StudentDTo> getTimeIntervalDate(LocalDate from, LocalDate to) {
        LocalDateTime fromDate = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(to, LocalTime.MAX);
        List<StudentEntity> byDate = studentRepository.getByCreatedDateBetween(fromDate, toDate);
        List<StudentDTo> studentDTos = new LinkedList<>();
        for (StudentEntity studentEntity : byDate) {
            studentDTos.add(getEntityToDTO(studentEntity));
        }
        return studentDTos;
    }


    /**
     * bu method bizga  StudentDTO dto type dagi parametr qabul qilib uni entity ga o'zgartirib beradi.
     *
     * @param dto -StudentDTO
     * @return StudentEntity
     */

    private StudentEntity getDtoToEntity(StudentDTo dto) {
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setLevel(dto.getLevel());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setSurname(dto.getSurname());
        return entity;
    }

    /**
     * bu method bizga  StudentEntity entity type dagi parametr qabul qilib uni StudentDTO ga o'zgartirib beradi.
     *
     * @param entity -StudentEntity
     * @return StudentDTO
     */
    private StudentDTo getEntityToDTO(StudentEntity entity) {
        StudentDTo dto = new StudentDTo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<StudentDTo> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate"); // vaqti bo'yicha sort qilib beradi. eng oxirida qo'yilganini birinchi chiqarb beradi.
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findAll(pageable);
        List<StudentEntity> entities = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();
        List<StudentDTo> dToList = new LinkedList<>();
        for (StudentEntity entity : entities) {
            dToList.add(getEntityToDTO(entity));
        }
        return new PageImpl<>(dToList, pageable, totalElements);
    }

    public PageImpl<StudentDTo> sortedPageByLevel(String level, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findByLevel(level, pageable);
        List<StudentEntity> entities = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();
        List<StudentDTo> dToList = new LinkedList<>();
        for (StudentEntity entity : entities) {
            dToList.add(getEntityToDTO(entity));
        }
        return new PageImpl<>(dToList, pageable, totalElements);
    }

    public PageImpl<StudentDTo> sortedByGenderPage(Gender gender, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(size - 1, page, sort);
//        Gender gender1 = Gender.valueOf(gender);
        Page<StudentEntity> byGender = studentRepository.findByGender(gender, pageable);
        long totalElements = byGender.getTotalElements();
        List<StudentEntity> entities = byGender.getContent();

        List<StudentDTo> dToList = new LinkedList<>();
        for (StudentEntity entity : entities) {
            dToList.add(getEntityToDTO(entity));
        }
        return new PageImpl<>(dToList, pageable, totalElements);
    }


    public boolean update2(Integer id, StudentDTo dTo) {
        int effectedRows = studentRepository.updateStudent(dTo.getName(), dTo.getSurname(), id);
        return true;
    }

    public PageImpl<StudentDTo> filter(StudentFilterDTO filter, int page, int size) {
        PaginationResultDTO<StudentEntity> paginationResult = studentCustomRepository.filter(filter, page, size);

        List<StudentDTo> dtoList = new LinkedList<>();
        for (StudentEntity entity : paginationResult.getList()) {
            dtoList.add(getEntityToDTO(entity));
        }

        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, paginationResult.getTotalSize());
    }


    public void test() {
//        List<Object[]> objectList = studentRepository.getShortInfo1();
//        List<StudentDTo> dtoList = new LinkedList<>();
//        for (Object[] obj : objectList) {
//            StudentDTo dto = new StudentDTo();
//            dto.setId((Integer) obj[0]);
//            dto.setName((String) obj[1]);
//            dto.setSurname((String) obj[2]);
//            dtoList.add(dto);
//        }
//        System.out.println(dtoList);

        List<StudentMapper> mapperList = studentRepository.joinExample12();
        List<StudentDTo> dtoList = new LinkedList<>();
        for (StudentMapper mapper : mapperList) {
            StudentDTo dto = new StudentDTo();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setSurname(mapper.getSurname());
            dtoList.add(dto);
        }
        System.out.println();

    }


//    public List<StudentDTo> getAllByNameWithSort() {
//        studentRepository.findAllByName("Ali", Sort.by(Sort.Direction.DESC));
//        studentRepository.findAll(Sort.by(Sort.Direction.DESC, "surname"));
//        return null;
//    }
//
//    public void testQuery() {
//        studentRepository.findByName1("Alish");
//        studentRepository.findByName2("Alish", "Asl", 10);
//        studentRepository.getShortInfo();
//
//        studentRepository.findByNameWithSort("Ali", "Aliyev",
//                Sort.by(Sort.Direction.DESC, "age"));
//    }


}
