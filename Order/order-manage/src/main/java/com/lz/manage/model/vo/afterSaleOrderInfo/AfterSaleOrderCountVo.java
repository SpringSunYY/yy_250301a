package com.lz.manage.model.vo.afterSaleOrderInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.bPOrderInfo
 * Author: YY
 * CreateTime: 2025-03-06  21:15
 * Description: AfterSaleOrderCountVo
 * Version: 1.0
 */
@Data
public class AfterSaleOrderCountVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderCount;

    /**
     * 白嫖退款金额
     */
    private BigDecimal afterSalePriceCount;
}
