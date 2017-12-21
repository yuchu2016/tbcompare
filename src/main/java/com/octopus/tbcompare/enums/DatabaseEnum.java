package com.octopus.tbcompare.enums;


public enum DatabaseEnum {
    MYSQL("MySql",3306,"com.mysql.jdbc.Driver","jdbc:mysql://"),
    SQL_SERVER("Sql_Server",1433,"com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:mssql://"),
    POSTGRESQL("PostgreSql",5432,"org.postgresql.Driver","jdbc:postgresql://")
    ;
    private String dbType;

    private Integer dbPort;

    private String className;

    private String conHeader;

    DatabaseEnum(String dbType, Integer dbPort, String className, String conHeader) {
        this.dbType = dbType;
        this.dbPort = dbPort;
        this.className = className;
        this.conHeader = conHeader;
    }

    public String getDbType() {
        return dbType;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public String getClassName() {
        return className;
    }

    public String getConHeader() {
        return conHeader;
    }

    //    private String dbClass;
}
