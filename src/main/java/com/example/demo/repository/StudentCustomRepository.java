package com.example.demo.repository;

import com.example.demo.dto.PaginationResultDTO;
import com.example.demo.dto.StudentFilterDTO;
import com.example.demo.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<StudentEntity> filter(StudentFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append("and name =:name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {
            builder.append("and lower(surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM StudentEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createDate desc");

        String countBuilder = "Select count(s) FROM StudentEntity as s where 1=1 " + builder;

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());

        Query countQuery = entityManager.createQuery(countBuilder);
        selectQuery.setMaxResults(size); //size
        selectQuery.setFirstResult((page-1)*size); //offset(page-1)*size;

       /* if (filter.getId() != null) {
            query.setParameter("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.setParameter("name", filter.getName());
        }
        if(filter.getSurname() != null){
            query.setParameter("surname", filter.getSurname());
        }*/
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<StudentEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<StudentEntity>(totalElements,entityList);


    }
}
