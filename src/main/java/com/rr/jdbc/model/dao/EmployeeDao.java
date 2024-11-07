package com.rr.jdbc.model.dao;

import com.rr.jdbc.model.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao {
    void insert(final EmployeeEntity entity);

    void update(final EmployeeEntity entity);

    void delete(final Long id);

    EmployeeEntity findById(final Long id);

    List<EmployeeEntity> findAll();
}
