package com.lz.manage.model.vo.returnOrderInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.returnOrderInfo
 * Author: YY
 * CreateTime: 2025-03-06  21:05
 * Description: ReturnOrderCountVo
 * Version: 1.0
 */
@Data
public class ReturnOrderCountVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderCount;

    /**
     * 客户退货金额
     */
    private BigDecimal returnPriceCount;

    /**
     * 上家退款金额
     */
    private BigDecimal lastReturnPriceCount;

}
