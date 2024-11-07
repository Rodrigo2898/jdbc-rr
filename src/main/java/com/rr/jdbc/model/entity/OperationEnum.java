package com.rr.jdbc.model.entity;

import java.util.stream.Stream;

public enum OperationEnum {

    INSERT("I"),
    UPDATE("U"),
    DELETE("D");

    private final String dbOperation;

    OperationEnum(String dbOperation) {
        this.dbOperation = dbOperation;
    }

    public String getDbOperation() {
        return dbOperation;
    }

    public static OperationEnum getByDbOperation(final String dbOperation) {
        return Stream.of(OperationEnum.values())
                .filter(o -> o.name().startsWith(dbOperation.toUpperCase()))
                .findFirst()
                .orElseThrow();
    }
}
