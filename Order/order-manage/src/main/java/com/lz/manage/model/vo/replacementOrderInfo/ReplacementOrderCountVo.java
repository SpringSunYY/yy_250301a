package com.lz.manage.model.vo.replacementOrderInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.replacementOrderInfo
 * Author: YY
 * CreateTime: 2025-03-06  20:53
 * Description: ReplacementOrderCountVo
 * Version: 1.0
 */
@Data
public class ReplacementOrderCountVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderCount;
    /**
     * 实付金额
     */
    private BigDecimal actuallyPriceCount;

    /**
     * 佣金
     */
    private BigDecimal commissionCount;

    /**
     * 合计金额
     */
    private BigDecimal totalPriceCount;
}
