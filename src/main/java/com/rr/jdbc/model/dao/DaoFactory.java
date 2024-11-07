package com.rr.jdbc.model.dao;

import com.rr.jdbc.model.dao.impl.EmployeeAuditDaoImpl;
import com.rr.jdbc.model.dao.impl.EmployeeDaoImpl;
import com.rr.jdbc.db.DB;

public class DaoFactory {

    public static EmployeeDao createEmployeeDao() {
        return new EmployeeDaoImpl(DB.getConnection());
    }

    public static EmployeeAuditDao createEmployeeAuditDao() {
        return new EmployeeAuditDaoImpl(DB.getConnection());
    }
}
