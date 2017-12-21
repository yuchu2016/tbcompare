package com.octopus.tbcompare.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 9:44
 */
@Entity
@Table(name = "tb_database")
@Data
public class Databases {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String driver;

    private String url;

    private String username;

    private String password;
}
