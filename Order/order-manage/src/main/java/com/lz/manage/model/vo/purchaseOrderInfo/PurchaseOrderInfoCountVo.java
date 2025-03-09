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
    private Long orderCount;
    private BigDecimal orderProfitCount;
    private Long salesNumberCount;
    private BigDecimal salesPriceCount;
    private BigDecimal purchasePriceCount;
    private BigDecimal purchasePremiumCount;
    private BigDecimal avgOrderProfitRate;
}
