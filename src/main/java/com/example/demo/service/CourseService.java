package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entity.CourseEntity;
import com.example.demo.exp.AppBadException;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO create(CourseDTO dto) {
        dto.setCreatedDate(LocalDate.now());
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


    public List<CourseDTO> getByPrice(Double price) {
        List<CourseEntity> byPrice = courseRepository.getByPrice(price);
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byPrice) {
            CourseDTO dto = getEntityToDTO(courseEntity);
            list.add(dto);
        }
        return list;
    }

    public List<CourseDTO> getByDuration(Integer duration) {
        List<CourseEntity> byDuration = courseRepository.getByDuration(duration);
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byDuration) {
            CourseDTO dto = getEntityToDTO(courseEntity);
            list.add(dto);
        }
        return list;

    }

    public List<CourseDTO> getByPriceInterval(Double price1, Double price2) {
        List<CourseEntity> byPriceInterval = courseRepository.getByPriceBetween(price1, price2);
        List<CourseDTO> priceList = new LinkedList<>();
        for (CourseEntity course : byPriceInterval) {
            priceList.add(getEntityToDTO(course));
        }
        return priceList;
    }

    public List<CourseDTO> getByCourse(LocalDate from, LocalDate to) {
        List<CourseEntity> courseEntities = courseRepository.getByCreatedDateBetween(from, to);
        List<CourseDTO> courseDTOS = new LinkedList<>();
        for (CourseEntity course : courseEntities) {
            courseDTOS.add(getEntityToDTO(course));
        }
        return courseDTOS;
    }


    public PageImpl<CourseDTO> getByIdCoursePagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CourseEntity> entities = courseRepository.findAll(pageable);
        List<CourseEntity> content = entities.getContent();
        long totalSize = entities.getTotalElements();

        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity entity : content) {
            list.add(getEntityToDTO(entity));
        }
        return new PageImpl<>(list, pageable, totalSize);
    }

    public PageImpl<CourseDTO> getByCreatedDatePagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CourseEntity> all = courseRepository.findAll(pageable);
        long totalSize = all.getTotalElements();
        List<CourseEntity> content = all.getContent();
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity course : content) {
            list.add(getEntityToDTO(course));
        }
        return new PageImpl<>(list, pageable, totalSize);
    }


    public PageImpl<CourseDTO> getByPricePagination(Double price, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate"); //
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CourseEntity> byPrice = courseRepository.getByPrice(price, pageable);

        long totalSize = byPrice.getTotalElements();
        List<CourseEntity> courseEntities = byPrice.getContent();

        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity courseEntity : courseEntities) {
            dtoList.add(getEntityToDTO(courseEntity));
        }
        return new PageImpl<>(dtoList, pageable, totalSize);
    }

    public PageImpl<CourseDTO> getByPriceBetweenPagination(Double price1, Double price2, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CourseEntity> byPriceBetween = courseRepository.getByPriceBetween(price1, price2, pageable);
        long totalSize = byPriceBetween.getTotalElements();
        List<CourseEntity> byPriceBetweenContent = byPriceBetween.getContent();
        List<CourseDTO> list = new LinkedList<>();
        for (CourseEntity courseEntity : byPriceBetweenContent) {
            list.add(getEntityToDTO(courseEntity));
        }
        return new PageImpl<>(list, pageable, totalSize);
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
