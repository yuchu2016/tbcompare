package com.octopus.tbcompare.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-21
 * Time: 10:04
 */
@Data
public class ColumnCompareVo {

    private List<ColumnVo> fromList;

    private List<ColumnVo> toList;

    public ColumnCompareVo() {
        fromList = new ArrayList<>();
        toList = new ArrayList<>();
    }
}
