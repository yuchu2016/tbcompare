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
 * Date: 2017-12-21
 * Time: 15:20
 */
@Data
@Entity
@Table(name = "tb_trans_type")
public class TransType {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String fromType;

    private String toType;

    private Boolean legalSize;
}
