package com.octopus.tbcompare.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-20
 * Time: 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnVo {
    private String name;

    private String remarks;

    private String type;

    private String size;

    private Boolean isNull;

    private Boolean isPrimary;
}
