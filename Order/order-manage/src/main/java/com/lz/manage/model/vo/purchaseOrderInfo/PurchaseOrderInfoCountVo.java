package com.lz.manage.model.vo.purchaseOrderInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Project: Order
 * Package: com.lz.manage.model.vo.PurchaseOrderInfoCountVo
 * Author: YY
 * CreateTime: 2025-03-06  20:31
 * Description: purchaseAccountInfoCountVo
 * Version: 1.0
 */
@Data
public class PurchaseOrderInfoCountVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单总数
     */
    private Long orderCount;

    /**
     * 订单总利润
     */
    private BigDecimal orderProfitCount;

    /**
     * 销售数量
     */
    private Long salesNumberCount;

    /**
     * 销售价格
     */
    private BigDecimal salesPriceCount;

    /**
     * 销售价格
     */
    private BigDecimal purchasePriceCount;

    /**
     * 采购补价
     */
    private BigDecimal purchasePremiumCount;

    /**
     * 平均订单利润率
     */
    private BigDecimal avgOrderProfitRate;
}
