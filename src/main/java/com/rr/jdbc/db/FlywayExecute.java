package com.rr.jdbc.db;

import org.flywaydb.core.Flyway;

public class FlywayExecute {
    public static void execute() {
        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/jdbcsample", "root", "root")
                .load();
        flyway.migrate();
    }
}
