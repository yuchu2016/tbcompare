package com.octopus.tbcompare.vo;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 11:28
 */
@Data
public class TableVo {

    private String name;

    private List<ColumnVo> columnList;
}
