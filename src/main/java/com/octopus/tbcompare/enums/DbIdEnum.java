package com.octopus.tbcompare.enums;

public enum DbIdEnum {
    MYSQL(1),
    SQLSERVER(2),
    POSTGRESQL(3),
    ;

    private Integer id;

    DbIdEnum( Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
