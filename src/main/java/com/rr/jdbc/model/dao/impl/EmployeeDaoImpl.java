package com.rr.jdbc.model.dao.impl;

import com.rr.jdbc.db.DB;
import com.rr.jdbc.db.DBException;
import com.rr.jdbc.model.dao.EmployeeDao;
import com.rr.jdbc.model.entity.EmployeeEntity;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private Connection conn;

    public EmployeeDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(EmployeeEntity entity) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, entity.getName());
            stmt.setBigDecimal(2, entity.getSalary());
            stmt.setString(3, formatOffsetDateTime(entity.getBirthday()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    entity.setId(id);
                }
                rs.close();
            } else {
                throw new DBException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
        }
    }

    @Override
    public void update(EmployeeEntity entity) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE employees SET name = ?, salary = ?, birthday = ? WHERE id = ?");
            stmt.setString(1, entity.getName());
            stmt.setBigDecimal(2, entity.getSalary());
            stmt.setString(3, formatOffsetDateTime(entity.getBirthday()));
            stmt.setLong(4, entity.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM employees WHERE id = ?");

            stmt.setLong(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
        }
    }

    @Override
    public EmployeeEntity findById(Long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                EmployeeEntity entity = new EmployeeEntity();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                entity.setSalary(rs.getBigDecimal("salary"));
                var birthdayInstant = rs.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC));

                return entity;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<EmployeeEntity> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM employees ORDER BY name");
            rs = stmt.executeQuery();

            List<EmployeeEntity> employees = new ArrayList<>();

            while (rs.next()) {
                EmployeeEntity entity = new EmployeeEntity();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                entity.setSalary(rs.getBigDecimal("salary"));
                var birthdayInstant = rs.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC));
                employees.add(entity);
            }
            return employees;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    private String formatOffsetDateTime(final OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private OffsetDateTime parseOffsetDateTime(final String dateTime) {
        return OffsetDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC));
    }
}
