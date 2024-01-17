package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.StudentCourseMarkDTO;
import com.example.demo.dto.StudentDTo;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentCourseMarkEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.exp.AppBadException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentCourseMarkRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
        if (!studentRepository.existsById(dto.getStudentId())
                || !courseRepository.existsById(dto.getCourseId())) {
            throw new AppBadException("not found!");
        }
        var entity = studentCourseMarkRepository.save(getEntity(dto));
        return getDTOWithDetail(entity);
    }

    public StudentCourseMarkDTO update(StudentCourseMarkDTO dto, Integer id) {
        if (!studentCourseMarkRepository.existsById(id) ||
                !studentRepository.existsById(dto.getStudentId())
                || !courseRepository.existsById(dto.getCourseId())) {
            throw new AppBadException("not found!");
        }
        dto.setId(id);
        StudentCourseMarkEntity save =
                studentCourseMarkRepository.save(getEntity(dto));
        return getDTOWithDetail(save);
    }


    public StudentCourseMarkDTO getByIdWithDetail(Integer id) {
        if (!studentCourseMarkRepository.existsById(id)) {
            throw new AppBadException("not found!");
        }
        StudentCourseMarkEntity entity =
                studentCourseMarkRepository.findById(id).get();
        return getDTOWithDetail(entity);
    }


    public StudentCourseMarkDTO getById(Integer id) {
        if (!studentCourseMarkRepository.existsById(id)) {
            throw new AppBadException("not found!");
        }
        return getDTO(studentCourseMarkRepository.findById(id).get());
    }


    public boolean delete(Integer id) {
        if (!studentCourseMarkRepository.existsById(id)) {
            throw new AppBadException("not found!");
        }
        studentCourseMarkRepository.deleteById(id);
        return true;
    }

    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> all =
                studentCourseMarkRepository.findAll();
        List<StudentCourseMarkDTO> list = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : all) {
            list.add(getDTO(studentCourseMarkEntity));
        }
        return list;
    }

    public List<String> getMark(Integer id, LocalDate localDate) {
        LocalDateTime from = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(localDate, LocalTime.MAX);
        List<String> mark = new LinkedList<>();
        List<StudentCourseMarkEntity> byCreatedDateBetween =
                studentCourseMarkRepository.findByCreatedDateBetween(from, to);
        for (StudentCourseMarkEntity entity : byCreatedDateBetween) {
            if (entity.getStudentId().getId().equals(id))
                mark.add(entity.getCourseId().getName() + "--> " + entity.getMark());
        }
        return mark;

    }


    public List<StudentCourseMarkDTO> getIntervalMark(Integer id, LocalDate from, LocalDate to) {
        LocalDateTime fromTime = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toTime = LocalDateTime.of(to, LocalTime.MAX);
        List<StudentCourseMarkDTO> markDTOList = new LinkedList<>();
        StudentEntity student = new StudentEntity();
        student.setId(id);
        List<StudentCourseMarkEntity> byStudentIdAndCreatedDateBetween =
                studentCourseMarkRepository.findByStudentIdAndCreatedDateBetween1(student, fromTime, toTime);
        for (StudentCourseMarkEntity entity : byStudentIdAndCreatedDateBetween) {
            markDTOList.add(getDTOWithDetail(entity));
        }
        return markDTOList;
    }

    public List<StudentCourseMarkDTO> getStudentMarkSortedCreatedDate(Integer id) {
        StudentEntity student = new StudentEntity();
        student.setId(id);
        List<StudentCourseMarkDTO> markDTOList = new LinkedList<>();
        List<StudentCourseMarkEntity> byStudentCreatedDateDesc =
                studentCourseMarkRepository.findByStudentIdOrderByCreatedDateDesc(student);
        for (StudentCourseMarkEntity entity : byStudentCreatedDateDesc) {
            markDTOList.add(getDTO(entity));
        }
        return markDTOList;

    }


    public List<StudentCourseMarkDTO> getGradeByCourseId(Integer id) {
        CourseEntity course = new CourseEntity();
        course.setId(id);
        List<StudentCourseMarkDTO> markDTOList = new LinkedList<>();
        List<StudentCourseMarkEntity> markEntities =
                studentCourseMarkRepository.findByCourseIdOrderByCreatedDateDesc(course);
        for (StudentCourseMarkEntity markEntity : markEntities) {
            StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
            dto.setCourseId(markEntity.getCourseId().getId());
            dto.setMark(markEntity.getMark());
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName(markEntity.getCourseId().getName());
            dto.setCourseDTO(courseDTO);
            dto.setCreatedDate(markEntity.getCreatedDate());

            markDTOList.add(dto);
        }
        return markDTOList;

    }


    public StudentCourseMarkDTO getTheStudentFinalGrade(Integer id) {
//        Studentni eng oxirda olgan baxosi, va curse nomi.
        StudentEntity student = new StudentEntity();
        student.setId(id);
        List<StudentCourseMarkEntity> byStudentCreatedDateDesc =
                studentCourseMarkRepository.findByStudentIdOrderByCreatedDateDesc(student);
        StudentCourseMarkEntity entity = byStudentCreatedDateDesc.get(0);
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setStudentDTo(getEntityToDTO(entity.getStudentId()));
        dto.setMark(entity.getMark());
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(entity.getCourseId().getName());
        dto.setCourseDTO(courseDTO);
        return dto;
    }
//    Studentni olgan eng katta 3ta baxosi.

    public List<StudentCourseMarkDTO> getMarkStudents(Integer id) {
        StudentEntity entity = new StudentEntity();
        entity.setId(id);
        List<StudentCourseMarkEntity> orderByMarkDesc =
                studentCourseMarkRepository.findTop3ByStudentIdOrderByMarkDesc(entity);
        List<StudentCourseMarkDTO> list = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : orderByMarkDesc) {
            list.add(getDTO(studentCourseMarkEntity));

        }
        return list;
    }

    public StudentCourseMarkDTO getMarkStudent(Integer id) {
        StudentEntity entity = new StudentEntity();
        entity.setId(id);
        Optional<StudentCourseMarkEntity> orderByMarkDesc =
                studentCourseMarkRepository.findByStudentIdOrderByCreatedDateAsc(entity);

        StudentCourseMarkEntity entity1 = orderByMarkDesc.get();
        return getDTOWithDetail(entity1);
    }


    public PageImpl<StudentCourseMarkDTO> getPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll(pageable);
        long totalElements = all.getTotalElements();
        List<StudentCourseMarkEntity> content = all.getContent();
        List<StudentCourseMarkDTO> list = new LinkedList<>();

        for (StudentCourseMarkEntity entity : content) {
            list.add(getDTOWithDetail(entity));
        }
        return new PageImpl<>(list, pageable, totalElements);
    }


    public PageImpl<StudentCourseMarkDTO> getByIdPagination(Integer studentId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        StudentEntity student = new StudentEntity();
        student.setId(studentId);
        Page<StudentCourseMarkEntity> byStudentPage = studentCourseMarkRepository.findByStudentId(student, pageable);
        long totalElements = byStudentPage.getTotalElements();
        List<StudentCourseMarkEntity> studentPageContent = byStudentPage.getContent();
        List<StudentCourseMarkDTO> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : studentPageContent) {
            list.add(getDTOWithDetail(entity));
        }
        return new PageImpl<>(list,pageable,totalElements);

    }


    private StudentCourseMarkDTO getDTO(StudentCourseMarkEntity entity) {
        var dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setCourseId(entity.getCourseId().getId());
        dto.setStudentId(entity.getStudentId().getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }


    private StudentCourseMarkEntity getEntity(StudentCourseMarkDTO dto) {
        var entity = new StudentCourseMarkEntity();
        StudentEntity student = studentRepository.findById(dto.getStudentId()).get();
        CourseEntity courseId = courseRepository.findById(dto.getCourseId()).get();
        entity.setStudentId(student);
        entity.setCourseId(courseId);
        entity.setMark(dto.getMark());
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

    private StudentCourseMarkDTO getDTOWithDetail(StudentCourseMarkEntity entity) {
        var dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setMark(entity.getMark());

        var courseId = entity.getCourseId();
        var studentId = entity.getStudentId();

        dto.setCourseDTO(getEntityToDTO(courseId));
        dto.setStudentDTo(getEntityToDTO(studentId));
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    private CourseDTO getEntityToDTO(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDuration(entity.getDuration());
        dto.setPrice(entity.getPrice());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

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


}
