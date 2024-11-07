package com.rr.jdbc.model.service;

import com.rr.jdbc.model.dao.DaoFactory;
import com.rr.jdbc.model.dao.EmployeeDao;
import com.rr.jdbc.model.entity.EmployeeEntity;

import java.util.List;

public class EmployeeService {
    private final EmployeeDao dao = DaoFactory.createEmployeeDao();

    public void insertOrUpdate(EmployeeEntity entity) {
        if (entity.getId() == null) {
            dao.insert(entity);
        } else {
            dao.update(entity);
        }
    }

    public EmployeeEntity findById(long id) {
        return dao.findById(id);
    }

    public List<EmployeeEntity> findAll() {
        return dao.findAll();
    }

    public void delete(long id) {
        dao.delete(id);
    }
}
