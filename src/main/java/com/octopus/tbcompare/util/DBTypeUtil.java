package com.octopus.tbcompare.util;

import com.octopus.tbcompare.enums.DatabaseEnum;
import com.octopus.tbcompare.enums.DbIdEnum;
import com.octopus.tbcompare.pojo.Databases;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-21
 * Time: 15:28
 */
public class DBTypeUtil {

    public static int getDbType(Databases databases){
        if (databases.getDriver().equals(DatabaseEnum.MYSQL.getClassName())){
            return DbIdEnum.MYSQL.getId();
        }
        if (databases.getDriver().equals(DatabaseEnum.SQL_SERVER.getClassName())){
            return DbIdEnum.SQLSERVER.getId();
        }
        if (databases.getDriver().equals(DatabaseEnum.POSTGRESQL.getClassName())){
            return DbIdEnum.POSTGRESQL.getId();
        }
        return 0;
    }
}
