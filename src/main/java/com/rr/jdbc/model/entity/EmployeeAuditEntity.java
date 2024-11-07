package com.rr.jdbc.model.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EmployeeAuditEntity(
    Long employeeId,
    String name,
    String oldName,
    BigDecimal salary,
    BigDecimal oldSalary,
    OffsetDateTime birthday,
    OffsetDateTime oldBirthday,
    OperationEnum operation
) {
}
