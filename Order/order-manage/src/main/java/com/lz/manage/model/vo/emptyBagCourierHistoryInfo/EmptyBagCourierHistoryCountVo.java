package com.lz.manage.model.vo.emptyBagCourierHistoryInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.emptyBagCourierHistoryInfo
 * Author: YY
 * CreateTime: 2025-03-06  21:10
 * Description: EmptyBagCourierHistoryCountVo
 * Version: 1.0
 */
@Data
public class EmptyBagCourierHistoryCountVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderCount;
    /**
     * 金额
     */
    private BigDecimal priceCount;
}
