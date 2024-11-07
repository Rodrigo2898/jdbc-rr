package com.rr.jdbc.db;

public class DBException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public DBException(String msg) {
        super(msg);
    }
}
