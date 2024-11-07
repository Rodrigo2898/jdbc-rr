package com.rr.jdbc.model.dao;

import com.rr.jdbc.model.entity.EmployeeAuditEntity;

import java.util.List;

public interface EmployeeAuditDao {
    List<EmployeeAuditEntity> findAll();
}
