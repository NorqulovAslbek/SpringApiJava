package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.CourseEntity;
import com.example.demo.exp.AppBadException;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO create(CourseDTO dto) {
//        dto.setCreatedDate(LocalDateTime.now());
        CourseEntity entity = getDtoToEntity(dto);
        courseRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> entityList = courseRepository.findAll();
        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            CourseDTO dto = getEntityToDTO(entity); //bu entity ni dto ga ozgartirb beradi.
            dtoList.add(dto);
        }
        return dtoList;
    }


    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        if (courseEntity.isPresent()) {
            CourseEntity course = courseEntity.get();
            return getEntityToDTO(course);

        } else {
            throw new AppBadException("Bundat student mavjud emas!!");
        }
    }


        public boolean deleteStudentById(Integer id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        } else {
            throw new AppBadException("Bunday id li student topilmadi!");
        }
    }


    public boolean updateStudentById(Integer id, CourseDTO dto) {
        if (courseRepository.existsById(id)) {
            CourseEntity entity = getDtoToEntity(dto);
            entity.setId(id);
            courseRepository.save(entity);
            return true;
        } else {
            throw new AppBadException("Bunday id li student topilmadi");
        }
    }


    public List<CourseDTO> getByName(String name) {
        List<CourseEntity> byName = courseRepository.getByName(name);
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byName) {
            CourseDTO dto = getEntityToDTO(courseEntity);
            list.add(dto);
        }
        return list;
    }

    //////

    public List<CourseDTO> getByPrice(Double price){
        List<CourseEntity> byPrice=courseRepository.getByPrice(price);
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byPrice) {
            CourseDTO dto = getEntityToDTO(courseEntity);
            list.add(dto);
        }
        return list;
    }
    ///////
    public List<CourseDTO> getByDuration(Integer duration){
        List<CourseEntity> byDuration=courseRepository.getByDuration(duration);
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byDuration) {
            CourseDTO dto = getEntityToDTO(courseEntity);
            list.add(dto);
        }
        return list;

    }


    private CourseEntity getDtoToEntity(CourseDTO dto) {
        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setPrice(dto.getPrice());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
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
}
