package com.octopus.tbcompare.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 15:03
 */
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "tb_type")
public class Type {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer db;

    private String type;
}
