package com.rr.jdbc.model.dao.impl;

import com.rr.jdbc.db.DB;
import com.rr.jdbc.db.DBException;
import com.rr.jdbc.model.dao.EmployeeAuditDao;
import com.rr.jdbc.model.entity.EmployeeAuditEntity;
import com.rr.jdbc.model.entity.EmployeeEntity;
import com.rr.jdbc.model.entity.OperationEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeAuditDaoImpl implements EmployeeAuditDao {
    private Connection conn;

    public EmployeeAuditDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<EmployeeAuditEntity> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM view_employee_audit");
            rs = stmt.executeQuery();

            List<EmployeeAuditEntity> employees = new ArrayList<>();

            while (rs.next()) {
                var birthday = Objects.isNull(rs.getTimestamp("birthday")) ? null :
                        OffsetDateTime.ofInstant(rs.getTimestamp("birthday").toInstant(), ZoneOffset.UTC);
                var oldBirthday = Objects.isNull(rs.getTimestamp("old_birthday")) ? null :
                        OffsetDateTime.ofInstant(rs.getTimestamp("old_birthday").toInstant(), ZoneOffset.UTC);
                employees.add(new EmployeeAuditEntity(
                        rs.getLong("employee_id"),
                        rs.getString("name"),
                        rs.getString("old_name"),
                        rs.getBigDecimal("salary"),
                        rs.getBigDecimal("old_salary"),
                        birthday,
                        oldBirthday,
                        OperationEnum.getByDbOperation(rs.getString("operation"))
                ));
            }
            return employees;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }
}
