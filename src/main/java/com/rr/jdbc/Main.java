package com.rr.jdbc;

import com.rr.jdbc.db.FlywayExecute;
import com.rr.jdbc.model.dao.impl.EmployeeDaoImpl;
import com.rr.jdbc.model.entity.EmployeeEntity;
import com.rr.jdbc.model.service.EmployeeAuditService;
import com.rr.jdbc.model.service.EmployeeService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Main {

    public static final EmployeeService service = new EmployeeService();
    public static final EmployeeAuditService auditService = new EmployeeAuditService();

    public static void main(String[] args) {
        System.out.println("Hello world!");
        FlywayExecute.execute();

        var employees = new EmployeeEntity();
        employees.setId(6L);
        employees.setName("Jiraya Sensei");
        employees.setSalary(new BigDecimal("3500"));
        employees.setBirthday(OffsetDateTime.now().minusYears(50));
        service.insertOrUpdate(employees);

        System.out.println("Id selecionado: " + service.findById(1L));

//        service.findAll().forEach(System.out::println);

//        System.out.println("_____________________________________");
//
//        service.delete(4L);
//
//        service.findAll().forEach(System.out::println);

        auditService.findAll().forEach(System.out::println);

    }
}