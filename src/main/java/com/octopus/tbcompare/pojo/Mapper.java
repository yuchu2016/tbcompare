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
 * Time: 15:23
 */
@Entity
@Table(name = "type_mapper")
@Data
public class Mapper {

    @Id
    @GeneratedValue
    private Integer id;

    private String msType;

    private String ssType;

    private String pgType;
}
