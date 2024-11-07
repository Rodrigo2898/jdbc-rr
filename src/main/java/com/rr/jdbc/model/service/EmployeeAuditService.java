package com.rr.jdbc.model.service;

import com.rr.jdbc.model.dao.DaoFactory;
import com.rr.jdbc.model.dao.EmployeeAuditDao;
import com.rr.jdbc.model.entity.EmployeeAuditEntity;

import java.util.List;

public class EmployeeAuditService {
    private final EmployeeAuditDao dao = DaoFactory.createEmployeeAuditDao();

    public List<EmployeeAuditEntity> findAll() {
        return dao.findAll();
    }
}
