package com.lz.manage.model.statics;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Project: eb-back
 * Package: com.ruoyi.electricbike.domain.statics.vo
 * Author: YY
 * CreateTime: 2024-11-22  08:49
 * Description: BarStaticsVo
 * Version: 1.0
 */
@Data
public class StaticsVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> names; // x
    private List<T> totals; // y
}

